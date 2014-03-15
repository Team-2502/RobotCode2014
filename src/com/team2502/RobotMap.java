


package com.team2502;

public class RobotMap {
	
	public static final int JOYSTICK_DRIVE_LEFT_PORT  = 2;
	public static final int JOYSTICK_DRIVE_RIGHT_PORT = 1;
	
	public static final int DRIVE_FRONT_LEFT          = 1; // PWM         Orange
	public static final int DRIVE_FRONT_RIGHT         = 2; // PWM         Yellow
	public static final int DRIVE_BACK_LEFT           = 3; // PWM         Pink
	public static final int DRIVE_BACK_RIGHT          = 4; // PWM         Blue
	public static final int DRIVE_TRAIN_SWITCHER      = 3; // Solenoid
	
	public static final int SHOOTER_LIMIT_SWITCH_UP   = 1; // Digital IO  
	public static final int SHOOTER_LIMIT_SWITCH_DOWN = 2; // Digital IO  
	public static final int SHOOTER_LATCH             = 2; // Solenoid    
	public static final int SHOOTER_LOADED_SENSOR     = 3; // Analog      
	public static final int SHOOTER_WINCH_CAN_PORT    = 9; // CAN Jaguar  
	public static final int SHOOTER_WINCH_ENCODER_A   = 4; // Digital IO
	public static final int SHOOTER_WINCH_ENCODER_B   = 5; // Digital IO
	
	public static final int COMPRESSOR_SWITCH         = 3; // Digital IO  
	public static final int COMPRESSOR_RELAY          = 1; // Relay       Black
	
	public static final int COLLECTOR_PISTON_PORT     = 5; // Solenoid    
	public static final int COLLECTOR_SENSOR_ONE_PORT = 1; // Analog      
	public static final int COLLECTOR_SENSOR_TWO_PORT = 2; // Analog      
	public static final int COLLECTOR_UP_LIMIT        = 4; // Digital IO  
	public static final int COLLECTOR_DOWN_LIMIT      = 5; // Digital IO  
	
	public static final int VISION_RING_LIGHT         = 8; // Solenoid
	
}
