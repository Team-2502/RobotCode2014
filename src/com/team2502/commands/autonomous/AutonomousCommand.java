package com.team2502.commands.autonomous;

import com.team2502.commands.drive.MoveRobotForward;
import com.team2502.commands.shooter.CalibrateShooterEncoderBottom;
import com.team2502.commands.shooter.CalibrateShooterEncoderTop;
import com.team2502.commands.shooter.LatchTheLatch;
import com.team2502.commands.shooter.PullBackShooter;
import com.team2502.commands.shooter.ResetShooterEncoder;
import com.team2502.commands.shooter.ShootBall;
import com.team2502.commands.shooter.UnlatchTheLatch;
import com.team2502.commands.shooter.UnwindWinch;
import com.team2502.commands.vision.ActivateRingLight;
import com.team2502.commands.vision.DeactivateRingLight;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 * @author Josh Larson
 */
public class AutonomousCommand extends CommandGroup {
	
	public static final double DEFAULT_WAIT = .4;
	public static final double DEFAULT_WAIT_AFTER = .8;
	private MoveRobotForward moveRobotForward;
	private MoveRobotForward moveRobotForwardAfter;
	
	public AutonomousCommand() {
		moveRobotForward = new MoveRobotForward(DEFAULT_WAIT);
		moveRobotForwardAfter = new MoveRobotForward(DEFAULT_WAIT_AFTER);
		// Activate the ring light for vision
		addSequential(new ActivateRingLight());
		// Latch and move forward
		addSequential(new LatchTheLatch());
		addSequential(moveRobotForward);
		addSequential(new WaitCommand(1.5));
//		addSequential(new WaitForHot(10));
		// Deactivate ring light 'cause vision is done
		addSequential(new DeactivateRingLight());
		// SHOOT DA BALLS SHOW NO MERCY
		addSequential(new ShootBall());
		addSequential(new WaitCommand(.1));
		addSequential(moveRobotForwardAfter);
		// Calibrate As Top Position
		addSequential(new ResetShooterEncoder());
		addSequential(new CalibrateShooterEncoderTop());
		addSequential(new WaitCommand(.2));
		// Start Moving Winch Down
		addSequential(new UnlatchTheLatch());
		addSequential(new PullBackShooter());
		// Calibrate As Bottom Position
		addSequential(new CalibrateShooterEncoderBottom());
		addSequential(new WaitCommand(.1));
		// Latch to secure
		addSequential(new LatchTheLatch());
		addSequential(new WaitCommand(.5));
		// Unwind to previous length
		addSequential(new UnwindWinch());
	}
	
	public void setMovementForward(double time) {
		moveRobotForward.setTime(time);
	}
	
	public void setAfterMovementForward(double time) {
		moveRobotForwardAfter.setTime(time);
	}
	
}
