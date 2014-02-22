package com.team2502.commands.shooter;

import com.team2502.commands.CommandBase;

/**
 *
 * @author josh
 */
public class ToggleTheLatch extends CommandBase {
	
	public ToggleTheLatch() {
		requires(shooterSubsystem);
	}
	
	protected void initialize() {
		if (shooterSubsystem.isLatched())
			shooterSubsystem.deactivateLatch();
		else
			shooterSubsystem.activateLatch();
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
