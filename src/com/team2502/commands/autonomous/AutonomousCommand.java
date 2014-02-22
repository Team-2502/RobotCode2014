package com.team2502.commands.autonomous;

import com.team2502.commands.drive.MoveRobotForward;
import com.team2502.commands.shooter.CalibrateShooterEncoderBottom;
import com.team2502.commands.shooter.CalibrateShooterEncoderTop;
import com.team2502.commands.shooter.LatchTheLatch;
import com.team2502.commands.shooter.PullBackShooter;
import com.team2502.commands.shooter.ShootBall;
import com.team2502.commands.shooter.UnlatchTheLatch;
import com.team2502.commands.shooter.UnwindWinch;
import com.team2502.commands.vision.WaitForHot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Josh Larson
 */
public class AutonomousCommand extends CommandGroup {
	
	private MoveRobotForward moveRobotForward;
	
	public AutonomousCommand() {
		moveRobotForward = new MoveRobotForward(0);
		addSequential(new LatchTheLatch());
		addSequential(moveRobotForward);
		addSequential(new WaitCommand(0));
		//addSequential(new WaitForHot(10));
		// SHOOT DA BALLS SHOW NO MERCY
		addSequential(new ShootBall());
		addSequential(new WaitCommand(.1));
		// Calibrate As Top Position
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
	
}
