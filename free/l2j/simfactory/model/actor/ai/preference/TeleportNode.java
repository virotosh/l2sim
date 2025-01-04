package free.l2j.simfactory.model.actor.ai.preference;

import net.sf.l2j.gameserver.model.location.TeleportLocation;

public class TeleportNode {
	protected TeleportLocation _loc;
	protected double _distance;
	
	public TeleportNode(TeleportLocation loc, double distance) {
		_loc = loc;
		_distance = distance;
	}
	
	public TeleportLocation getLocation() {
		return _loc;
	}	
	
	public double getDistance() {
		return _distance;
	}
}