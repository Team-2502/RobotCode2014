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
	
	protected void end() {

	}
	
	protected void execute() {
		
	}
	
	protected void initialize() {
		shooterSubsystem.setCurrentEncoderPositionAsTop();
	}
	
	protected void interrupted() {
		end();
	}
	
	protected boolean isFinished() {
		return true;
	}

}
