package com.team2502.commands.shooter;

import com.team2502.commands.CommandBase;
import com.team2502.subsystems.ShooterSubsystem;

/**
 *
 * @author josh
 */
public class StartCompressor extends CommandBase {
	
	public StartCompressor() {
		requires(shooterSubsystem);
	}
	
	protected void initialize() {
		
	}
	
	protected void execute() {
		shooterSubsystem.startCompressor();
	}
	
	protected boolean isFinished() {
		return false;
	}
	
	protected void end() {
		shooterSubsystem.stopCompressor();
	}
	
	protected void interrupted() {
		
	}
}
