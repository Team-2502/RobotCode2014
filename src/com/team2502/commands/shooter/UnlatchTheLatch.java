package com.team2502.commands.shooter;

import com.team2502.commands.CommandBase;

/**
 * @author Jackson Turner
 *
 */
public class UnlatchTheLatch extends CommandBase {

	public UnlatchTheLatch() {
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
		shooterSubsystem.deactivateLatch();
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
