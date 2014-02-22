package com.team2502.commands.shooter;

import com.team2502.commands.CommandBase;

/**
 * @author Jackson Turner
 *
 */
public class LatchTheLatch extends CommandBase {

	public LatchTheLatch() {
		requires(shooterSubsystem);
	}
	
	protected void end() {

	}
	
	protected void execute() {

	}
	
	protected void initialize() {
		shooterSubsystem.activateLatch();
	}
	
	protected void interrupted() {
		end();
	}
	
	protected boolean isFinished() {
		return true;
	}

}
