package com.team2502.commands.shooter;

import com.team2502.commands.CommandBase;

/**
 * @author Jackson Turner
 *
 */
public class PullBackShooter extends CommandBase {
	
	private int downInRow = 0;
	
	public PullBackShooter() {
		requires(shooterSubsystem);
	}
	
	protected void end() {
		shooterSubsystem.stopWinch();
	}
	
	protected void execute() {
		shooterSubsystem.moveWinchDown();
		if (shooterSubsystem.isDown())
			downInRow++;
		else
			downInRow = 0;
	}
	
	protected void initialize() {
		shooterSubsystem.moveWinchDown();
		downInRow = 0;
	}
	
	protected void interrupted() {
		end();
	}
	
	protected boolean isFinished() {
		return downInRow >= 3;
	}

}
