package free.l2j.simfactory.taskmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import net.sf.l2j.commons.logging.CLogger;
import net.sf.l2j.commons.pool.ThreadPool;
import net.sf.l2j.commons.random.Rnd;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.data.xml.PlayerData;
import net.sf.l2j.gameserver.data.xml.RestartPointData;
import net.sf.l2j.gameserver.data.xml.TeleportData;
import net.sf.l2j.gameserver.enums.actors.ClassId;
import net.sf.l2j.gameserver.enums.actors.ClassRace;
import net.sf.l2j.gameserver.idfactory.IdFactory;
import net.sf.l2j.gameserver.model.World;
import net.sf.l2j.gameserver.model.actor.Npc;
import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.model.actor.container.player.Appearance;
import net.sf.l2j.gameserver.model.actor.instance.Monster;
import net.sf.l2j.gameserver.model.actor.template.PlayerTemplate;
import net.sf.l2j.gameserver.model.location.TeleportLocation;
import net.sf.l2j.gameserver.model.restart.RestartPoint;
import net.sf.l2j.gameserver.model.spawn.MultiSpawn;
import net.sf.l2j.gameserver.model.spawn.NpcMaker;
import net.sf.l2j.gameserver.data.manager.SpawnManager;
import net.sf.l2j.gameserver.data.sql.PlayerInfoTable;

import free.l2j.simfactory.commons.SimPlayerHelper;
import free.l2j.simfactory.data.xml.SimPlayerAIData;
import free.l2j.simfactory.model.actor.SimPlayer;
import free.l2j.simfactory.model.actor.ai.attribute.SimPlayerAIClass;
import free.l2j.simfactory.model.actor.ai.preference.Functions;
import free.l2j.simfactory.model.actor.ai.preference.HuntZone;
import free.l2j.simfactory.model.actor.ai.preference.TeleportModule;
import free.l2j.simfactory.model.actor.ai.preference.TeleportModule.TCity;
import free.l2j.simfactory.model.actor.ai.preference.TeleportNode;
import free.l2j.simfactory.model.actor.SimPlayerNameManager;
import free.l2j.simfactory.network.SimGameClient;

public class SimPlayerTaskManager implements Runnable
{
	private static final CLogger LOGGER = new CLogger(SimPlayerTaskManager.class.getName());
	List<SimPlayer> _simPlayers = new ArrayList<>();
	public final Map<String, Npc> _farmMonsters = new HashMap<>();
	
