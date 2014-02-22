package com.team2502.commands.shooter;

import com.team2502.commands.CommandBase;

/**
 *
 * @author josh
 */
public class ForceWinchDown extends CommandBase {
	
	public ForceWinchDown() {
		requires(shooterSubsystem);
	}
	
	protected void initialize() {
		shooterSubsystem.forceWinchDown();
	}
	
	protected void execute() {
		shooterSubsystem.forceWinchDown();
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
