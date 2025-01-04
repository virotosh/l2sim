package free.l2j.simfactory.model.actor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.logging.Level;
import java.util.stream.Collectors;

import net.sf.l2j.commons.logging.CLogger;
import net.sf.l2j.commons.random.Rnd;

import net.sf.l2j.gameserver.data.SkillTable;
import net.sf.l2j.gameserver.data.SkillTable.FrequentSkill;
import net.sf.l2j.gameserver.data.manager.CursedWeaponManager;
import net.sf.l2j.gameserver.data.xml.AdminData;
import net.sf.l2j.gameserver.data.xml.RestartPointData;
import net.sf.l2j.gameserver.enums.MessageType;
import net.sf.l2j.gameserver.enums.RestartType;
import net.sf.l2j.gameserver.enums.actors.ClassId;
import net.sf.l2j.gameserver.enums.items.CrystalType;
import net.sf.l2j.gameserver.enums.skills.SkillType;
import net.sf.l2j.gameserver.model.World;
import net.sf.l2j.gameserver.model.WorldObject;
import net.sf.l2j.gameserver.model.actor.Creature;
import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.model.actor.container.player.Appearance;
import net.sf.l2j.gameserver.model.actor.instance.Monster;
import net.sf.l2j.gameserver.model.actor.instance.StaticObject;
import net.sf.l2j.gameserver.model.actor.template.PlayerTemplate;
import net.sf.l2j.gameserver.model.group.Party;
import net.sf.l2j.gameserver.model.item.instance.ItemInstance;
import net.sf.l2j.gameserver.model.location.Location;
import net.sf.l2j.gameserver.model.olympiad.OlympiadManager;
import net.sf.l2j.gameserver.model.pledge.ClanMember;
import net.sf.l2j.gameserver.network.serverpackets.JoinParty;
import net.sf.l2j.gameserver.skills.AbstractEffect;
import net.sf.l2j.gameserver.skills.L2Skill;

import free.l2j.simfactory.commons.SimPlayerHelper;
import free.l2j.simfactory.data.xml.SimPlayerAIData;
import free.l2j.simfactory.model.actor.ai.attribute.SimPlayerAISkill;
import free.l2j.simfactory.model.actor.ai.preference.Functions;
import free.l2j.simfactory.model.actor.ai.preference.OffensiveSkill;
import free.l2j.simfactory.model.actor.ai.preference.TeleportModule;
import free.l2j.simfactory.model.actor.ai.preference.WalkNode;
import free.l2j.simfactory.taskmanager.SimPlayerTaskManager;


public class SimPlayer extends Player
{
	private static final CLogger LOGGER = new CLogger(SimPlayer.class.getName());
	protected boolean _isBusy = false;
	protected List<OffensiveSkill> _offensiveSkills = new ArrayList<>();
	protected boolean _isMagicClass = false;
	
	protected String _walkOrFarm = "farm";
	protected boolean _isWalking = false;
	protected boolean _shouldGoHomeWhenOutCity = false;
	protected Queue<WalkNode> _walkNodes = new LinkedList<>();
	private WalkNode _currentWalkNode;
	private int _simlevel = 1;
	
	public SimPlayer(int objectId, PlayerTemplate template, String accountName, Appearance app)
	{
		super(objectId, template, accountName, app);
		this.forceRunStance();
	}
	
