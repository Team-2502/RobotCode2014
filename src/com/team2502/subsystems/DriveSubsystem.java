package com.team2502.subsystems;

import com.team2502.RobotMap;
import com.team2502.commands.drive.DriveWithJoystick;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author Josh Larson
 */
public class DriveSubsystem extends Subsystem {
	
	private static final boolean REVERSE_LEFT = false;
	private static final boolean REVERSE_RIGHT = true;
	private SpeedController frontLeft;
	private SpeedController frontRight;
	private SpeedController backLeft;
	private SpeedController backRight;
	private Solenoid trainSwitcher;
	private boolean isTraction; // Either traction or mecanum
	
	public DriveSubsystem() {
		frontLeft = new Talon(RobotMap.DRIVE_FRONT_LEFT);
		frontRight = new Talon(RobotMap.DRIVE_FRONT_RIGHT);
		backLeft = new Talon(RobotMap.DRIVE_BACK_LEFT);
		backRight = new Talon(RobotMap.DRIVE_BACK_RIGHT);
		trainSwitcher = new Solenoid(RobotMap.DRIVE_TRAIN_SWITCHER);
		switchToMecanum();
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoystick());
	}
	
	/**
	 * Stops the robot from moving alltogether
	 */
	public void stopMoving() {
		frontLeft.set(0);
		frontRight.set(0);
		backLeft.set(0);
		backRight.set(0);
	}
	
	/**
	 * This would be considered a "raw" method to drive the robot in a very
	 * specific way.
	 * @param left Speed to run the two left motors
	 * @param right Speed to run the two right motors
	 */
	public final void driveTank(double left, double right) {
		if (left > 1) left = 1;
		if (left < -1) left = -1;
		if (right > 1) right = 1;
		if (right < -1) right = -1;
		frontLeft.set(left * (REVERSE_LEFT ? -1 : 1));
		frontRight.set(right * (REVERSE_RIGHT ? -1 : 1));
		backLeft.set(left * (REVERSE_LEFT ? -1 : 1));
		backRight.set(right * (REVERSE_RIGHT ? -1 : 1));
	}
	
	/**
	 * This would be considered a "raw" method to drive the robot in a very
	 * specific way.
	 * @param forwardLeft Speed to run the forward-left motor
	 * @param forwardRight Speed to run the forward-right motor
	 * @param rearLeft Speed to run the rear-left motor
	 * @param rearRight Speed to run the rear-right motor
	 */
	public final void driveIndependent(double forwardLeft, double forwardRight, double rearLeft, double rearRight) {
		if (forwardLeft > 1) forwardLeft = 1;
		if (forwardLeft < -1) forwardLeft = -1;
		if (forwardRight > 1) forwardRight = 1;
		if (forwardRight < -1) forwardRight = -1;
		if (rearLeft > 1) rearLeft = 1;
		if (rearLeft < -1) rearLeft = -1;
		if (rearRight > 1) rearRight = 1;
		if (rearRight < -1) rearRight = -1;
		frontLeft.set(forwardLeft * (REVERSE_LEFT ? -1 : 1));
		frontRight.set(forwardRight * (REVERSE_RIGHT ? -1 : 1));
		backLeft.set(rearLeft * (REVERSE_LEFT ? -1 : 1));
		backRight.set(rearRight * (REVERSE_RIGHT ? -1 : 1));
	}
	
	/**
	 * This is a convenience method, so that it is easy to make the robot move
	 * based on joysticks. This is where the left joystick controls the two left
	 * wheels, and the right joystick controls the two right wheels.
	 * @param left The left joystick for driving
	 * @param right The right joystick for driving
	 */
	public final void driveTank(Joystick left, Joystick right) {
		driveTank(left.getY(), right.getY());
	}
	
	/**
	 * This is a convenience method, so that it is easy to make the robot move
	 * based on joysticks. This is where the left joystick controls the two left
	 * wheels, and the right joystick controls the two right wheels.
	 * @param left The left joystick for driving
	 * @param right The right joystick for driving
	 * @param squared Whether the input is squared or not
	 */
	public final void driveTank(Joystick left, Joystick right, boolean squared) {
		driveTank(left.getY()*left.getY(), right.getY()*right.getY());
	}
	
	/**
	 * This is a convenience method, so that it is easy to make the robot move
	 * based on a joystick. This is where the joystick drives the robot in a way
	 * that when the driver pushes forward it moves forward and it turns the
	 * robot with a rotation of the joystick.
	 * @param joy The joystick to use for driving
	 */
	public final void driveArcade(Joystick joy) {
		driveTank(joy.getY() + joy.getZ(), joy.getY() - joy.getZ());
	}
	
	/**
	 * This is a convenience method, so that it is easy to make the robot move
	 * based on a joystick. This is where the joystick drives the robot in a way
	 * that when the driver pushes forward it moves forward and it turns the
	 * robot with a rotation of the joystick.
	 * @param joy The joystick to use for driving
	 */
	public final void driveArcade(Joystick joy, boolean squared) {
		double left = joy.getY() + joy.getZ();
		double right = joy.getY() - joy.getZ();
		if (left > 1) left = 1;
		if (left < -1) left = -1;
		if (right > 1) right = 1;
		if (right < -1) right = -1;
		if (squared) {
			left *= left;
			right *= right;
		}
		driveTank(left, right);
	}
	
	/**
	 * This is a convenience method, so that it is easy to make the robot move
	 * based on a joystick. This is where the joystick drives the robot in a way
	 * that it is able to strafe left/right as well as being able to move forward
	 * and turn.
	 * @param joy The joystick to use for driving
	 */
	public final boolean driveMecanum(Joystick joy) {
		if (isTraction)
			return false;
		double forward = -joy.getY();
		double right = joy.getX();
		double clockwise = joy.getZ();
		driveIndependent(forward+clockwise+right, forward-clockwise-right, forward+clockwise-right, forward-clockwise+right);
		return true;
	}
	
	public final boolean isMovingSideways() {
		double percentage = .1;
		double left = Math.abs(frontLeft.get() + backLeft.get()) / 2;
		double right = Math.abs(frontRight.get() + backRight.get()) / 2;
		boolean opposite = ((left > 0 && right < 0) || (left < 0 && right > 0));
		boolean leftPassed = (left > percentage) && opposite;
		boolean rightPassed = (right > percentage) && opposite;
		return !(leftPassed && rightPassed);
	}
	
	public final boolean isTraction() {
		return isTraction;
	}
	
	public final boolean switchToTraction() {
		if (isMovingSideways())
			return false;
		isTraction = true;
		trainSwitcher.set(isTraction);
		return true;
	}
	
	public final void switchToMecanum() {
		isTraction = false;
		trainSwitcher.set(isTraction);
	}
	
}
