package com.team2502.commands.shooter;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * @author Jackson Turner
 *
 */
public class ShootAndCalibrate extends CommandGroup {

	public ShootAndCalibrate() {
		addSequential(new ShootBall());
		addSequential(new CalibrateShooterEncoderTop());
		addSequential(new PullBackShooter());
		addSequential(new CalibrateShooterEncoderBottom());
		addSequential(new LoadShooter());
	}

}
