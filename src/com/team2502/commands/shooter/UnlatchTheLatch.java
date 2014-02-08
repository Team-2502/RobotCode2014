package com.team2502.commands.shooter;

import com.team2502.commands.CommandBase;

/**
 * @author Jackson Turner
 *
 */
public class UnlatchTheLatch extends CommandBase {

	public UnlatchTheLatch() {
		requires(shooterSubsystem);
	}
	
	protected void end() {

	}
	
	protected void execute() {

	}
	
	protected void initialize() {
		shooterSubsystem.deactivateLatch();
	}
	
	protected void interrupted() {
		end();
	}
	
	protected boolean isFinished() {
		return true;
	}

}
