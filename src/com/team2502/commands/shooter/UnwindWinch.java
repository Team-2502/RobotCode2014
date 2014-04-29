package com.team2502.commands.shooter;

import com.team2502.black_box.BlackBoxProtocol;
import com.team2502.commands.CommandBase;

/**
 * @author Jackson Turner
 *
 */
public class UnwindWinch extends CommandBase {
	
	private boolean isUpOnInitialize;
	
	public UnwindWinch() {
		requires(shooterSubsystem);
	}
	
	protected void end() {
		if (!isUpOnInitialize)
			BlackBoxProtocol.log(isFinished() ? "Winch is up" : "Winch pull-up interrupted");
		shooterSubsystem.stopWinch();
	}
	
	protected void execute() {
		
	}
	
	protected void initialize() {
		shooterSubsystem.moveWinchUp();
		isUpOnInitialize = isFinished();
	}
	
	protected void interrupted() {
		end();
	}
	
	protected boolean isFinished() {
		return shooterSubsystem.isWinchProgressUp();
	}

}
