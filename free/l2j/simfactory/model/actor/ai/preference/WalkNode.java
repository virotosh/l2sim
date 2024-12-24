package free.l2j.simfactory.model.actor.ai.preference;

import net.sf.l2j.commons.random.Rnd;

public class WalkNode {
	private int _x;
	private int _y;
	private int _z;
	private int _stayIterations;
	
	public WalkNode(int x, int y, int z) {
		_x = x;
		_y = y;
		_z = z;
		_stayIterations = Rnd.get(1, 10);
	}
	
	public int getX() {
		return _x;
	}
	
	public int getY() {
		return _y;
	}
	
	public int getZ() {
		return _z;
	}
	
	public int getStayIterations() {
		return _stayIterations;
	}
}