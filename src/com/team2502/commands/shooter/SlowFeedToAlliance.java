package com.team2502.commands.shooter;

import com.team2502.commands.LogToBlackBox;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 * @author Josh Larson
 */
public class SlowFeedToAlliance extends CommandGroup {
	
	public SlowFeedToAlliance() {
		addSequential(new LogToBlackBox("Slow feeding"));
		addSequential(new WindWinchDown());
		addSequential(new UnlatchTheLatch());
		addSequential(new WaitCommand(.1));
		addSequential(new WindWinchUp());
	}
	
}
