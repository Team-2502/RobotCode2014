package com.team2502.commands.vision;

import com.team2502.commands.CommandBase;

/**
 *
 * @author Josh Larson
 */
public class DeactivateRingLight extends CommandBase {
	
	public DeactivateRingLight() {
		requires(visionSubsystem);
	}
	
	protected void initialize() {
		visionSubsystem.deactivateRingLight();
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
