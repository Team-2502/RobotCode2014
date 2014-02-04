package com.team2502.commands.shooter;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * @author Jackson Turner
 *
 */
public class ShootAndReload extends CommandGroup {

	public ShootAndReload() {
		addSequential(new ShootBall());
		addSequential(new LoadShooter());
	}

}
