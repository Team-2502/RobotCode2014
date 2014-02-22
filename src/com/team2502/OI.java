package com.team2502;

import com.team2502.commands.collector.ToggleForklift;
import com.team2502.commands.drive.ToggleDriveTrain;
import com.team2502.commands.shooter.CalibrateWinch;
import com.team2502.commands.shooter.ForceWinchDown;
import com.team2502.commands.shooter.ForceWinchUp;
import com.team2502.commands.shooter.LatchTheLatch;
import com.team2502.commands.shooter.PullBackShooter;
import com.team2502.commands.shooter.ShootAndReload;
import com.team2502.commands.shooter.StartCompressor;
import com.team2502.commands.shooter.UnlatchTheLatch;
import com.team2502.commands.shooter.UnwindWinch;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
	
	/*
	 * Joystick 1 (Right)
	 *		* Trigger  -	Shoot and Reload Ball
	 *		* Button 2 -	Switch Drive Train
	 *		* 
	 * 
	 * Joystick 2 (Left)
	 *		* Trigger	-	Toggle Forklift
	 *		* Button 2	-	Switch Drive Train
	 *		* 
	 */
	
	public static final int JOYSTICK_COUNT = 1;
	public static Joystick joyDriveLeft;
	public static Joystick joyDriveRight;
	
	private static JoystickButton switchDriveTrainLeft;
	private static JoystickButton switchDriveTrainRight;
	
	private static JoystickButton toggleForkliftLeft;
	
	private static JoystickButton shootBallRight;
	
	private static JoystickButton startCompressorLeft;
	private static JoystickButton startCompressorRight;
	
	private static JoystickButton moveWinchUpRight;
	private static JoystickButton moveWinchDownRight;
	
	private static JoystickButton latchTheLatchRight;
	private static JoystickButton unlatchTheLatchRight;
	
	private static JoystickButton shootAndCalibrateRight;
	
	public static void init() {
		joyDriveLeft = new Joystick(RobotMap.JOYSTICK_DRIVE_LEFT_PORT);
		joyDriveRight = new Joystick(RobotMap.JOYSTICK_DRIVE_RIGHT_PORT);
		createLeftJoystickButtons();
		createRightJoystickButtons();
		linkLeftJoystickButtons();
		linkRightJoystickButtons();
	}
	
	private static void createLeftJoystickButtons() {
		switchDriveTrainLeft = new JoystickButton(joyDriveLeft, 2);
		toggleForkliftLeft = new JoystickButton(joyDriveLeft, 1);
		startCompressorLeft = new JoystickButton(joyDriveLeft, 4);
	}
	
	private static void createRightJoystickButtons() {
		switchDriveTrainRight = new JoystickButton(joyDriveRight, 2);
		shootBallRight = new JoystickButton(joyDriveRight, 1);
		startCompressorRight = new JoystickButton(joyDriveRight, 4);
		moveWinchUpRight = new JoystickButton(joyDriveRight, 9);
		moveWinchDownRight = new JoystickButton(joyDriveRight, 11);
		latchTheLatchRight = new JoystickButton(joyDriveRight, 10);
		unlatchTheLatchRight = new JoystickButton(joyDriveRight, 12);
		shootAndCalibrateRight = new JoystickButton(joyDriveRight, 3);
	}
	
	private static void linkLeftJoystickButtons() {
		switchDriveTrainLeft.whenPressed(new ToggleDriveTrain());
		toggleForkliftLeft.whenPressed(new ToggleForklift());
		startCompressorLeft.whileHeld(new StartCompressor());
	}
	
	private static void linkRightJoystickButtons() {
		switchDriveTrainRight.whenPressed(new ToggleDriveTrain());
		shootBallRight.whenPressed(new ShootAndReload());
		startCompressorRight.whileHeld(new StartCompressor());
		moveWinchUpRight.whileHeld(new ForceWinchUp());
		moveWinchDownRight.whileHeld(new ForceWinchDown());
		latchTheLatchRight.whenPressed(new LatchTheLatch());
		unlatchTheLatchRight.whenPressed(new UnlatchTheLatch());
		shootAndCalibrateRight.whenPressed(new CalibrateWinch());
	}
	
}
