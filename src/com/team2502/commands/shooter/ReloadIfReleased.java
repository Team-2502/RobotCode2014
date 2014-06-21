package com.team2502.commands.shooter;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 * @author Jackson Turner
 *
 */
public class ReloadIfReleased extends CommandGroup {
	
	public ReloadIfReleased() {
		addSequential(new ShootBall());
		addSequential(new WaitCommand(.5));
		addSequential(new LoadShooter());
	}
	
}
