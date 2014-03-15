package com.team2502.commands.collector;

import com.team2502.commands.CommandBase;

/**
 * @author Jackson Turner
 *
 */
public class MoveForkliftUp extends CommandBase {

	public MoveForkliftUp() {
		requires(collectorSubsystem);
	}
	
	protected void end() {

	}
	
	protected void execute() {

	}
	
	protected void initialize() {
		collectorSubsystem.moveForkliftUp();
	}
	
	protected void interrupted() {
		end();
	}
	
	protected boolean isFinished() {
		return collectorSubsystem.isForkliftUp();
	}

}
