package com.team2502.commands.shooter;

import com.team2502.commands.CommandBase;

/**
 * @author Jackson Turner
 *
 */
public class UnwindWinch extends CommandBase {
	
	public UnwindWinch() {
		requires(shooterSubsystem);
	}
	
	protected void end() {
		shooterSubsystem.stopWinch();
	}
	
	protected void execute() {
		
	}
	
	protected void initialize() {
		shooterSubsystem.moveWinchUp();
	}
	
	protected void interrupted() {
		end();
	}
	
	protected boolean isFinished() {
		return shooterSubsystem.isWinchProgressUp();
	}

}