	protected SimPlayerTaskManager()
	{
		LOGGER.info("Initializing SimPlayer TaskManager");
		ThreadPool.scheduleAtFixedRate(this, 1000, 1000);
		loadHuntingZonesLevel1_80();
		LOGGER.info("Loaded {} farm mobs for sim players",_farmMonsters.size());
		
	}
	@Override
	public final void run()
	{
		// Loop all Sims.
		try {
			_simPlayers.forEach(x-> x.onUpdate());	
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	public final void add(SimPlayer simPlayer)
	{
		
	}
	
	public final void remove(SimPlayer simPlayer)
	{
		
	}
	
	private final void loadHuntingZonesLevel1_80(){
		int min = 1;
		int max = 81;

		Map<String, List<Npc>> _monsters = new HashMap<>();

		Set<NpcMaker> abc = SpawnManager.getInstance().getNpcMakers();
		IntStream.range(min, max).forEachOrdered(n -> {
			_monsters.put(String.valueOf(n), new ArrayList<>());
		});
		for (NpcMaker npc : abc) {
			for (MultiSpawn spawn: npc.getSpawns()) {
				try {
					String moblevel= String.valueOf(spawn.getNpc().getStatus().getLevel());
					_monsters.get(moblevel).add(spawn.getNpc());
				}catch(Exception ex) {}
			}
		}
		
		IntStream.range(min, max).forEachOrdered(n -> {
			List<Npc> mobList = _monsters.get(String.valueOf(n));
			
			List<HuntZone> zones = new ArrayList<>();
			for (Npc mob: mobList) {
				if (!(mob instanceof Monster) || mob.isInWater() ||
					mob.isRaidBoss() || mob.isRaidRelated() || 
					mob.hasMaster() || mob.getExpReward()==0 || 
					(mob.getZ()< -4000 && mob.getZ()> -1000 ) ) // skip underground mobs
					continue;
				
				final RestartPoint rp = RestartPointData.getInstance().getRestartPoint(mob.getSpawnLocation());
		    	TCity city = TCity.NON_CITY;
		    	
		    	for (int index = 0; index < TeleportModule.RestartPointNames.length; index++)
				{	
		    		try {
			    		if (TeleportModule.RestartPointNames[index].equals(rp.getName())) {
			    			city=TeleportModule.TCities[index];
			    			break;
			    		}
		    		} catch(Exception ex) {
		    			continue;
		    		}
				}
		    	if (city.equals(TCity.NON_CITY))
		        	continue;
		        
		        Npc GK = World.getInstance().getNpc(TeleportModule.GateKeepers[city.getValue()]);
		        
		        final List<TeleportLocation> teleports = TeleportData.getInstance().getTeleports(GK.getNpcId());
		        List<TeleportNode> teleNodes = new ArrayList<>();
				for (int index = 0; index < teleports.size(); index++)
				{
					final TeleportLocation teleport = teleports.get(index);
					if (teleport == null)
						continue;
					double distance = Functions.distanceBetween(mob.getSpawnLocation().getX(), mob.getSpawnLocation().getY(), mob.getSpawnLocation().getZ(),
																				teleport.getX(), teleport.getY(), teleport.getZ());
					if (teleport.getZ()> -4000 && teleport.getZ()< -1000) {
						teleNodes.add(new TeleportNode(teleport,distance));
					}
				}
				
				TeleportNode closestTeleNode = (teleNodes.stream().sorted((o1, o2)-> Double.compare(o1.getDistance(), o2.getDistance())).collect(Collectors.toList())).get(0);
				
				zones.add(new HuntZone(mob,closestTeleNode.getDistance()));
			}
			HuntZone closestMob = (zones.stream().sorted((o1, o2)-> Double.compare(o1.getDistance(), o2.getDistance())).collect(Collectors.toList())).get(0);
			_farmMonsters.put(String.valueOf(n), closestMob.getMonster());
			if (closestMob.getDistance()>=8000 && n>1) // skip too far mobs
				_farmMonsters.put(String.valueOf(n), _farmMonsters.get(String.valueOf(n-1)));
		});
		
	}
	
	public SimPlayer spawnRandomSimPlayer(int x, int y, int z) {
		int objectId = IdFactory.getInstance().getNextId();
		String accountName = "simplayer";
		String playerName = SimPlayerNameManager.getInstance().getRandomAvailableName();
		int level = Rnd.get(1,80);
		
		SimPlayerAIClass _simClass = SimPlayerHelper.getRandomSimPlayerAIClass(level);
			

		final PlayerTemplate template = PlayerData.getInstance().getTemplate(_simClass.getClassID());
		Appearance app = SimPlayerHelper.getRandomAppearance(template.getRace());
		SimPlayer simPlayer = new SimPlayer(objectId, template, accountName, app);

		simPlayer.setName(playerName);
		simPlayer.setAccessLevel(Config.DEFAULT_ACCESS_LEVEL);
		PlayerInfoTable.getInstance().addPlayer(objectId, accountName, playerName, simPlayer.getAccessLevel().getLevel());
		simPlayer.setBaseClass(_simClass.getClassID());
		SimPlayerHelper.setLevel(simPlayer, level);
		simPlayer.rewardSkills();
		simPlayer.setOffensiveSkills(SimPlayerAIData.getInstance().getAvailableSimPlayerAISkills());
		simPlayer.setMagicClass(_simClass.isMagicClass());
		
		SimPlayerHelper.giveArmorsByClass(simPlayer);
		SimPlayerHelper.giveWeaponsByClass(simPlayer,true);
		
		World.getInstance().addPlayer(simPlayer);
		
		if (Config.PLAYER_SPAWN_PROTECTION > 0)
			simPlayer.setSpawnProtection(true);
		
		simPlayer.setClient(new SimGameClient(simPlayer));
		simPlayer.spawnMe(x, y, z);
		simPlayer.onPlayerEnter();
		simPlayer.heal();
		
		_simPlayers.add(simPlayer);
		return simPlayer;
	}
	
	public SimPlayer spawnRandomSimPlayer() {
		int objectId = IdFactory.getInstance().getNextId();
		String accountName = "simplayer";
		String playerName = SimPlayerNameManager.getInstance().getRandomAvailableName();
		int level = 1;
		
		SimPlayerAIClass _simClass = SimPlayerHelper.getRandomSimPlayerAIClass(level);
			

		final PlayerTemplate template = PlayerData.getInstance().getTemplate(_simClass.getClassID());
		Appearance app = SimPlayerHelper.getRandomAppearance(template.getRace());
		SimPlayer simPlayer = new SimPlayer(objectId, template, accountName, app);

		simPlayer.setName(playerName);
		simPlayer.setAccessLevel(Config.DEFAULT_ACCESS_LEVEL);
		PlayerInfoTable.getInstance().addPlayer(objectId, accountName, playerName, simPlayer.getAccessLevel().getLevel());
		simPlayer.setBaseClass(_simClass.getClassID());
		SimPlayerHelper.setLevel(simPlayer, level);
		simPlayer.rewardSkills();
		simPlayer.setOffensiveSkills(SimPlayerAIData.getInstance().getAvailableSimPlayerAISkills());
		simPlayer.setMagicClass(_simClass.isMagicClass());
		
		SimPlayerHelper.giveArmorsByClass(simPlayer);
		SimPlayerHelper.giveWeaponsByClass(simPlayer,true);
		
		World.getInstance().addPlayer(simPlayer);
		
		if (Config.PLAYER_SPAWN_PROTECTION > 0)
			simPlayer.setSpawnProtection(true);
		
		simPlayer.setClient(new SimGameClient(simPlayer));
		
		int npcId = 30006; // HUMAN
		if(_simClass.getClassID().getRace().equals(ClassRace.ELF) )
			npcId = 30146;
		if(_simClass.getClassID().getRace().equals(ClassRace.DARK_ELF) )
			npcId = 30134;
		if(_simClass.getClassID().getRace().equals(ClassRace.DWARF))
			npcId = 30540;
		if(_simClass.getClassID().getRace().equals(ClassRace.ORC) )
			npcId = 30576;
		Npc _npc = World.getInstance().getNpc(npcId);
		simPlayer.spawnMe(_npc.getX()+Rnd.get(-50, 50), _npc.getY()+Rnd.get(-50, 50), _npc.getZ());
		simPlayer.onPlayerEnter();
		simPlayer.heal();
		
		_simPlayers.add(simPlayer);
		return simPlayer;
	}

	public void despawnSimPlayer(int objectId) {
		Player player = World.getInstance().getPlayer(objectId);
		if (player instanceof SimPlayer) {
			SimPlayer simPlayer = (SimPlayer) player;
			_simPlayers.remove(_simPlayers.indexOf(simPlayer));
			simPlayer.despawn();
		}
	}


	public int getSimPlayersCount() {
		return getSimPlayers().size();
	}

	public List<SimPlayer> getSimPlayers() {
		//return _simPlayers;
		return World.getInstance().getPlayers().stream().filter(x -> x instanceof SimPlayer).map(x -> (SimPlayer) x)
				.collect(Collectors.toList());
	}
	
	public static final SimPlayerTaskManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static final class SingletonHolder
	{
		protected static final SimPlayerTaskManager INSTANCE = new SimPlayerTaskManager();
	}

}