package com.team2502.commands.shooter;

import com.team2502.commands.collector.MoveForkliftDown;
import com.team2502.commands.collector.MoveForkliftUp;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 * @author Josh Larsons
 *
 */
public class CalibrateWinch extends CommandGroup {
	
	public CalibrateWinch() {
		// Calibrates Encoder
		addSequential(new CalibrateShooterEncoderTop());
		// Shoots the Ball
		addSequential(new ShootBall());
		addSequential(new WaitCommand(.1));
		// Unlatches for when we pull back down
		addSequential(new UnlatchTheLatch());
		// Pulls the winch back up, and calibrates it as the bottom
		addSequential(new PullBackShooter());
		addSequential(new CalibrateShooterEncoderBottom());
		addSequential(new WaitCommand(.1));
		// Latches
		addSequential(new LatchTheLatch());
		addSequential(new WaitCommand(.2));
		// Calibrates then Unwinds
		addSequential(new UnwindWinch());
	}
	
}
