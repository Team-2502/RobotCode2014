package com.team2502.commands.vision;

import com.team2502.commands.CommandBase;

/**
 *
 * @author josh
 */
public class VisionUpdater extends CommandBase {
	
	public VisionUpdater() {
		requires(visionSubsystem);
	}
	
	protected void initialize() {
		
	}
	
	protected void execute() {
		
	}
	
	protected boolean isFinished() {
		return false;
	}
	
	protected void end() {
		
	}
	
	protected void interrupted() {
		end();
	}
}
