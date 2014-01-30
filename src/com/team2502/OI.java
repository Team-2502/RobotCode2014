
package com.team2502;

import edu.wpi.first.wpilibj.Joystick;

public class OI {
	
	private static Joystick joyArcade;
	private static Joystick joyDriveLeft;
	private static Joystick joyDriveRight;
	
	public static void init() {
		joyArcade = new Joystick(RobotMap.JOYSTICK_ARCADE_PORT);
		joyDriveLeft = new Joystick(RobotMap.JOYSTICK_DRIVE_LEFT_PORT);
		joyDriveRight = new Joystick(RobotMap.JOYSTICK_DRIVE_RIGHT_PORT);
	}
	
}
