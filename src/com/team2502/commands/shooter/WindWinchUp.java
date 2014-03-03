package com.team2502.commands.shooter;

import com.team2502.commands.CommandBase;

/**
 *
 * @author Josh Larson
 */
public class WindWinchUp extends CommandBase {
	
	public WindWinchUp() {
		requires(shooterSubsystem);
	}
	
	protected void initialize() {
		shooterSubsystem.moveWinchUpPID();
	}
	
	protected void execute() {
		
	}
	
	protected boolean isFinished() {
		return shooterSubsystem.isWinchProgressUp();
	}
	
	protected void end() {
		shooterSubsystem.stopWinch();
	}
	
	protected void interrupted() {
		end();
	}
}
