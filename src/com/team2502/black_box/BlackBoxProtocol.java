package com.team2502.black_box;

import edu.wpi.first.wpilibj.communication.ModulePresence;
import edu.wpi.first.wpilibj.communication.ModulePresence.ModuleType;
import java.io.IOException;
import java.io.OutputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.SocketConnection;

/**
 *
 * @author Josh Larson
 */
public class BlackBoxProtocol {
	
	private static BlackBoxProtocol INSTANCE;
	private int updateRate = 1000 / 20; // 20hz, 50ms
	private String [] remoteIPList = new String[0];
	private int remotePort = 1180;
	private final int connectionType = Connector.WRITE;
	private int packetNum = 0;
	private long lastConnectionAttempt = 0;
	private SocketConnection comm = null;
	private OutputStream outputStream = null;
	private BlackBoxUpdater updater;
	private Thread updaterThread;
	private boolean running;
	
	private BlackBoxProtocol(String [] ips, int port, double updateRateHertz) {
		remoteIPList = ips;
		remotePort = port;
		updateRate = (int)(1000 / updateRateHertz);
		updater = new BlackBoxUpdater();
		updaterThread = new Thread(updater);
	}
	
	public static BlackBoxProtocol getInstance() {
		return INSTANCE;
	}
	
	public static void start(String [] ips, int port, double updateRateHertz) {
		if (INSTANCE == null) {
			INSTANCE = new BlackBoxProtocol(ips, port, updateRateHertz);
			INSTANCE.startAll();
		} else if (!INSTANCE.isRunning()) {
			INSTANCE.startAll();
		}
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
	
	public boolean isRunning() {
		return running;
	}
	
	public int getUpdateRate() {
		return updateRate;
	}
	
	private void startAll() {
		if (reconnectToDriverStation())
			System.out.println("BlackBoxProtocol: Established connection to driver station");
		else
			System.out.println("BlackBoxProtocol: Failed to establish connection with driver station");
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
		return System.currentTimeMillis() - lastConnectionAttempt;
	}
	
	public boolean reconnectToDriverStation() {
		for (int i = 0; i < remoteIPList.length; i++) {
			if (!isConnectedToDriverStation()) {
				if (getTimeSinceLastConnectionAttempt() >= 1000) {
					openConnection(remoteIPList[i], false);
				}
				if (isConnectedToDriverStation()) {
					System.out.println("BlackBoxProtocol: Established connection with driver station.");
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean openConnection(String ip, boolean outputError) {
		lastConnectionAttempt = System.currentTimeMillis();
		try {
			comm = (SocketConnection)Connector.open("socket://"+ip+":"+remotePort, connectionType);
			outputStream = comm.openOutputStream();
			return true;
		} catch (IOException ex) {
			outputStream = null;
			comm = null;
			if (outputError) {
				String error = toString()
						+ ": Failed to establish connection"
						+ " with driver station"
						+ " at " + ip+":"+remotePort;
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
	
	
}
