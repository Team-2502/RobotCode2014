package com.team2502.commands.collector;

import com.team2502.commands.CommandBase;

/**
 * @author Jackson Turner
 *
 */
public class MoveForkliftDown extends CommandBase {

	public MoveForkliftDown() {
		requires(collectorSubsystem);
	}
	
	protected void end() {

	}

	protected void execute() {

	}
	
	protected void initialize() {
		collectorSubsystem.moveForkliftDown();
	}
	
	protected void interrupted() {
		end();
	}
	
	protected boolean isFinished() {
		return collectorSubsystem.isForkliftDown();
	}

}
