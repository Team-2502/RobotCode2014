package com.team2502.commands.collector;

import com.team2502.commands.CommandBase;

/**
 * @author Jackson Turner
 *
 */
public class WaitForBallThenRaiseCollector extends CommandBase {

	private boolean finished = false;

	public WaitForBallThenRaiseCollector() {
		requires(collectorSubsystem);
	}
	
	protected void end() {

	}
	
	protected void execute() {
		if (collectorSubsystem.isBallInCollector()) {
			collectorSubsystem.moveCollectorUp();
			finished = true;
		}
	}
	
	protected void initialize() {
		
	}
	
	protected void interrupted() {
		end();
	}
	
	protected boolean isFinished() {
		return finished && collectorSubsystem.isForkliftUp();
	}

}
