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
	
	protected void end() {

	}
	
	protected void execute() {
		
	}
	
	protected void initialize() {
		shooterSubsystem.setCurrentEncoderPositionAsBottom();
	}
	
	protected void interrupted() {
		end();
	}
	
	protected boolean isFinished() {
		return true;
	}

}
