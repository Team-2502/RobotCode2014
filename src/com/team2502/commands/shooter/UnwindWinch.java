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

	@Override
	protected void end() {
		shooterSubsystem.stopWinch();
	}

	@Override
	protected void execute() {
		if (shooterSubsystem.getWinchProgress() <= 0)
			finished = true;
	}

	@Override
	protected void initialize() {
		shooterSubsystem.moveWinchUp();
	}

	@Override
	protected void interrupted() {
		end();
	}

	@Override
	protected boolean isFinished() {
		return finished;
	}

}
