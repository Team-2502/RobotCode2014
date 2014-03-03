package com.team2502.commands.shooter;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 * @author Jackson Turner
 *
 */
public class ShootAndCalibrate extends CommandGroup {
	
	public ShootAndCalibrate() {
		// Shoots the Ball
		addSequential(new ShootBall());
		addSequential(new WaitCommand(.5));
		// Calibrates Encoder
		addSequential(new ResetShooterEncoder());
		addSequential(new CalibrateShooterEncoderTop());
		// Unlatches for when we pull back down
		addSequential(new UnlatchTheLatch());
		// Pulls the winch back up
		addSequential(new PullBackShooter());
		addSequential(new WaitCommand(.1));
		// Latches
		addSequential(new LatchTheLatch());
		addSequential(new WaitCommand(.1));
		// Calibrates then Unwinds
		addSequential(new CalibrateShooterEncoderBottom());
		addSequential(new UnwindWinch());
	}
	
}