	public void onUpdate()
	{
		if (isBusy()) return;
		
		setBusy(true);
		onDeath();
		applyBuffs();
		switch(_walkOrFarm)
		{
			case "walk":
				Location huntZone = new Location(SimPlayerTaskManager.getInstance()._farmMonsters.get(String.valueOf(this.getStatus().getLevel())).getSpawnLocation()); 
				TeleportModule.MoveInCity(this);
				TeleportModule.TeleportToLocation(this,huntZone.getX(),huntZone.getY(),huntZone.getZ(), _shouldGoHomeWhenOutCity);
				// farm when reach hungting zone
				if (this.getX()==huntZone.getX() && this.getY()==huntZone.getY() && this.getZ()==huntZone.getZ())
					this.setWalkOrFarm("farm");
				break;
			case "farm":
				// activities when level up: class transfer, learn skills, move to new loc.
				if (onLevelUp()) {
					// class transfer
					this.autoClassChange();
					// learn skills
					this.rewardSkills();
					this.setOffensiveSkills(SimPlayerAIData.getInstance().getAvailableSimPlayerAISkills());
					// move to new loc
					this.setWalkOrFarm("walk");
					break;
				}
				handleShots();
				targetNear();
				attack();
				break;
			default:
		}
		heal();
		setBusy(false);
	}
	
	private boolean onLevelUp() {
		if (_simlevel<this.getStatus().getLevel()) {
			_simlevel = this.getStatus().getLevel();
			return true;
		}
		
		return false;
	}
	
	private void autoClassChange()
	{
		if ( (this.getClassId().getLevel()!=1 && this.getStatus().getLevel()>19 && this.getStatus().getLevel()<=39) ||
			(this.getClassId().getLevel()!=2 && this.getStatus().getLevel()>39 && this.getStatus().getLevel()<=75) || 
			(this.getClassId().getLevel()!=3 && this.getStatus().getLevel()>75) ) {
			List<ClassId> childs = new ArrayList<>();
			for (ClassId _class: ClassId.VALUES)
				if (_class.getParent()==this.getClassId())
					childs.add(_class);
			int _rnd = Rnd.get(0, childs.size()-1);
			ClassId tochange = childs.get(_rnd);
			if ( (tochange.getLevel()==1 && this.getStatus().getLevel()>19 && this.getStatus().getLevel()<=39) ||
				(tochange.getLevel()==2 && this.getStatus().getLevel()>39 && this.getStatus().getLevel()<=75) || 
				(tochange.getLevel()==3 && this.getStatus().getLevel()>75) ) {
				this.setClassId(tochange.getId());
				this.setBaseClass(tochange.getId());
				this.refreshHennaList();
			}
		}
	}
	
	public void setWalkOrFarm(String _type) {
		_walkOrFarm = _type;
		_walkNodes.clear(); 
		_currentWalkNode = null;
		_isWalking = false;
		_shouldGoHomeWhenOutCity = false;
		// abort all cast & attack.
		abortAll(true);
	}
	
	public void walk() {
		if(_isWalking) {
			if(userReachedDestination(_currentWalkNode)) {	
				_currentWalkNode = null;
				_isWalking = false;
			}
			else
				this.getAI().tryToMoveTo(new Location(_currentWalkNode.getX(), _currentWalkNode.getY(), _currentWalkNode.getZ()), null);
		}
		
		if(_walkNodes.isEmpty())
			return;
		if(!_isWalking && _currentWalkNode == null) {
			_currentWalkNode = getWalkNodes().poll();
			//_walkNodes.add(_currentWalkNode);
			this.getAI().tryToMoveTo(new Location(_currentWalkNode.getX(), _currentWalkNode.getY(), _currentWalkNode.getZ()), null);
			_isWalking = true;
		}
	}
	
	public void resetWalk() {
		_currentWalkNode = null;
		_isWalking = false;
		getWalkNodes().clear();	
	}
	
	protected boolean userReachedDestination(WalkNode targetWalkNode) {
		if(this.getX() == targetWalkNode.getX()
			&& this.getY() == targetWalkNode.getY()
			&& this.getZ() == targetWalkNode.getZ())
			return true;
		
		return false;
	}
	
	public Queue<WalkNode> getWalkNodes(){
		return _walkNodes;
	}
	
	protected void addWalkNode(WalkNode walkNode) {
		_walkNodes.add(walkNode);
	}
	
	public void setWalk(boolean isWalk) {
		_isWalking = isWalk;
	}
	
