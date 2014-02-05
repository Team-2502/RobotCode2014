package com.team2502.commands.collector;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * @author Jackson Turner
 *
 */
public class WaitForBallThenCollect extends CommandGroup {

	public WaitForBallThenCollect() {
		addSequential(new WaitForBallThenRaiseCollector());
		addSequential(new MoveForkliftDown());
	}

}
