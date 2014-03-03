package com.team2502.commands.shooter;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author Josh Larson
 */
public class SlowFeedToAlliance extends CommandGroup {
	
	public SlowFeedToAlliance() {
		addSequential(new WindWinchDown());
		addSequential(new UnlatchTheLatch());
		addSequential(new WindWinchUp());
	}
	
}
