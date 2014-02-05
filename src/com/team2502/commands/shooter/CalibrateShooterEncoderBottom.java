package com.team2502.commands.shooter;

import com.team2502.commands.CommandBase;

/**
 * @author Jackson Turner
 *
 */
public class CalibrateShooterEncoderBottom extends CommandBase {

	public CalibrateShooterEncoderBottom() {
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
		shooterSubsystem.setCurrentEncoderPositionAsBottom();
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
