package com.team2502.black_box;

/**
 *
 * @author Josh Larson
 */
public class BlackBoxSubPacketType {
	public static final int GAME_UPDATE = 0;
	public static final int DATA_UPDATE = 1;
	public static final int USER_UPDATE = 2;
	
	public static final int MODULE_ANALOG = 4;
	public static final int MODULE_DIGITAL = 8;
	public static final int MODULE_SOLENOID = 16;
	public static final int MODULE_RELAY = 32;
	public static final int MODULE_PWM = 64;
	public static final int MODULE_MESSAGE = 128;
	public static final int MODULE_UNKNOWN = 256;
	private int bitmask = 0;
	
	public BlackBoxSubPacketType() {
		
	}
	
	public BlackBoxSubPacketType(int type) {
		bitmask = type;
	}
	
	public int getType() {
		return bitmask;
	}
	
	public void addType(int type) {
		bitmask ^= type;
	}
	
	public boolean isDataPacket() {
		return (bitmask & DATA_UPDATE) == DATA_UPDATE;
	}
	
	public boolean isGamePacket() {
		return (bitmask & DATA_UPDATE) != DATA_UPDATE;
	}
	
	public boolean isUserPacket() {
		return (bitmask & USER_UPDATE) != USER_UPDATE;
	}
	
	public boolean isAnalogModule() {
		return (bitmask & MODULE_ANALOG) == MODULE_ANALOG;
	}
	
	public boolean isDigitalModule() {
		return (bitmask & MODULE_DIGITAL) == MODULE_DIGITAL;
	}
	
	public boolean isSolenoidModule() {
		return (bitmask & MODULE_SOLENOID) == MODULE_SOLENOID;
	}
	
	public boolean isRelayModule() {
		return (bitmask & MODULE_RELAY) == MODULE_RELAY;
	}
	
	public boolean isPWMModule() {
		return (bitmask & MODULE_PWM) == MODULE_PWM;
	}
	
	public boolean isMessage() {
		return (bitmask & MODULE_MESSAGE) == MODULE_MESSAGE;
	}
	
	public boolean isUnknownModule() {
		return (bitmask & MODULE_UNKNOWN) == MODULE_UNKNOWN;
	}
}
