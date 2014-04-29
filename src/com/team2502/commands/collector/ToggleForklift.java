package com.team2502.commands.collector;

import com.team2502.black_box.BlackBoxProtocol;
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
		if (collectorSubsystem.isForkliftUp()) {
			BlackBoxProtocol.log("Forklift down");
			collectorSubsystem.moveForkliftDown();
		} else {
			BlackBoxProtocol.log("Forklift up");
			collectorSubsystem.moveForkliftUp();
		}
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
