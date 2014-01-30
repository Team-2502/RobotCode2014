package com.team2502.commands.drive;

import com.team2502.OI;
import com.team2502.commands.CommandBase;

/**
 *
 * @author Josh Larson
 */
public class DriveWithJoystick extends CommandBase {
	
	public DriveWithJoystick() {
		requires(driveSubsystem);
	}
	
	protected void initialize() {
		
	}
	
	protected void execute() {
		if (driveSubsystem.isTraction()) {
			if (OI.JOYSTICK_COUNT == 2) {
				driveSubsystem.driveTank(OI.joyDriveLeft, OI.joyDriveRight);
			} else {
				driveSubsystem.driveArcade(OI.joyArcade);
			}
		} else {
			if (OI.JOYSTICK_COUNT == 2) {
				driveSubsystem.driveMecanum(OI.joyDriveLeft, OI.joyDriveRight);
			} else {
				driveSubsystem.driveMecanum(OI.joyArcade);
			}
		}
	}
	
	protected boolean isFinished() {
		return false;
	}
	
	protected void end() {
		driveSubsystem.stopMoving();
	}
	
	protected void interrupted() {
		end();
	}
}
