package com.team2502.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author Josh Larson
 */
public class DriveSubsystem extends Subsystem {
	
	// TODO: Add all the motor controllers here (Talons, Jaguars, etc)
	// TODO: Add all sensors here (Encoders, Obstacle detecting IR sensors, etc)
	
	public void initDefaultCommand() {
		
	}
	
	/**
	 * This would be considered a "raw" method to drive the robot in a very
	 * specific way.
	 * @param left Speed to run the two left motors
	 * @param right Speed to run the two right motors
	 */
	public void driveTank(double left, double right) {
		
	}
	
	/**
	 * This would be considered a "raw" method to drive the robot in a very
	 * specific way.
	 * @param forwardLeft Speed to run the forward-left motor
	 * @param forwardRight Speed to run the forward-right motor
	 * @param rearLeft Speed to run the rear-left motor
	 * @param rearRight Speed to run the rear-right motor
	 */
	public void driveMecanum(double forwardLeft, double forwardRight, double rearLeft, double rearRight) {
		
	}
	
	/**
	 * This is a convenience method, so that it is easy to make the robot move
	 * based on joysticks. This is where the left joystick controls the two left
	 * wheels, and the right joystick controls the two right wheels.
	 * @param left The left joystick for driving
	 * @param right The right joystick for driving
	 */
	public void driveTank(Joystick left, Joystick right) {
		
	}
	
	/**
	 * This is a convenience method, so that it is easy to make the robot move
	 * based on a joystick. This is where the joystick drives the robot in a way
	 * that when the driver pushes forward it moves forward and it turns the
	 * robot with a rotation of the joystick.
	 * @param joy The joystick to use for driving
	 */
	public void driveArcade(Joystick joy) {
		
	}
	
	/**
	 * This is a convenience method, so that it is easy to make the robot move
	 * based on a joystick. This is where the joystick drives the robot in a way
	 * that it is able to strafe left/right as well as being able to move forward
	 * and turn.
	 * @param joy The joystick to use for driving
	 */
	public void driveMecanum(Joystick joy) {
		
	}
	
}
