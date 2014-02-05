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

	@Override
	protected void end() {

	}

	protected void execute() {

	}

	@Override
	protected void initialize() {
		collectorSubsystem.moveCollectorUp();
	}

	@Override
	protected void interrupted() {
		end();
	}

	@Override
	protected boolean isFinished() {
		return collectorSubsystem.isForkliftUp();
	}

}
