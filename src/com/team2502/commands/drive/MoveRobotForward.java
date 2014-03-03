package com.team2502.commands.drive;

import com.team2502.commands.CommandBase;

/**
 *
 * @author Josh Larson
 */
public class MoveRobotForward extends CommandBase {
	
	private static final double SPEED = -1;
	private long startedTime;
	private double seconds;
	
	public MoveRobotForward(double seconds) {
		requires(driveSubsystem);
		startedTime = System.currentTimeMillis();
		this.seconds = seconds;
	}
	
	public void setTime(double seconds) {
		this.seconds = seconds;
	}
	
	protected void initialize() {
		driveSubsystem.driveTank(SPEED * (seconds < 0 ? -1 : 1), SPEED * (seconds < 0 ? -1 : 1));
		startedTime = System.currentTimeMillis();
	}
	
	protected void execute() {
		driveSubsystem.driveTank(SPEED * (seconds < 0 ? -1 : 1), SPEED * (seconds < 0 ? -1 : 1));
	}
	
	protected boolean isFinished() {
		return (System.currentTimeMillis() - startedTime) >= Math.abs(seconds)*1000;
	}
	
	protected void end() {
		driveSubsystem.stopMoving();
	}
	
	protected void interrupted() {
		end();
	}
}
