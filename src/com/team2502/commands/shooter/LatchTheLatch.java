package com.team2502.commands.shooter;

import com.team2502.commands.CommandBase;

/**
 * @author Jackson Turner
 *
 */
public class LatchTheLatch extends CommandBase {

	public LatchTheLatch() {
		requires(shooterSubsystem);
	}

	@Override
	protected void end() {

	}

	@Override
	protected void execute() {

	}

	@Override
	protected void initialize() {
		if (shooterSubsystem.isDown())
			shooterSubsystem.activateLatch();
	}

	@Override
	protected void interrupted() {
		end();
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

}
