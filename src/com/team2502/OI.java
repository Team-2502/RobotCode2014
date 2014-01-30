
package com.team2502;

import edu.wpi.first.wpilibj.Joystick;

public class OI {
	
	public static final boolean TANK_DRIVE = true;
	public static Joystick joyArcade;
	public static Joystick joyDriveLeft;
	public static Joystick joyDriveRight;
	
	public static void init() {
		joyArcade = new Joystick(RobotMap.JOYSTICK_ARCADE_PORT);
		joyDriveLeft = new Joystick(RobotMap.JOYSTICK_DRIVE_LEFT_PORT);
		joyDriveRight = new Joystick(RobotMap.JOYSTICK_DRIVE_RIGHT_PORT);
	}
	
}
