package com.team2502.black_box;

import edu.wpi.first.wpilibj.AnalogModule;
import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.SolenoidBase;


/**
 *
 * @author Josh Larson
 */
public class BlackBoxSubPacket {
	
	private static final int headerSize = 2;
	private int type = 0;
	private byte [] data = null;
	
	private BlackBoxSubPacket() {
		
	}
	
	public int getType() {
		return type;
	}
	
	public int getLength() {
		if (data == null) return 0;
		return data.length;
	}
	
	public byte [] getData() {
		return data;
	}
	
	public static BlackBoxSubPacket createPacketForMessage(String message) {
		BlackBoxSubPacket packet = new BlackBoxSubPacket();
		BlackBoxSubPacketType type = new BlackBoxSubPacketType(BlackBoxSubPacketType.USER_UPDATE & BlackBoxSubPacketType.MODULE_MESSAGE);
		packet.type = type.getType();
		short length = (short)message.length();
		byte [] data = new byte[2 + headerSize + message.length()];
		int pos = 0;
		generateHeader(data, 0, headerSize, 6);
		pos += headerSize;
		data[pos++] = (byte)(length & 0xff);
		data[pos++] = (byte)((length >> 8) & 0xff);
		for (int i = 0; i < length; i++) {
			data[pos++] = (byte)message.charAt(i);
		}
		packet.data = data;
		return packet;
	}
	
	public static BlackBoxSubPacket getInstance(int type) {
		BlackBoxSubPacket packet = new BlackBoxSubPacket();
		BlackBoxSubPacketType pType = new BlackBoxSubPacketType(type);
		if (!pType.isGamePacket())
			throw new IllegalArgumentException("You need to specify a module for non-match packets");
		packet.type = type;
		packet.data = createGamePacket();
		return packet;
	}
	
	public static BlackBoxSubPacket getInstance(int type, int module) {
		BlackBoxSubPacket packet = new BlackBoxSubPacket();
		BlackBoxSubPacketType pType = new BlackBoxSubPacketType(type);
		packet.type = type;
		if (pType.isDataPacket()) {
			if (pType.isAnalogModule()) {
				packet.data = createAnalogPacket(module);
			} else if (pType.isDigitalModule()) {
				packet.data = createDigitalPacket(module);
			} else if (pType.isRelayModule()) {
				packet.data = createRelayPacket(module);
			} else if (pType.isSolenoidModule()) {
				packet.data = createSolenoidPacket(module);
			} else if (pType.isPWMModule()) {
				packet.data = createPWMPacket(module);
			}
		}
		return packet;
	}
	
	private static byte [] createGamePacket() {
		DriverStation ds = DriverStation.getInstance();
		float matchTime = (float)ds.getMatchTime();
		float batteryVoltage = (float)ds.getBatteryVoltage();
		byte dsNumber = (byte)ds.getLocation();
		int teamNumber = ds.getTeamNumber();
		byte [] matchTimeRaw = intToByteArray(Float.floatToIntBits(matchTime));
		byte [] batteryVoltageRaw = intToByteArray(Float.floatToIntBits(batteryVoltage));
		byte [] teamNumberRaw = intToByteArray(teamNumber);
		byte miscData = 0;
		final byte [] ref = {(byte)0x80, 0x40, 0x20, 0x10, 0x08, 0x04, 0x02, 0x01};
		miscData ^= (ds.getAlliance().value==Alliance.kBlue_val)?ref[0]:0; //(Blue, Red)(True, False)
		miscData ^= !ds.isEnabled() ? ref[1] : 0; // (Disabled, Enabled)
		miscData ^= ds.isAutonomous()? ref[2] : 0; // (Autonomous, )
		miscData ^= ds.isOperatorControl() ? ref[3] : 0; // (Operator Control, )
		miscData ^= ds.isTest() ? ref[4] : 0; // (Test, )
		miscData ^= ds.isFMSAttached() ? ref[5] : 0; // (FMS, )
		byte [] data = new byte[14 + headerSize];
		int pos = 0;
		generateHeader(data, 0, headerSize, 0);
		pos += headerSize;
		System.arraycopy(matchTimeRaw, 0, data, pos, 4); pos += 4;
		System.arraycopy(batteryVoltageRaw, 0, data, pos, 4); pos += 4;
		data[pos] = dsNumber; pos++;
		System.arraycopy(teamNumberRaw, 0, data, pos, 4); pos += 4;
		data[pos] = miscData; pos++;
		return data;
	}
	
	private static byte [] createAnalogPacket(int module) {
		byte [] data = new byte[9 + headerSize];
		generateHeader(data, 0, headerSize, 1);
		data[headerSize] = (byte)module;
		for (int i = 0; i < 8; i++) {
			data[i+headerSize+1] = (byte)(5 / AnalogModule.getInstance(module).getVoltage(i+1) * 255);
//			data[i+headerSize+1] = 0;
		}
		return data;
	}
	
	private static byte [] createDigitalPacket(int module) {
		byte [] data = new byte[3 + headerSize];
		generateHeader(data, 0, headerSize, 2);
		data[headerSize] = (byte)(module+1);
		for (int i = 0; i < 14; i++) {
			if (!DigitalModule.getInstance(module+1).getDIO(i)) continue;
			if (i < 8)
				data[headerSize+1] ^= (byte)(1 << (7-i));
			else
				data[headerSize+2] ^= (byte)(1 << 7-(i-8));
		}
		return data;
	}
	
	private static byte [] createRelayPacket(int module) {
		byte [] data = new byte[3 + headerSize];
		generateHeader(data, 0, headerSize, 4);
		data[headerSize] = (byte)(module+1);
		for (int i = 0; i < 8; i++) {
			byte bit = (byte)(DigitalModule.getInstance(module+1).getRelayForward(i+1) ? 1:0);
			if (bit == 0) bit = (byte)(DigitalModule.getInstance(module+1).getRelayReverse(i+1) ? 2:0);
//			byte bit = (byte)new Random().nextInt(3);
			if (bit == 1) {
				data[headerSize+1] ^= 1 << 7 - i;
			} else if (bit == 2) {
				data[headerSize+1] ^= 1 << 7 - i;
				data[headerSize+2] ^= 1 << 7 - i;
			}
		}
		return data;
	}
	
	private static byte [] createPWMPacket(int module) {
		byte [] data = new byte[11 + headerSize];
		generateHeader(data, 0, headerSize, 3);
		data[headerSize] = (byte)(module+1);
		for (int i = 0; i < 10; i++) {
			data[i+headerSize+1] = (byte)DigitalModule.getInstance(module+1).getPWM(i+1);
//			data[i+headerSize+1] = (byte)0x80;
		}
		return data;
	}
	
	private static byte [] createSolenoidPacket(int module) {
		byte [] data = new byte[2 + headerSize];
		generateHeader(data, 0, headerSize, 5);
		data[headerSize] = (byte)module;
		data[headerSize+1] = SolenoidBase.getAllFromModule(module);
//		data[headerSize+1] = (byte)0 ^ (byte)0x80 ^ (byte)0x10;
		return data;
	}
	
	private static void generateHeader(byte [] data, int pos, int len, int dataType) {
		if (len < headerSize) { return; }
		if (pos < 0 || pos + headerSize > data.length) { return; }
		data[pos+0] = (byte)dataType;
		data[pos+1] = (byte)data.length;
	}
	
	private static byte[] intToByteArray(int value) {
		byte [] b = new byte[4];
		for (int i = 0; i < 4; i++) {
			int offset = (b.length - 1 - i) * 8;
			b[i] = (byte) ((value >>> offset) & 0xFF);
		}
		return b;
	}
}
