package com.team2502.commands.shooter;

import com.team2502.commands.CommandBase;

/**
 *
 * @author josh
 */
public class ResetShooterEncoder extends CommandBase {
	
	public ResetShooterEncoder() {
		requires(shooterSubsystem);
	}
	
	protected void initialize() {
		shooterSubsystem.resetEncoder();
	}
	
	protected void execute() {
		
	}
	
	protected boolean isFinished() {
		return true;
	}
	
	protected void end() {
		
	}
	
	protected void interrupted() {
		
	}
	
}
