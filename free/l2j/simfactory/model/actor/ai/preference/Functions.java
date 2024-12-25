package free.l2j.simfactory.model.actor.ai.preference;

import net.sf.l2j.gameserver.model.location.Location;

import free.l2j.simfactory.model.actor.SimPlayer;

public class Functions {
	public static double distanceBetween(Location a1, Location a2) {
        return Math.sqrt(Math.pow((a2.getX() - a1.getX()), 2) + Math.pow((a2.getY() - a1.getY()), 2) + Math.pow((a2.getZ() - a1.getZ()), 2));
    }
	
	public static double distanceBetween(long x1, long y1, long z1, long x2, long y2, long z2) {
        return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2) + Math.pow((z2 - z1), 2));
    }
	
	public static boolean inRange(SimPlayer player, int x1, int y1, int z1, int range) {
        return distanceBetween(player.getX(), player.getY(), player.getZ(), x1, y1, z1) < range;
    }
	
	/*public static void MoveAndWait(SimPlayer player, int x, int y, int z) {
        Engine.MoveTo(x, y, z);
        for (int i = 0; i < Party.Chars.Count; i++) {
            int x1 = User.X;
            int y1 = User.Y;
            int z1 = User.Z;
            int x2 = Party.Chars.Items[i].X;
            int y2 = Party.Chars.Items[i].Y;
            int z2 = Party.Chars.Items[i].Z;

            if (distanceBetween(x1, y1, z1, x2, y2, z2) < 2000) {
                while (!(distanceBetween(x1, y1, z1, x2, y2, z2) < 150)) {
                    x2 = Party.Chars.Items[i].X;
                    y2 = Party.Chars.Items[i].Y;
                    z2 = Party.Chars.Items[i].Z;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }*/
}