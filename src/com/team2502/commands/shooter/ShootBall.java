package com.team2502.commands.shooter;

import com.team2502.commands.CommandBase;

/**
 * @author Jackson Turner
 *
 */
public class ShootBall extends CommandBase {

	public ShootBall() {
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
		if (shooterSubsystem.isLoaded() && shooterSubsystem.isDown() && shooterSubsystem.isLatched())
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