	public boolean isWalk() {
		return _isWalking;
	}
	
	public void setBusy(boolean isbusy) {
		_isBusy = isbusy;
	}
	
	public boolean isBusy() {
		return _isBusy;
	}
	
	public void setMagicClass(boolean isMagicClass) {
		_isMagicClass = isMagicClass;
	}
	
	public boolean isMagicClass() {
		return _isMagicClass;
	}
	
	protected void targetNear()
	{
		if (this.getTarget() != null)
			if(((Creature)this.getTarget()).isDead())
				this.setTarget(null);
		
		// pick items first before target new
		List<ItemInstance> items = this.getKnownTypeInRadius(ItemInstance.class, 500).stream().filter(x->(x.getOwnerId()==this.getObjectId())&&!x.getItemType().toString().equals("HERB")).collect(Collectors.toList());
		for (ItemInstance item : items) {
			this.getAI().tryToPickUp(item.getObjectId(), false);
		}
		if (items.size()>0)
			return;
		
		if(this.getTarget() == null) {
			List<Creature> targets = this.getKnownTypeInRadius(Monster.class, 3000).stream().filter(x->!x.isDead()).collect(Collectors.toList());
			if(!targets.isEmpty()) {
				double minDistance = 1000000000;
				Creature neartarget = null;
				for (Creature target : targets) {
					// skip too high lvl mobs
					if (target.getStatus().getLevel()>this.getStatus().getLevel()+10)
						continue;
					if (target.isVisible() && !target.isRaidBoss() && !target.isRaidRelated() && !target.isInWater()) {
						if (target.getTarget() == this) {
							neartarget = target;
							break;
						}
						double distance = Functions.distanceBetween(this.getX(), this.getY(), this.getZ(), target.getX(), target.getY(), target.getZ());
						if (distance<minDistance) {
							minDistance = distance;
							neartarget = target;
						}
					}
				}
				if (neartarget!=null)
					this.setTarget(neartarget);
			}
		}else {
			if(((Creature)this.getTarget()).isDead())
				this.setTarget(null);
		}
	}	
	
	protected void targetRandom()
	{
		if(this.getTarget() == null) {
			List<Creature> targets = this.getKnownTypeInRadius(Monster.class, 1000).stream().filter(x->!x.isDead()).collect(Collectors.toList());
			if(!targets.isEmpty()) {
				Creature target = targets.get(Rnd.get(0, targets.size() -1 ));
				this.setTarget(target);				
			}
		}else {
			if(((Creature)this.getTarget()).isDead())
				this.setTarget(null);
		}
	}	
	
	protected void attack()	{
		if (this.getTarget()==null)
			return;
		TeleportModule.MoveToLocation(this, this.getTarget().getX(), 
											this.getTarget().getY(), 
											this.getTarget().getZ());
		if (this.isWalk())
			return;
		for (OffensiveSkill _offensiveSkill : _offensiveSkills) {
			L2Skill skill = this.getSkill(_offensiveSkill.getSkillId());
			if(skill != null && (skill.getSkillType()==SkillType.MDAM || skill.getSkillType()==SkillType.PDAM )) {
				this.getAI().tryToCast((Creature)this.getTarget(), skill);
			}
		}
		
		// try regular attack
		if (!this.isMageClass())
			this.getAI().tryToAttack((Creature)this.getTarget(), true, false);
	}
	
	protected L2Skill getRandomFighterSkill() {	
		int skillIndex = Rnd.get(0, _offensiveSkills.size() -1 );
		L2Skill skill = this.getSkill(_offensiveSkills.get(skillIndex).getSkillId());
		return skill;
	}
	
