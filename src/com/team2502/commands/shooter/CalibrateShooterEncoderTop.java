package com.team2502.commands.shooter;

import com.team2502.commands.CommandBase;

/**
 * @author Jackson Turner
 *
 */
public class CalibrateShooterEncoderTop extends CommandBase {

	public CalibrateShooterEncoderTop() {
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
		shooterSubsystem.setCurrentEncoderPositionAsTop();
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
