package com.team2502.black_box;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;
import javax.microedition.io.Connector;
import javax.microedition.io.SocketConnection;

/**
 *
 * @author Josh Larson
 */
public class BlackBoxProtocol {
	
	private static BlackBoxProtocol INSTANCE;
	private static boolean started;
	private int updateRate = 1000 / 20; // 20hz, 50ms
	private String [] remoteIPList = new String[0];
	private int remotePort = 1180;
	private final int connectionType = Connector.WRITE;
	private int packetNum = 0;
	private long [] lastConnectionAttempt = new long[0];
	private SocketConnection comm = null;
	private OutputStream outputStream = null;
	private BlackBoxUpdater updater;
	private Thread updaterThread;
	private boolean running;
	
	private BlackBoxProtocol() {
		updater = new BlackBoxUpdater();
		updaterThread = new Thread(updater);
	}
	
	private void initialize(String [] ips, int port, double updateRateHertz) {
		remoteIPList = ips;
		remotePort = port;
		lastConnectionAttempt = new long[ips.length];
		updateRate = (int)(1000 / updateRateHertz);
	}
	
	public static BlackBoxProtocol getInstance() {
		return INSTANCE;
	}
	
	public static void initialize() {
		if (INSTANCE == null)
			INSTANCE = new BlackBoxProtocol();
	}
	
	public static void start(String [] ips, int port, double updateRateHertz) {
		if (started)
			return;
		INSTANCE.initialize(ips, port, updateRateHertz);
		started = true;
	}
	
	public static void start(String ip, int port, double updateRateHertz) {
		start(new String[]{ip}, port, updateRateHertz);
	}
	
	public static void stop() {
		if (INSTANCE != null)
			INSTANCE.stopAll();
	}
	
	public static void log(String message) {
		if (INSTANCE == null)
			return;
		INSTANCE.updater.addMessage(message);
	}
	
	public static boolean isStarted() {
		return started;
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public int getUpdateRate() {
		return updateRate;
	}
	
	private void startAll() {
		updaterThread.start();
		running = true;
	}
	
	private void stopAll() {
		try {
			outputStream.close();
			comm.close();
		} catch (IOException ex) {
			System.out.println(toString() + ": Failed to close connection with driver station");
		}
		updaterThread.interrupt();
		running = false;
	}
	
	public boolean isConnectedToDriverStation() {
		return comm != null && outputStream != null;
	}
	
	public long getTimeSinceLastConnectionAttempt() {
		if (lastConnectionAttempt.length < 1)
			return -1;
		long shortest = System.currentTimeMillis() - lastConnectionAttempt[0];
		for (int i = 1; i < lastConnectionAttempt.length; i++) {
			long cur = System.currentTimeMillis() - lastConnectionAttempt[i];
			if (cur < shortest)
				shortest = cur;
		}
		return shortest;
	}
	
	public boolean reconnectToDriverStation() {
		if (getTimeSinceLastConnectionAttempt() >= 1000) {
			for (int i = 0; i < remoteIPList.length; i++) {
				if (!isConnectedToDriverStation()) {
					if (openConnection(i, false))
						return true;
				}
			}
		}
		return false;
	}
	
	private boolean openConnection(int index, boolean outputError) {
		System.out.println("Attempting to connect to " + remoteIPList[index]);
		if (index < 0 || index >= lastConnectionAttempt.length)
			return false;
		lastConnectionAttempt[index] = System.currentTimeMillis();
		try {
			// This is my version of a "timeout" for 5000ms
			//Timer t = new Timer();
			//t.schedule(new TimeoutTask(Thread.currentThread()), 5000);
			// This is what is being timed out
			comm = (SocketConnection)Connector.open("socket://"+remoteIPList[index]+":"+remotePort, connectionType, true);
			outputStream = comm.openOutputStream();
			return true;
		} catch (Exception ex) {
			outputStream = null;
			comm = null;
			if (outputError) {
				String error = toString()
						+ ": Failed to establish connection"
						+ " with driver station"
						+ " at " + remoteIPList[index]+":"+remotePort;
				System.out.println(error);
			}
			return false;
		}
	}
	
	public void sendPacket(BlackBoxPacket packet) {
		if (!isConnectedToDriverStation()) return;
		try {
			outputStream.write(packet.encode(packetNum++));
		} catch (IOException ex) {
			String error = toString() + ": IO Error while sending packet #" + packetNum;
			try {
				error += " to " + comm.getAddress();
			} catch (IOException ex1) {
				
			}
			System.out.println(error);
			outputStream = null;
			comm = null;
		}
	}
	
	public String toString() {
		return "BlackBoxProtocol";
	}
	
	
	private class TimeoutTask extends TimerTask {
		
		private Thread thread;
		
		public TimeoutTask(Thread thread) {
			this.thread = thread;
		}
		
		public void run() {
			System.out.println("TRYING TO TIME OUT!");
			thread.interrupt();
		}
	}
	
}
