package com.team2502.black_box;

import edu.wpi.first.wpilibj.networktables2.util.HalfQueue;


/**
 *
 * @author Josh Larson
 */
public class BlackBoxPacket {
	
	private HalfQueue packetQueue;
//	private ArrayList packetQueue;
	private int dataSize = 0;
	private final int headerSize = 0;
	private final int footerSize = 0;
	
	public BlackBoxPacket() {
		packetQueue = new HalfQueue(20);
//		packetQueue = new ArrayList(20);
	}
	
	public void addPacket(BlackBoxSubPacket subpacket) {
		dataSize += subpacket.getLength();
		packetQueue.queue(subpacket);
//		packetQueue.add(subpacket);
	}
	
	public byte [] encode(int packetNum) {
		int length = headerSize + dataSize + footerSize;
		byte [] data = new byte[length];
		int pos = 0;
		generateHeader(data, pos, headerSize, packetNum);
		pos += headerSize;
		for (int i = 0; i < packetQueue.size(); i++) {
			byte [] dataToCopy = ((BlackBoxSubPacket)packetQueue.array[i]).getData();
//			byte [] dataToCopy = ((BlackBoxSubPacket)packetQueue.get(i)).getData();
			System.arraycopy(dataToCopy, 0, data, pos, dataToCopy.length);
			pos += dataToCopy.length;
		}
		generateFooter(data, pos, footerSize);
		pos += footerSize;
		packetQueue.clear();
		dataSize = 0;
		return data;
	}
	
	public void decode(byte [] data) {
		
	}
	
	private void generateHeader(byte [] data, int pos, int len, int packetSize) {
		if (len < headerSize) return;
		if (pos < 0 || pos + headerSize > data.length) return;
		if (headerSize == 0) return;
		byte [] packetSizeBytes = intToByteArray(packetSize);
		data[pos+0] = packetSizeBytes[0];
		data[pos+1] = packetSizeBytes[1];
		data[pos+2] = packetSizeBytes[2];
		data[pos+3] = packetSizeBytes[3];
	}
	
	private void generateFooter(byte [] data, int pos, int len) {
		if (len < footerSize) return;
		if (pos < 0 || pos + footerSize > data.length) return;
		if (footerSize == 0) return;
		for (int i = 0; i < footerSize; i++)
			data[pos+i] = (byte)0xFF;
	}
	
	public static byte[] intToByteArray(int value) {
		byte [] b = new byte[4];
		for (int i = 0; i < 4; i++) {
			int offset = (b.length - 1 - i) * 8;
			b[i] = (byte) ((value >>> offset) & 0xFF);
		}
		return b;
	}
	
	public static String getHexString(byte [] bytes) {
		final char [] hexArray = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
		StringBuffer ret = new StringBuffer();
		int v;
		for (int i = 0; i < bytes.length; i++) {
			v = bytes[i] & 0xFF;
			ret.append(hexArray[v >>> 4]);
			ret.append(hexArray[v & 0x0F]);
			ret.append(' ');
		}
		return new String(ret);
	}
	
}
