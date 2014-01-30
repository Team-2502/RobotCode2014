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
			if (OI.TANK_DRIVE) {
				driveSubsystem.driveTank(OI.joyDriveLeft, OI.joyDriveRight);
			} else {
				driveSubsystem.driveArcade(OI.joyArcade);
			}
		} else {
			driveSubsystem.driveMecanum(OI.joyArcade);
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
