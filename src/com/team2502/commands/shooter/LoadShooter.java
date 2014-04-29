package com.team2502.commands.shooter;

import com.team2502.commands.LogToBlackBox;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 * @author Jackson Turner
 *
 */
public class LoadShooter extends CommandGroup {
	
	public LoadShooter() {
		addSequential(new LogToBlackBox("Reloading shooter..."));
		addSequential(new UnlatchTheLatch());
		addSequential(new PullBackShooter());
		addSequential(new LatchTheLatch());
		addSequential(new WaitCommand(.5));
		addSequential(new UnwindWinch());
	}

}
