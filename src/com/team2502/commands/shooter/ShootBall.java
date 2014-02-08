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
	
	protected void end() {

	}
	
	protected void execute() {
	}
	
	protected void initialize() {
		if (shooterSubsystem.isLoaded() && shooterSubsystem.isDown() && shooterSubsystem.isLatched())
			shooterSubsystem.deactivateLatch();
	}
	
	protected void interrupted() {
		end();
	}
	
	protected boolean isFinished() {
		return true;
	}

}
