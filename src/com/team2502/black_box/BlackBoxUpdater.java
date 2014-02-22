package com.team2502.black_box;

import edu.wpi.first.wpilibj.communication.ModulePresence;
import edu.wpi.first.wpilibj.communication.ModulePresence.ModuleType;

/**
 *
 * @author Josh Larson
 */
public class BlackBoxUpdater implements Runnable {
	
	private String [] messageList = new String[0];
	private boolean running = false;
	
	public BlackBoxUpdater() {
		
	}
	
	public void stop() {
		running = false;
	}
	
	public void run() {
		running = true;
		while (running) {
			if (BlackBoxProtocol.getInstance().isConnectedToDriverStation()) {
				BlackBoxPacket gamePacket = new BlackBoxPacket();
				addGamePacket(gamePacket);
				BlackBoxPacket dataPacket = new BlackBoxPacket();
				addAnalogPackets(dataPacket);
				addDigitalPackets(dataPacket);
				addSolenoidPackets(dataPacket);
				BlackBoxPacket messagePacket = new BlackBoxPacket();
				synchronized (messageList) {
					for (int i = 0; i < messageList.length; i++) {
						String message = (String)messageList[i];
						messagePacket.addPacket(BlackBoxSubPacket.createPacketForMessage(message));
					}
					messageList = new String[0];
				}
				BlackBoxProtocol.getInstance().sendPacket(gamePacket);
				BlackBoxProtocol.getInstance().sendPacket(dataPacket);
				BlackBoxProtocol.getInstance().sendPacket(messagePacket);
			} else {
				BlackBoxProtocol.getInstance().reconnectToDriverStation();
			}
			try {
				Thread.sleep(BlackBoxProtocol.getInstance().getUpdateRate());
			} catch (InterruptedException ex) {
				running = false;
			}
		}
	}
	
	public void addMessage(String message) {
		synchronized (messageList) {
			String [] nList = new String[messageList.length+1];
			System.arraycopy(messageList, 0, nList, 0, messageList.length);
			nList[nList.length-1] = message;
			messageList = nList;
		}
	}
	
	private void addGamePacket(BlackBoxPacket packet) {
		BlackBoxSubPacketType packetType = new BlackBoxSubPacketType(0);
		packetType.addType(BlackBoxSubPacketType.GAME_UPDATE);
		packet.addPacket(BlackBoxSubPacket.getInstance(packetType.getType()));
	}
	
	private void addAnalogPackets(BlackBoxPacket packet) {
		for (int i = 0; i < 4; i++) {
			if (!hasAnalogModule(i)) continue;
			BlackBoxSubPacketType packetType = new BlackBoxSubPacketType(0);
			packetType.addType(BlackBoxSubPacketType.DATA_UPDATE);
			packetType.addType(BlackBoxSubPacketType.MODULE_ANALOG);
			packet.addPacket(BlackBoxSubPacket.getInstance(packetType.getType(), i+1));
		}
	}
	
	private void addDigitalPackets(BlackBoxPacket packet) {
		for (int i = 0; i < 4; i++) {
			if (!hasDigitalModule(i)) { continue; }
			BlackBoxSubPacketType packetType = new BlackBoxSubPacketType(0);
			packetType.addType(BlackBoxSubPacketType.DATA_UPDATE);
			packetType.addType(BlackBoxSubPacketType.MODULE_DIGITAL);
			packet.addPacket(BlackBoxSubPacket.getInstance(packetType.getType(), i));
			createPWMPacket(i, packet);
			createRelayPacket(i, packet);
		}
	}
	
	private void createPWMPacket(int module, BlackBoxPacket packet) {
		BlackBoxSubPacketType packetType = new BlackBoxSubPacketType(0);
		packetType.addType(BlackBoxSubPacketType.DATA_UPDATE);
		packetType.addType(BlackBoxSubPacketType.MODULE_PWM);
		packet.addPacket(BlackBoxSubPacket.getInstance(packetType.getType(), module));
	}
	
	private void createRelayPacket(int module, BlackBoxPacket packet) {
		BlackBoxSubPacketType packetType = new BlackBoxSubPacketType(0);
		packetType.addType(BlackBoxSubPacketType.DATA_UPDATE);
		packetType.addType(BlackBoxSubPacketType.MODULE_RELAY);
		packet.addPacket(BlackBoxSubPacket.getInstance(packetType.getType(), module));
	}
	
	private void addSolenoidPackets(BlackBoxPacket packet) {
		for (int i = 0; ; i++) {
			if (!ModulePresence.getModulePresence(ModuleType.kSolenoid, i+1)) break;
			BlackBoxSubPacketType packetType = new BlackBoxSubPacketType(0);
			packetType.addType(BlackBoxSubPacketType.DATA_UPDATE);
			packetType.addType(BlackBoxSubPacketType.MODULE_SOLENOID);
			packet.addPacket(BlackBoxSubPacket.getInstance(packetType.getType(), i+1));
		}
	}
	
	private boolean hasAnalogModule(int moduleNum) {
		return ModulePresence.getModulePresence(ModuleType.kDigital, moduleNum);
	}
	
	private boolean hasDigitalModule(int moduleNum) {
		return ModulePresence.getModulePresence(ModuleType.kDigital, moduleNum);
	}
	
}
