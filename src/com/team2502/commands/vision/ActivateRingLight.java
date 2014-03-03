package com.team2502.commands.vision;

import com.team2502.commands.CommandBase;

/**
 *
 * @author Josh Larson
 */
public class ActivateRingLight extends CommandBase {
	
	public ActivateRingLight() {
		requires(visionSubsystem);
	}
	
	protected void initialize() {
		visionSubsystem.activateRingLight();
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
