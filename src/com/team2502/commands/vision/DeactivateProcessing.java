package com.team2502.commands.vision;

import com.team2502.commands.CommandBase;

/**
 *
 * @author Josh Larson
 */
public class DeactivateProcessing extends CommandBase {
	
	public DeactivateProcessing() {
		requires(visionSubsystem);
	}
	
	protected void initialize() {
		visionSubsystem.setProcessing(false);
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
