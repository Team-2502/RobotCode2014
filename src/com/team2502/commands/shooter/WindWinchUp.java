package com.team2502.commands.shooter;

import com.team2502.black_box.BlackBoxProtocol;
import com.team2502.commands.CommandBase;

/**
 *
 * @author Josh Larson
 */
public class WindWinchUp extends CommandBase {
	
	private boolean isUpOnInitialize;
	
	public WindWinchUp() {
		requires(shooterSubsystem);
	}
	
	protected void initialize() {
		shooterSubsystem.moveWinchUpPID();
		isUpOnInitialize = isFinished();
	}
	
	protected void execute() {
		
	}
	
	protected boolean isFinished() {
		return shooterSubsystem.isWinchProgressUp();
	}
	
	protected void end() {
		if (!isUpOnInitialize)
			BlackBoxProtocol.log(isFinished() ? "Winch is up" : "Winch pull-up interrupted");
		shooterSubsystem.stopWinch();
	}
	
	protected void interrupted() {
		end();
	}
}
