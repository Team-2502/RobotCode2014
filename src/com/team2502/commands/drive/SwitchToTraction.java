package com.team2502.commands.drive;

import com.team2502.commands.CommandBase;

/**
 *
 * @author josh
 */
public class SwitchToTraction extends CommandBase {
	
	private boolean switchSucceed;
	
	public SwitchToTraction() {
		requires(driveSubsystem);
		switchSucceed = false;
	}
	
	protected void initialize() {
		switchSucceed = driveSubsystem.switchToTraction();
	}
	
	protected void execute() {
		switchSucceed = driveSubsystem.switchToTraction();
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
