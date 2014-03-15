package com.team2502;

import com.team2502.Socket.SocketCallback;
import com.team2502.black_box.BlackBoxProtocol;
import edu.wpi.first.wpilibj.image.NIVision.Range;


/**
 *
 * @author Josh Larson
 */
public class RaspberryPi implements SocketCallback {
	
	private static final int INITIALIZE_ID         = 1;
	private static final int START_ID              = 2;
	private static final int STOP_ID               = 3;
	private static final int MATCH_ID              = 4;
	private static final int TARGET_REQUEST_ID     = 5;
	private static final int TARGET_HEADER_ID      = 6;
	private static final int TARGET_DATA_ID        = 7;
	private static final int SET_BRIGHTNESS_ID     = 8;
	private static final int SET_THRESHOLD_ID      = 9;
	private static final int SET_COMPETITION_ID    = 10;
	private static final int SET_GAME_RECORDING_ID = 11;
	private PiCallback callback;
	private Socket socket;
	private String address;
	private boolean initialized;
	
	private int brightness;
	private Range thresholdRange;
	private int width;
	private int height;
	private boolean gameRecording;
	private boolean competitionMode;
	
	public RaspberryPi(String ip, PiCallback callback) {
		socket = new Socket(ip, 1180);
		socket.setCallback(this);
		brightness = 15;
		thresholdRange = new Range(1, 255);
		width = 640;
		height = 480;
		gameRecording = false;
		competitionMode = true;
		this.callback = callback;
	}
	
	private void sendInitializeData() {
		sendInitialize();
		sendStartProcessing();
		sendThreshold(thresholdRange.getLower(), thresholdRange.getUpper());
		sendGameRecording(false);
		sendCompetitionMode(competitionMode);
		sendSize(width, height);
	}
	
	public void start() {
		socket.start();
	}
	
	public void stop() {
		socket.close();
	}
	
	public boolean isStarted() {
		return socket.isStarted();
	}
	
	public String getAddress() {
		return address;
	}
	
	public boolean isConnected() {
		return socket.isConnected();
	}
	
	public void setBrightness(int brightness) {
		if (brightness != this.brightness) {
			sendBrightness(brightness);
			BlackBoxProtocol.log("Set brightness to " + brightness);
		}
		this.brightness = brightness;
	}
	
	public void setThreshold(int min, int max) {
		if (min != thresholdRange.getLower() || max != thresholdRange.getUpper()) {
			sendThreshold(min, max);
			BlackBoxProtocol.log("Set Threshold to " + min + ", " + max);
		}
		thresholdRange.set(min, max);
	}
	
	public void setSize(int width, int height) {
		if (width != this.width || height != this.height) {
			sendSize(width, height);
			BlackBoxProtocol.log("Set size to " + width + "x" + height);
		}
		this.width = width;
		this.height = height;
	}
	
	public void setGameRecording(boolean on) {
		if (gameRecording != on)
			sendGameRecording(on);
		gameRecording = on;
	}
	
	public void setCompetitionMode(boolean mode) {
		if (competitionMode != mode)
			sendCompetitionMode(mode);
		competitionMode = mode;
	}
	
	public int getBrightness() {
		return brightness;
	}
	
	public Range getRange() {
		return new Range(thresholdRange.getLower(), thresholdRange.getUpper());
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public boolean isGameRecording() {
		return gameRecording;
	}
	
	private void onTargetsReceived(byte [] data) {
		ByteBuffer rArray = ByteBuffer.wrap(data);
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
		callback.onReceiveTargetList(list);
	}
	
	public void onConnected() {
		sendInitializeData();
	}
	
	public void onDisconnected() {
		
	}
	
	public void onRead(int opID, byte [] data) {
		switch (opID) {
			case TARGET_HEADER_ID:
				if (socket != null && data != null && data.length >= 2)
					socket.read(TARGET_DATA_ID, data[1]);
				break;
			case TARGET_DATA_ID:
				onTargetsReceived(data);
				break;
		}
	}
	
	public void onWrite(int opID, boolean success) {
		
	}
	
	private void sendInitialize() {
		ByteBuffer wArray = ByteBuffer.allocate(2);
		wArray.put((byte)1);
		wArray.put((byte)0);
		socket.write(INITIALIZE_ID, wArray.array());
	}
	
	private void sendStartProcessing() {
		ByteBuffer wArray = ByteBuffer.allocate(2);
		wArray.put((byte)2);
		wArray.put((byte)0);
		socket.write(START_ID, wArray.array());
	}
	
	private void sendStopProcessing() {
		ByteBuffer wArray = ByteBuffer.allocate(2);
		wArray.put((byte)3);
		wArray.put((byte)0);
		socket.write(STOP_ID, wArray.array());
	}
	
	public void sendMatchData(ByteBuffer data) {
		byte [] sData = new byte[data.length()+2];
		sData[0] = 7;
		sData[1] = (byte)data.length();
		System.arraycopy(data.array(), 0, sData, 2, data.length());
		socket.write(MATCH_ID, data.array());
	}
	
	private void sendGameRecording(boolean recording) {
		ByteBuffer wArray = ByteBuffer.allocate(4);
		wArray.put((byte)6); // Set Values
		wArray.put((byte)2); // Length
		wArray.put((byte)4); // Game Recording
		wArray.put((byte)(recording ? 1 : 0));
		socket.write(SET_GAME_RECORDING_ID, wArray.array());
	}
	
	private void sendBrightness(int brightness) {
		ByteBuffer wArray = ByteBuffer.allocate(4);
		wArray.put((byte)6); // Set Values
		wArray.put((byte)2); // Length
		wArray.put((byte)1); // Brightness
		wArray.put((byte)brightness);
		socket.write(SET_BRIGHTNESS_ID, wArray.array());
	}
	
	private void sendThreshold(int threshLow, int threshHigh) {
		ByteBuffer wArray = ByteBuffer.allocate(5);
		wArray.put((byte)6); // Set Values
		wArray.put((byte)3); // Length
		wArray.put((byte)2); // Threshold
		wArray.put((byte)threshLow);
		wArray.put((byte)threshHigh);
		socket.write(SET_THRESHOLD_ID, wArray.array());
	}
	
	private void sendCompetitionMode(boolean competitionMode) {
		ByteBuffer wArray = ByteBuffer.allocate(4);
		wArray.put((byte)6); // Set Values
		wArray.put((byte)2); // Length
		wArray.put((byte)2); // Threshold
		wArray.put((byte)(competitionMode ? 1 : 0));
		socket.write(SET_COMPETITION_ID, wArray.array());
	}
	
	private void sendSize(int width, int height) {
		ByteBuffer wArray = ByteBuffer.allocate(11);
		wArray.put((byte)6); // Set Values
		wArray.put((byte)3); // Length
		wArray.put((byte)2); // Threshold
		wArray.putInt(width);
		wArray.putInt(height);
		socket.write(SET_THRESHOLD_ID, wArray.array());
	}
	
	public void requestTargetList() {
		// Send packet to request targets
		ByteBuffer wArray = ByteBuffer.allocate(2);
		wArray.put((byte)4);
		wArray.put((byte)0);
		socket.write(TARGET_REQUEST_ID, wArray.array());
		// Get target list
		socket.read(TARGET_HEADER_ID, 2);
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
	
	public interface PiCallback {
		public void onReceiveTargetList(Target [] list);
	}
}
