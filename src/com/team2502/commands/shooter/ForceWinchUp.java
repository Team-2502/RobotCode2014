package com.team2502.commands.shooter;

import com.team2502.commands.CommandBase;

/**
 *
 * @author josh
 */
public class ForceWinchUp extends CommandBase {
	
	public ForceWinchUp() {
		requires(shooterSubsystem);
	}
	
	protected void initialize() {
		shooterSubsystem.forceWinchUp();
	}
	
	protected void execute() {
		shooterSubsystem.forceWinchUp();
	}
	
	protected boolean isFinished() {
		return true;
	}
	
	protected void end() {
		shooterSubsystem.stopWinch();
	}
	
	protected void interrupted() {
		end();
	}
}
