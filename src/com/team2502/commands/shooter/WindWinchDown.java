package com.team2502.commands.shooter;

import com.team2502.black_box.BlackBoxProtocol;
import com.team2502.commands.CommandBase;

/**
 *
 * @author Josh Larson
 */
public class WindWinchDown extends CommandBase {
	
	private boolean isDownOnInitialize;
	
	public WindWinchDown() {
		requires(shooterSubsystem);
	}
	
	protected void initialize() {
		shooterSubsystem.moveWinchDownPID();
		isDownOnInitialize = isFinished();
	}
	
	protected void execute() {
		
	}
	
	protected boolean isFinished() {
		return shooterSubsystem.isWinchProgressDown();
	}
	
	protected void end() {
		if (!isDownOnInitialize)
			BlackBoxProtocol.log(isFinished() ? "Winch is down" : "Winch pull-down interrupted");
		shooterSubsystem.stopWinch();
	}
	
	protected void interrupted() {
		end();
	}
}
