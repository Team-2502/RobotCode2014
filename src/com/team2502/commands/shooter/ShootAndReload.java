package com.team2502.commands.shooter;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 * @author Jackson Turner
 *
 */
public class ShootAndReload extends CommandGroup {
	
	public ShootAndReload() {
		addSequential(new ShootBall());
		addSequential(new WaitCommand(.2));
		addSequential(new LoadShooter());
	}
	
}
