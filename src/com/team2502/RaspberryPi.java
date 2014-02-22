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
		socket = new Socket(ip, 1180, Socket.READ_WRITE);
	}
	
	public String getAddress() {
		return address;
	}
	
	public boolean isConnected() {
		return socket.isConnected();
	}
	
	public void initialize() {
		socket.connect();
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
			t.setNormX(rArray.getDouble());
			t.setNormY(rArray.getDouble());
			t.setDistance(rArray.getDouble());
			t.setDistanceError(rArray.getDouble());
			t.setRectangularity(rArray.getDouble());
			t.setXAngle(rArray.getDouble());
			t.setYAngle(rArray.getDouble());
			list[i] = t;
		}
		return list;
	}
	
	public class Target {
		private double normX;
		private double normY;
		private double distance;
		private double distanceError;
		private double rectangularity;
		private double xAngle;
		private double yAngle;
		
		public void setNormX(double normX) { this.normX = normX; }
		public void setNormY(double normY) { this.normY = normY; }
		public void setDistance(double distance) { this.distance = distance; }
		public void setDistanceError(double err ) { this.distanceError = err; }
		public void setRectangularity(double rect) { this.rectangularity = rect; }
		public void setXAngle(double xAngle) { this.xAngle = xAngle; }
		public void setYAngle(double yAngle) { this.yAngle = yAngle; }
		
		public double getNormX() { return normX; }
		public double getNormY() { return normY; }
		public double getDistance() { return distance; }
		public double getDistanceError() { return distanceError; }
		public double getRectangularity() { return rectangularity; }
		public double getXAngle() { return xAngle; }
		public double getYAngle() { return yAngle; }
		
		public String toString() {
			String ret = "Target[";
			ret += "norm(x,y)=("+Math.floor(normX*1000+.5)/1000+","+Math.floor(normY*1000+.5)/1000+"), ";
			ret += "distance="+Math.floor(distance*1000+.5)/1000+" +/- ";
			ret += Math.floor(distanceError*1000+.5)/1000 + ", angle(x,y)=(";
			ret += Math.floor(xAngle*1000+.5)/1000+","+Math.floor(yAngle*1000+.5)/1000;
			ret += ")]";
			return ret;
		}
	}
	
	public class TargetBuilder {
		private Target target;
		public TargetBuilder() {
			target = new Target();
		}
		public Target build() {
			return target;
		}
		public TargetBuilder setNormilizedX(double normX) {
			target.normX = normX;
			return this;
		}
		public TargetBuilder setNormalizedY(double normY) {
			target.normY = normY;
			return this;
		}
		public TargetBuilder setDistance(double distance) {
			target.distance = distance;
			return this;
		}
		public TargetBuilder setDistanceError(double distanceError) {
			target.distanceError = distanceError;
			return this;
		}
		public TargetBuilder setXAngle(double xAngle) {
			target.xAngle = xAngle;
			return this;
		}
		public TargetBuilder setYAngle(double yAngle) {
			target.yAngle = yAngle;
			return this;
		}
	}
	
}
