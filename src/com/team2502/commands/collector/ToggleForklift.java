package com.team2502.commands.collector;

import com.team2502.commands.CommandBase;

/**
 *
 * @author Josh Larson
 */
public class ToggleForklift extends CommandBase {
	
	public ToggleForklift() {
		requires(collectorSubsystem);
	}
	
	protected void initialize() {
		if (collectorSubsystem.isForkliftUp())
			collectorSubsystem.moveForkliftDown();
		else
			collectorSubsystem.moveForkliftUp();
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
