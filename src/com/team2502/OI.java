package com.team2502;

import edu.wpi.first.wpilibj.Joystick;

public class OI {
	
	public static final int JOYSTICK_COUNT = 2;
	public static Joystick joyDriveLeft;
	public static Joystick joyDriveRight;
	
	public static void init() {
		if (JOYSTICK_COUNT >= 2)
			joyDriveLeft = new Joystick(RobotMap.JOYSTICK_DRIVE_LEFT_PORT);
		// I want this joystick to be the one that is the constant drive joystick
		joyDriveRight = new Joystick(RobotMap.JOYSTICK_DRIVE_RIGHT_PORT);
	}
	
}
