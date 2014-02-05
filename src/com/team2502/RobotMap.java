package com.team2502;

public class RobotMap {
	
	public static final int JOYSTICK_ARCADE_PORT      = 1;
	public static final int JOYSTICK_DRIVE_LEFT_PORT  = 2;
	public static final int JOYSTICK_DRIVE_RIGHT_PORT = 3;
	
	public static final int DRIVE_FRONT_LEFT     = 1; // PWM
	public static final int DRIVE_FRONT_RIGHT    = 2; // PWM
	public static final int DRIVE_BACK_LEFT      = 3; // PWM
	public static final int DRIVE_BACK_RIGHT     = 4; // PWM
	public static final int DRIVE_TRAIN_SWITCHER = 1; // Solenoid
	
	public static final int SHOOTER_LIMIT_SWITCH_UP   = 1; // Limit switch
	public static final int SHOOTER_LIMIT_SWITCH_DOWN = 2; // Limit switch
	public static final int SHOOTER_LATCH             = 1; // Solenoid
	public static final int SHOOTER_LOADED_SENSOR     = 1; // Analog (IR?)
	public static final int SHOOTER_WINCH_CAN_PORT = 1;  // CAN Jaguar
	
	public static final int COMPRESSOR_SWITCH = 1; // Compressor
	public static final int COMPRESSOR_RELAY = 2; // Compressor
	
	public static final int COLLECTOR_PISTON_PORT = 1; // Solenoid
	public static final int COLLECTOR_SENSOR_ONE_PORT = 1; // Analog (IR?)
	public static final int COLLECTOR_SENSOR_TWO_PORT = 2; // Analog (IR?)
	public static final int COLLECTOR_UP_LIMIT = 1; // Limit Switch
	public static final int COLLECTOR_DOWN_LIMIT = 2;  // Limit Switch
	
}
