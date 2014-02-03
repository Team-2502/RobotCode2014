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
	
	public static final int SHOOTER_WINCH_ENCODER_A = 1; // Encoder
	public static final int SHOOTER_WINCH_ENCODER_B = 2; // Encoder
	public static final int SHOOTER_LIMIT_SWITCH_UP   = 1; // Limit switch
	public static final int SHOOTER_LIMIT_SWITCH_DOWN = 2; // Limit switch
	public static final int SHOOTER_LATCH             = 1; // Solenoid
	public static final int SHOOTER_LOADED_SENSOR     = 1; // Analog (IR?)
	
	public static final int COMPRESSOR_SWITCH = 1; // Compressor
	public static final int COMPRESSOR_RELAY = 2; // Compressor
	
	public static final int WINCH_CAN_PORT = 1;  // CAN Jaguar
	
}
