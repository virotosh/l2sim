package free.l2j.simfactory.network;

import java.nio.ByteBuffer;

import net.sf.l2j.commons.mmocore.ReceivablePacket;

import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.network.GameClient;
import net.sf.l2j.gameserver.network.serverpackets.L2GameServerPacket;

import free.l2j.simfactory.model.actor.SimPlayer;

public class SimGameClient extends GameClient{

	private SimPlayer simplayer;
	
	public SimGameClient(SimPlayer simplayer) {
        super(null);
        this.simplayer = simplayer;
    }
	
	@Override
	public String toString() {
	    return "Bot Client toString()";
	}

	@Override
	public boolean decrypt(ByteBuffer buf, int size) {
	    return true;
	}

	@Override
	public boolean encrypt(ByteBuffer buf, int size) {
	    return true;
	}

	@Override
	public byte[] enableCrypt() {
	    return new byte[0];
	}

	@Override
	public void setState(GameClientState pState) {
	    
	}

	@Override
	public void sendPacket(L2GameServerPacket gsp) {
	    
	}

	@Override
	public byte markToDeleteChar(int slot) {
	    return 0;
	}

	@Override
	public void markRestoredChar(int slot) {
	    
	}

	@Override
	public Player loadCharFromDisk(int slot) {
	    return simplayer;
	}

	@Override
	public void close(L2GameServerPacket gsp) {
	    
	}

	@Override
	public void closeNow() {
	    
	}

	@Override
	public void cleanMe(boolean fast) {
	    
	}

	@Override
	public boolean dropPacket() {
	    return false;
	}

	@Override
	public void onBufferUnderflow() {
	    
	}

	@Override
	public void onUnknownPacket() {
	    
	}

	@Override
	public void execute(ReceivablePacket<GameClient> packet) {
	    
	}

	@Override
	public boolean isDetached() {
	    return false;
	}
	
}