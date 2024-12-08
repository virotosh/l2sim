package free.l2j.simfactory.taskmanager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import net.sf.l2j.commons.logging.CLogger;
import net.sf.l2j.commons.pool.ThreadPool;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.data.xml.PlayerData;
import net.sf.l2j.gameserver.idfactory.IdFactory;
import net.sf.l2j.gameserver.model.World;
import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.model.actor.container.player.Appearance;
import net.sf.l2j.gameserver.model.actor.template.PlayerTemplate;
import net.sf.l2j.gameserver.data.sql.PlayerInfoTable;

import free.l2j.simfactory.commons.SimPlayerHelper;
import free.l2j.simfactory.data.xml.SimPlayerAIData;
import free.l2j.simfactory.model.actor.SimPlayer;
import free.l2j.simfactory.model.actor.ai.attribute.SimPlayerAIClass;
import free.l2j.simfactory.model.actor.SimPlayerNameManager;
import free.l2j.simfactory.network.SimGameClient;

public class SimPlayerTaskManager implements Runnable
{
	private static final CLogger LOGGER = new CLogger(SimPlayerTaskManager.class.getName());
	List<SimPlayer> _simPlayers = new ArrayList<>();
	
	protected SimPlayerTaskManager()
	{
		LOGGER.info("Initializing SimPlayer TaskManager");
		ThreadPool.scheduleAtFixedRate(this, 1000, 1000);
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
	
	public SimPlayer spawnRandomSimPlayer(int x, int y, int z) {
		int objectId = IdFactory.getInstance().getNextId();
		String accountName = "simplayer";
		String playerName = SimPlayerNameManager.getInstance().getRandomAvailableName();
		
		SimPlayerAIClass _simClass = SimPlayerHelper.getRandomSimPlayerAIClass();
			

		final PlayerTemplate template = PlayerData.getInstance().getTemplate(_simClass.getClassID());
		Appearance app = SimPlayerHelper.getRandomAppearance(template.getRace());
		SimPlayer simPlayer = new SimPlayer(objectId, template, accountName, app);

		simPlayer.setName(playerName);
		simPlayer.setAccessLevel(Config.DEFAULT_ACCESS_LEVEL);
		PlayerInfoTable.getInstance().addPlayer(objectId, accountName, playerName, simPlayer.getAccessLevel().getLevel());
		simPlayer.setBaseClass(_simClass.getClassID());
		SimPlayerHelper.setLevel(simPlayer, 81);
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