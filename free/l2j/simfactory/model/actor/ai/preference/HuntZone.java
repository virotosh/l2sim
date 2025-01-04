package free.l2j.simfactory.model.actor.ai.preference;

import net.sf.l2j.gameserver.model.actor.Npc;

public class HuntZone {
	protected Npc _mob;
	protected double _distance;
	
	public HuntZone(Npc mob, double distance) {
		_mob = mob;
		_distance = distance;
	}
	
	public Npc getMonster() {
		return _mob;
	}	
	
	public double getDistance() {
		return _distance;
	}
}