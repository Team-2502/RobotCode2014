package com.team2502.commands.vision;

import com.team2502.commands.CommandBase;

/**
 *
 * @author Josh Larson
 */
public class VisionUpdater extends CommandBase {
	
	private long lastUpdate;
	
	public VisionUpdater() {
		requires(visionSubsystem);
	}
	
	protected void initialize() {
		lastUpdate = System.currentTimeMillis();
	}
	
	protected void execute() {
		if (System.currentTimeMillis() - lastUpdate >= 100 && visionSubsystem.isConnected()) {
			visionSubsystem.updateRaspberryPi();
			lastUpdate = System.currentTimeMillis();
		}
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
