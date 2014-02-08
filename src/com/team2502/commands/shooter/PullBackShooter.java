package com.team2502.commands.shooter;

import com.team2502.commands.CommandBase;

/**
 * @author Jackson Turner
 *
 */
public class PullBackShooter extends CommandBase {

	private boolean finished = false;

	public PullBackShooter() {
		requires(shooterSubsystem);
	}
	
	protected void end() {
		shooterSubsystem.stopWinch();
	}
	
	protected void execute() {
		if (shooterSubsystem.isDown())
			finished = true;
	}
	
	protected void initialize() {
		shooterSubsystem.moveWinchDown();
	}
	
	protected void interrupted() {
		end();
	}
	
	protected boolean isFinished() {
		return finished;
	}

}