	public void setOffensiveSkills(List<SimPlayerAISkill> simPlayerAISkills) 
	{
		_offensiveSkills = new ArrayList<>();
		// skills defined in xml SimPlayerAIData
		for (SimPlayerAISkill _skill : simPlayerAISkills) {
			if (_skill.getClassID() == this.getClassId())
				try {
					if (this.getSkill(_skill.getSkill())!=null)
						_offensiveSkills.add(new OffensiveSkill(_skill.getSkill(), _skill.getPriority()));
				}catch (Exception ex) {}
		}
		
		// skills learned by level
		if (_offensiveSkills.size()==0) {
			for (var _skill : this.getSkills().entrySet()) {
				if(_skill.getValue().isOffensive()) {
					_offensiveSkills.add(new OffensiveSkill(_skill.getValue().getId(), (int)_skill.getValue().getPower()) );
				}
			}
		}
		_offensiveSkills = _offensiveSkills.stream().sorted((o1, o2)-> Integer.compare(o2.getPriority(), o1.getPriority())).collect(Collectors.toList());
		
	}
	
	public synchronized void despawn()
	{
		try
		{
			// Set the online status to false
			setOnlineStatus(false, true);
			
			// abort all cast & attack.
			abortAll(true);
			// remove the target
			setTarget(null);
			
			// remove party matching
			removeMeFromPartyMatch();
			
			// remove flying
			if (isFlying())
				removeSkill(FrequentSkill.WYVERN_BREATH.getSkill().getId(), false);
			
			// Stop all scheduled tasks
			stopChargeTask();
			
			// Cancel the cast of eventual fusion skill users on this target.
			for (Creature character : getKnownType(Creature.class))
				if (character.getFusionSkill() != null && character.getFusionSkill().getTarget() == this)
					character.abortAll(true);
				
			// Stop effects.
			for (AbstractEffect effect : getAllEffects())
			{
				if (effect.getSkill().isToggle())
				{
					effect.exit();
					continue;
				}
				
				switch (effect.getEffectType())
				{
					case SIGNET_GROUND:
					case SIGNET_EFFECT:
						effect.exit();
						break;
					default:
						break;
				}
			}
			
			// remove sim from the world
			decayMe();
			
			// leave party
			if (getParty() != null)
				getParty().removePartyMember(this, MessageType.DISCONNECTED);
			
			// remove pet
			if (getSummon() != null)
				getSummon().unSummon(this);
			
			// remove olympiad game
			if (OlympiadManager.getInstance().isRegistered(this) || getOlympiadGameId() != -1)
				OlympiadManager.getInstance().removeDisconnectedCompetitor(this);
			
			// set clan stat offline
			if (getClan() != null)
			{
				ClanMember clanMember = getClan().getClanMember(getObjectId());
				if (clanMember != null)
					clanMember.setPlayerInstance(null);
			}
			
			// remove trade
			if (getActiveRequester() != null)
			{
				setActiveRequester(null);
				cancelActiveTrade();
			}
			
			// remove gm
			if (isGM())
				AdminData.getInstance().deleteGm(this);
			
			// remove observer
			if (isInObserverMode())
				setXYZInvisible(getSavedLocation());
			
			// remove from boat
			if (getDockedBoat() != null)
				getDockedBoat().oustPlayer(this, Location.DUMMY_LOC);
			
			// remove inventory
			getInventory().deleteMe();
			
			// remove warehouse
			clearWarehouse();
			
			// remove freight
			clearFreight();
			clearDepositedFreight();
			
			// remove cursed weap
			if (isCursedWeaponEquipped())
				CursedWeaponManager.getInstance().getCursedWeapon(getCursedWeaponEquippedId()).setPlayer(null);
			
			// TODO
			//if (getClanId() > 0)
			//	getClan().broadcastToOtherOnlineMembers(new PledgeShowMemberListUpdate(this), this);
			
			// remove seated
			if (isSeated())
			{
				final WorldObject object = World.getInstance().getObject(_throneId);
				if (object instanceof StaticObject)
					((StaticObject) object).setBusy(false);
			}
			
			// force remove player
			World.getInstance().removePlayer(this); 
			
			//TODO
			// udpate friends & blocklist
			//notifyFriends(false);
			//getBlockList().playerLogout();
		}
		catch (Exception e)
		{
			LOGGER.warn(Level.WARNING, "Exception on despawn()" + e.getMessage(), e);
		}
	}
	
