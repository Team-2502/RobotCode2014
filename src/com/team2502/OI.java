package com.team2502;

import com.team2502.commands.collector.ToggleAndWaitForBall;
import com.team2502.commands.collector.ToggleForklift;
import com.team2502.commands.drive.ToggleDriveTrain;
import com.team2502.commands.shooter.CalibrateWinch;
import com.team2502.commands.shooter.ForceWinchDown;
import com.team2502.commands.shooter.ForceWinchUp;
import com.team2502.commands.shooter.LatchTheLatch;
import com.team2502.commands.shooter.ShootAndReload;
import com.team2502.commands.shooter.SlowFeedToAlliance;
import com.team2502.commands.shooter.StartCompressor;
import com.team2502.commands.shooter.UnlatchTheLatch;
import com.team2502.commands.vision.ActivateRingLight;
import com.team2502.commands.vision.DeactivateRingLight;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
	
	public static final int JOYSTICK_COUNT = 1;
	public static Joystick joyDriveLeft;
	public static Joystick joyDriveRight;
	
	private static JoystickButton switchDriveTrainLeft;
	private static JoystickButton switchDriveTrainRight;
	
	private static JoystickButton toggleForkliftLeft;
	
	private static JoystickButton backupToggleForkliftLeft;
	
	private static JoystickButton shootBallRight;
	
	private static JoystickButton startCompressorLeft;
	private static JoystickButton startCompressorRight;
	
	private static JoystickButton moveWinchUpRight;
	private static JoystickButton moveWinchDownRight;
	
	private static JoystickButton latchTheLatchRight;
	private static JoystickButton unlatchTheLatchRight;
	
	private static JoystickButton shootAndCalibrateRight;
	
	private static JoystickButton activateRingLightRight;
	private static JoystickButton deactivateRingLightRight;
	
	private static JoystickButton slowFeedToAllianceLeft;
	private static JoystickButton slowFeedToAllianceRight;
	
	private static JoystickButton windWinchUpLeft;
	private static JoystickButton windWinchDownLeft;
	
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
		slowFeedToAllianceLeft = new JoystickButton(joyDriveLeft, 3);
		backupToggleForkliftLeft = new JoystickButton(joyDriveLeft, 5);
		//wndWinchUpLeft = new JoystickButton(joyDriveLeft, 9);
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
		activateRingLightRight = new JoystickButton(joyDriveRight, 6);
		deactivateRingLightRight = new JoystickButton(joyDriveRight, 5);
		slowFeedToAllianceRight = new JoystickButton(joyDriveRight, 7);
	}
	
	private static void linkLeftJoystickButtons() {
		switchDriveTrainLeft.whenPressed(new ToggleDriveTrain());
		toggleForkliftLeft.whenPressed(new ToggleForklift());
		startCompressorLeft.whileHeld(new StartCompressor());
		slowFeedToAllianceLeft.whenPressed(new SlowFeedToAlliance());
		backupToggleForkliftLeft.whenPressed(new ToggleForklift());
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
		activateRingLightRight.whileHeld(new ActivateRingLight());
		deactivateRingLightRight.whileHeld(new DeactivateRingLight());
		slowFeedToAllianceRight.whenPressed(new SlowFeedToAlliance());
	}
	
}
