package com.team2502.commands.drive;

import com.team2502.commands.CommandBase;

/**
 *
 * @author Josh Larson
 */
public class ToggleDriveTrain extends CommandBase {
	
	private boolean switchSucceed;
	
	public ToggleDriveTrain() {
		requires(driveSubsystem);
		switchSucceed = false;
	}
	
	protected void initialize() {
		
	}
	
	protected void execute() {
		switchSucceed = driveSubsystem.toggleDriveTrain();
	}
	
	protected boolean isFinished() {
		return switchSucceed;
	}
	
	protected void end() {
		
	}
	
	protected void interrupted() {
		end();
	}
}
