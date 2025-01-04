package free.l2j.simfactory.taskmanager;

import net.sf.l2j.commons.logging.CLogger;
import net.sf.l2j.commons.pool.ThreadPool;

import free.l2j.simfactory.model.actor.SimPlayer;

public class SimPlayerSpawnManager implements Runnable 
{
	private static final CLogger LOGGER = new CLogger(SimPlayerSpawnManager.class.getName());
	protected SimPlayerSpawnManager()
	{
		SimPlayer simPlayer = SimPlayerTaskManager.getInstance().spawnRandomSimPlayer();
		simPlayer.getAI().tryToCast(simPlayer, 2100, 1);
		simPlayer.setWalkOrFarm("walk");
		LOGGER.info("Spawned {} sim players", SimPlayerTaskManager.getInstance().getSimPlayersCount());
		// refresh activities every 10 minutes
		ThreadPool.scheduleAtFixedRate(this, 1000*60*10, 1000*60*10);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run()
	{
		// Loop all Sims.
		try {
			SimPlayerTaskManager.getInstance().getSimPlayers().forEach(x-> x.setWalkOrFarm("walk"));
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	public static final SimPlayerSpawnManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static final class SingletonHolder
	{
		protected static final SimPlayerSpawnManager INSTANCE = new SimPlayerSpawnManager();
	}
}