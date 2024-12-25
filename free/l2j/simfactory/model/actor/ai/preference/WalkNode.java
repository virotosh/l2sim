package free.l2j.simfactory.model.actor.ai.preference;

public class WalkNode {
	private int _x;
	private int _y;
	private int _z;
	
	public WalkNode(int x, int y, int z) {
		_x = x;
		_y = y;
		_z = z;
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
}