package com.team2502.commands.shooter;

import com.team2502.commands.CommandBase;

/**
 *
 * @author Josh Larson
 */
public class WindWinchDown extends CommandBase {
	
	public WindWinchDown() {
		requires(shooterSubsystem);
	}
	
	protected void initialize() {
		shooterSubsystem.moveWinchDownPID();
	}
	
	protected void execute() {
		
	}
	
	protected boolean isFinished() {
		return shooterSubsystem.isWinchProgressDown();
	}
	
	protected void end() {
		shooterSubsystem.stopWinch();
	}
	
	protected void interrupted() {
		end();
	}
}
