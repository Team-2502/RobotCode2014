package com.team2502.commands.shooter;

import com.team2502.commands.CommandBase;

/**
 *
 * @author Josh Larson
 */
public class StopWinch extends CommandBase {
	
	public StopWinch() {
		requires(shooterSubsystem);
	}
	
	protected void initialize() {
		shooterSubsystem.stopWinch();
	}
	
	protected void execute() {
		shooterSubsystem.stopWinch();
	}
	
	protected boolean isFinished() {
		return true;
	}
	
	protected void end() {
		
	}
	
	protected void interrupted() {
		
	}
}
