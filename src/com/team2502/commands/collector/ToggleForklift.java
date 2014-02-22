package com.team2502.commands.collector;

import com.team2502.commands.CommandBase;

/**
 *
 * @author josh
 */
public class ToggleForklift extends CommandBase {
	
	public ToggleForklift() {
		requires(collectorSubsystem);
	}
	
	protected void initialize() {
		if (collectorSubsystem.isForkliftUp())
			collectorSubsystem.moveCollectorDown();
		else
			collectorSubsystem.moveCollectorUp();
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
