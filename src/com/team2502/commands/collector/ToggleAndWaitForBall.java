package com.team2502.commands.collector;

import com.team2502.commands.CommandBase;

/**
 * @author Jackson Turner
 *
 */
public class ToggleAndWaitForBall extends CommandBase {
	
	private boolean finished = false;
	
	public ToggleAndWaitForBall() {
		requires(collectorSubsystem);
	}
	
	protected void end() {
		
	}
	
	protected void execute() {
		if (collectorSubsystem.isBallInCollector() && !finished) {
			collectorSubsystem.moveCollectorUp();
			finished = true;
		}
	}
	
	protected void initialize() {
		if (collectorSubsystem.isForkliftDown()) {
			finished = true;
			collectorSubsystem.moveCollectorUp();
		} else {
			finished = false;
			collectorSubsystem.moveCollectorDown();
		}
	}
	
	protected void interrupted() {
		end();
	}
	
	protected boolean isFinished() {
		return finished && collectorSubsystem.isForkliftUp();
	}

}
