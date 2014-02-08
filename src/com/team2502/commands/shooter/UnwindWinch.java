package com.team2502.commands.shooter;

import com.team2502.commands.CommandBase;

/**
 * @author Jackson Turner
 *
 */
public class UnwindWinch extends CommandBase {

	boolean finished = false;

	public UnwindWinch() {
		requires(shooterSubsystem);
	}
	
	protected void end() {
		shooterSubsystem.stopWinch();
	}
	
	protected void execute() {
		if (shooterSubsystem.getWinchProgress() <= 0)
			finished = true;
	}
	
	protected void initialize() {
		shooterSubsystem.moveWinchUp();
	}
	
	protected void interrupted() {
		end();
	}
	
	protected boolean isFinished() {
		return finished;
	}

}