	public void heal() {
		int max = getStatus().getMaxHp();
		if (getStatus().getHp() < max)
			getStatus().setHp(max);
		
		max = getStatus().getMaxMp();
		if (getStatus().getMp() < max)
			getStatus().setMp(max);
		
		max = getStatus().getMaxCp();
		if (getStatus().getCp() < max)
			getStatus().setCp(max);
	}
	
	public void simPlayerJoinParty(Player requester) {
		if (requester == null)
			return;
		
		// reply to requester
		requester.sendPacket(new JoinParty(1));
		
		Party party = requester.getParty();
		if (party == null)
			party = new Party(requester, this, requester.getLootRule());
		else
			party.addPartyMember(this);
		
		// Must be kept out of "ok" answer, can't be merged with higher content.
		if (party != null)
			party.setPendingInvitation(false);
		
		this.setActiveRequester(null);
		requester.onTransactionResponse();
	}
	
	protected void onDeath() {
		if (this.isDead()) {
			this.doRevive();
			Location townloc = RestartPointData.getInstance().getLocationToTeleport(this, RestartType.TOWN);
			this.teleportTo(townloc, 20);
			this.setWalkOrFarm("walk");
		}
	}
	
	protected void handleShots() {
		if(this.getInventory().getItemByItemId(getShotId()) != null) {
			if(this.getInventory().getItemByItemId(getShotId()).getCount() <= 50) {
				this.getInventory().addItem(getShotId(), 200);			
			}
		}else {
			this.getInventory().addItem(getShotId(), 200);
		}
		
		if(this.getAutoSoulShot().isEmpty()) {
			this.addAutoSoulShot(getShotId());
			this.rechargeShots(true, true);
		}	
	}
	
	protected int getShotId() {
		try {
			if(this.getActiveWeaponItem().getCrystalType() == CrystalType.NONE)
				return !_isMagicClass ? 1835 : 3947;
			if(this.getActiveWeaponItem().getCrystalType() == CrystalType.D)
				return !_isMagicClass ? 1463 : 3948;
			if(this.getActiveWeaponItem().getCrystalType() == CrystalType.C)
				return !_isMagicClass ? 1464 : 3949;
			if(this.getActiveWeaponItem().getCrystalType() == CrystalType.B)
				return !_isMagicClass ? 1465 : 3950;
			if(this.getActiveWeaponItem().getCrystalType() == CrystalType.A)
				return !_isMagicClass ? 1466 : 3951;
			if(this.getActiveWeaponItem().getCrystalType() == CrystalType.S)
				return !_isMagicClass ? 1467 : 3952;
		}catch(Exception ex) {}
		
		return 0;
	}
	
	protected void applyBuffs() {
		int[][] _buffs = SimPlayerHelper.getCommonFighterBuffs(); 
		if (_isMagicClass) _buffs = SimPlayerHelper.getCommonMageBuffs();
		for(int[] buff : _buffs){
			try {
				Map<Integer, AbstractEffect> activeEffects = Arrays.stream(this.getAllEffects())
						//TODO
						//.filter(x->x.getEffectType() == L2EffectType.BUFF)
					.collect(Collectors.toMap(x-> x.getSkill().getId(), x->x));
			
			if(!activeEffects.containsKey(buff[0]))
				SkillTable.getInstance().getInfo(buff[0], buff[1]).getEffects(this, this);
			else {
				if((activeEffects.get(buff[0]).getPeriod() - activeEffects.get(buff[0]).getTime()) <= 20) {
					SkillTable.getInstance().getInfo(buff[0], buff[1]).getEffects(this, this);
				}
			}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}