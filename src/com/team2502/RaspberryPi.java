package com.team2502;


/**
 *
 * @author Josh Larson
 */
public class RaspberryPi {
	
	private Socket socket;
	private String address;
	private boolean initialized;
	
	public RaspberryPi(String ip) {
		socket = new Socket(ip, 1180);
	}
	
	public String getAddress() {
		return address;
	}
	
	public boolean isConnected() {
		return socket.isConnected();
	}
	
	public void initialize() {
		ByteBuffer wArray = ByteBuffer.allocate(2);
		wArray.put((byte)1);
		wArray.put((byte)0);
		socket.write(wArray.array());
	}
	
	public void startProcessing() {
		ByteBuffer wArray = ByteBuffer.allocate(2);
		wArray.put((byte)2);
		wArray.put((byte)0);
		socket.write(wArray.array());
	}
	
	public void stopProcessing() {
		ByteBuffer wArray = ByteBuffer.allocate(2);
		wArray.put((byte)3);
		wArray.put((byte)0);
		socket.write(wArray.array());
	}
	
	public void sendMatchData(ByteBuffer data) {
		byte [] sData = new byte[data.length()+2];
		sData[0] = 7;
		sData[1] = (byte)data.length();
		System.arraycopy(data.array(), 0, sData, 2, data.length());
		socket.write(data.array());
	}
	
	public void setGameRecording(boolean recording) {
		ByteBuffer wArray = ByteBuffer.allocate(4);
		wArray.put((byte)6); // Set Values
		wArray.put((byte)2); // Length
		wArray.put((byte)4); // Game Recording
		wArray.put((byte)(recording ? 1 : 0));
		socket.write(wArray.array());
	}
	
	public void setBrightness(int brightness) {
		ByteBuffer wArray = ByteBuffer.allocate(4);
		wArray.put((byte)6); // Set Values
		wArray.put((byte)2); // Length
		wArray.put((byte)1); // Brightness
		wArray.put((byte)brightness);
		socket.write(wArray.array());
	}
	
	public void setThreshold(int threshLow, int threshHigh) {
		ByteBuffer wArray = ByteBuffer.allocate(5);
		wArray.put((byte)6); // Set Values
		wArray.put((byte)3); // Length
		wArray.put((byte)2); // Threshold
		wArray.put((byte)threshLow);
		wArray.put((byte)threshHigh);
		socket.write(wArray.array());
	}
	
	public void setCompetitionMode(boolean competitionMode) {
		ByteBuffer wArray = ByteBuffer.allocate(4);
		wArray.put((byte)6); // Set Values
		wArray.put((byte)2); // Length
		wArray.put((byte)2); // Threshold
		wArray.put((byte)(competitionMode ? 1 : 0));
		socket.write(wArray.array());
	}
	
	public Target [] getTargetList() {
		// Send packet to request targets
		ByteBuffer wArray = ByteBuffer.allocate(2);
		wArray.put((byte)4);
		wArray.put((byte)0);
		socket.write(wArray.array());
		// Get target list
		ByteBuffer rArray = socket.readNextBundle();
		int targetCount = rArray.get();
		Target [] list = new Target[targetCount];
		for (int i = 0; i < targetCount; i++) {
			Target t = new Target();
			t.normX = rArray.getDouble();
			t.normY = rArray.getDouble();
			t.xAngle = rArray.getDouble();
			t.yAngle = rArray.getDouble();
			t.angle = rArray.getDouble();
			list[i] = t;
		}
		return list;
	}
	
	public class Target {
		public double normX;
		public double normY;
		public double xAngle;
		public double yAngle;
		public double angle;
		
		public String toString() {
			String ret = "Target[";
			ret += "norm(x,y)=("+Math.floor(normX*1000+.5)/1000+","+Math.floor(normY*1000+.5)/1000+"), ";
			ret += Math.floor(xAngle*1000+.5)/1000+","+Math.floor(yAngle*1000+.5)/1000;
			ret += ")]";
			return ret;
		}
	}
}
