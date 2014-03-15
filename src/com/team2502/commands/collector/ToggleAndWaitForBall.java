package com.team2502.commands.collector;

import com.team2502.commands.CommandBase;

/**
 * @author Jackson Turner
 *
 */
public class ToggleAndWaitForBall extends CommandBase {
	
	private static final double TIME_UNTIL_BALL = 2.0;
	private boolean finished = false;
	
	public ToggleAndWaitForBall() {
		requires(collectorSubsystem);
	}
	
	protected void end() {
		
	}
	
	protected void execute() {
		if (collectorSubsystem.isBallInCollector() && !finished && timeSinceInitialized() >= TIME_UNTIL_BALL) {
			collectorSubsystem.moveForkliftUp();
			finished = true;
		}
	}
	
	protected void initialize() {
		if (collectorSubsystem.isForkliftDown()) {
			finished = true;
			collectorSubsystem.moveForkliftUp();
		} else {
			finished = false;
			collectorSubsystem.moveForkliftDown();
		}
	}
	
	protected void interrupted() {
		end();
	}
	
	protected boolean isFinished() {
		return finished;
	}

}
