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

	@Override
	protected void end() {

	}

	protected void execute() {
		if (collectorSubsystem.isBallInCollector()) {
			collectorSubsystem.moveCollectorUp();
			finished = true;
		}
	}

	@Override
	protected void initialize() {
		
	}

	@Override
	protected void interrupted() {
		end();
	}

	@Override
	protected boolean isFinished() {
		return finished && collectorSubsystem.isForkliftUp();
	}

}
