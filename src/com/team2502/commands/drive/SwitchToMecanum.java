package com.team2502.commands.drive;

import com.team2502.commands.CommandBase;

/**
 *
 * @author Josh Larson
 */
public class SwitchToMecanum extends CommandBase {
	
	public SwitchToMecanum() {
		requires(driveSubsystem);
	}
	
	protected void initialize() {
		driveSubsystem.switchToMecanum();
	}
	
	protected void execute() {
		
	}
	
	protected boolean isFinished() {
		return true;
	}
	
	protected void end() {
		
	}
	
	protected void interrupted() {
		end();
	}
}
