package com.team2502.commands.shooter;

import com.team2502.commands.CommandBase;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * @author Jackson Turner
 *
 */
public class PullBackShooter extends CommandBase {

	private boolean finished = false;

	public PullBackShooter() {
		requires(shooterSubsystem);
	}

	@Override
	protected void end() {
		shooterSubsystem.stopWinch();
	}

	@Override
	protected void execute() {
		if (shooterSubsystem.isDown())
			finished = true;
	}

	@Override
	protected void initialize() {
		shooterSubsystem.moveWinchDown();
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
