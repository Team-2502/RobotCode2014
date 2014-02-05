package com.team2502.commands.shooter;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * @author Jackson Turner
 *
 */
public class LoadShooter extends CommandGroup{

	public LoadShooter() {
		addSequential(new PullBackShooter());
		addSequential(new LatchTheLatch());
		addSequential(new UnwindWinch());
	}

}
