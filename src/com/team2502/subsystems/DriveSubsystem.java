package com.team2502.subsystems;

import com.team2502.RobotMap;
import com.team2502.black_box.BlackBoxProtocol;
import com.team2502.commands.drive.DriveWithJoystick;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Josh Larson
 */
public class DriveSubsystem extends Subsystem {
	
	private static final double MOTOR_THRESHOLD = 0.04;
	private static final double FORWARD_SENSITIVITY = 1;
	private static final double TURN_SENSITIVITY = 0.8;
	private static final double STRAFE_SENSITIVITY = 1;
	private static final boolean REVERSE_FRONT_LEFT = false;
	private static final boolean REVERSE_FRONT_RIGHT = true;
	private static final boolean REVERSE_BACK_LEFT = false;
	private static final boolean REVERSE_BACK_RIGHT = true;
	private SpeedController frontLeft;
	private SpeedController frontRight;
	private SpeedController backLeft;
	private SpeedController backRight;
	private RobotDrive driveTrain;
	private Solenoid trainSwitcher;
	private boolean isTraction; // Either traction or mecanum
	
	public DriveSubsystem() {
		frontLeft = new Talon(RobotMap.DRIVE_FRONT_LEFT);
		frontRight = new Talon(RobotMap.DRIVE_FRONT_RIGHT);
		backLeft = new Talon(RobotMap.DRIVE_BACK_LEFT);
		backRight = new Talon(RobotMap.DRIVE_BACK_RIGHT);
//		driveTrain = new RobotDrive(frontLeft, backLeft, frontRight, backRight);
		trainSwitcher = new Solenoid(RobotMap.DRIVE_TRAIN_SWITCHER);
//		driveTrain.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, REVERSE_FRONT_LEFT);
//		driveTrain.setInvertedMotor(RobotDrive.MotorType.kFrontRight, REVERSE_FRONT_RIGHT);
//		driveTrain.setInvertedMotor(RobotDrive.MotorType.kRearLeft, REVERSE_BACK_LEFT);
//		driveTrain.setInvertedMotor(RobotDrive.MotorType.kRearRight, REVERSE_BACK_RIGHT);
//		driveTrain.setSafetyEnabled(false);
//		driveTrain.setExpiration(2000);
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
	public final void driveTank(double left, double right, boolean squared) {
		driveIndependent(left, right, left, right, squared);
	}
	
	/**
	 * This would be considered a "raw" method to drive the robot in a very
	 * specific way.
	 * @param forwardLeft Speed to run the forward-left motor
	 * @param forwardRight Speed to run the forward-right motor
	 * @param rearLeft Speed to run the rear-left motor
	 * @param rearRight Speed to run the rear-right motor
	 */
	public final void driveIndependent(double forwardLeft, double forwardRight, double rearLeft, double rearRight, boolean squared) {
		if (forwardLeft > 1) forwardLeft = 1;
		if (forwardLeft < -1) forwardLeft = -1;
		if (forwardRight > 1) forwardRight = 1;
		if (forwardRight < -1) forwardRight = -1;
		if (rearLeft > 1) rearLeft = 1;
		if (rearLeft < -1) rearLeft = -1;
		if (rearRight > 1) rearRight = 1;
		if (rearRight < -1) rearRight = -1;
		if (squared) {
			forwardLeft *= Math.abs(forwardLeft);
			forwardRight *= Math.abs(forwardRight);
			rearLeft *= Math.abs(rearLeft);
			rearRight *= Math.abs(rearRight);
		}
		if (Math.abs(forwardLeft) < MOTOR_THRESHOLD)
			forwardLeft = 0;
		if (Math.abs(forwardRight) < MOTOR_THRESHOLD)
			forwardRight = 0;
		if (Math.abs(rearLeft) < MOTOR_THRESHOLD)
			rearLeft = 0;
		if (Math.abs(rearRight) < MOTOR_THRESHOLD)
			rearRight = 0;
		frontLeft.set(forwardLeft * (REVERSE_FRONT_LEFT ? -1 : 1));
		frontRight.set(forwardRight * (REVERSE_FRONT_RIGHT ? -1 : 1));
		backLeft.set(rearLeft * (REVERSE_BACK_LEFT ? -1 : 1));
		backRight.set(rearRight * (REVERSE_BACK_RIGHT ? -1 : 1));
	}
	
	/**
	 * This is a convenience method, so that it is easy to make the robot move
	 * based on joysticks. This is where the left joystick controls the two left
	 * wheels, and the right joystick controls the two right wheels.
	 * @param left The left joystick for driving
	 * @param right The right joystick for driving
	 */
	public final void driveTank(Joystick left, Joystick right) {
		driveTank(left, right, false);
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
//		driveTrain.tankDrive(left, right);
		driveTank(left.getY()*FORWARD_SENSITIVITY, right.getY()*FORWARD_SENSITIVITY, false);
	}
	
	/**
		driveIndependent(forward+clockwise+righ
	 * This is a convenience method, so that it is easy to make the robot move
	 * based on a joystick. This is where the joystick drives the robot in a way
	 * that when the driver pushes forward it moves forward and it turns the
	 * robot with a rotation of the joystick.
	 * @param joy The joystick to use for driving
	 */
	public final void driveArcade(Joystick joy) {
		driveArcade(joy, false);
	}
	
	/**
	 * This is a convenience method, so that it is easy to make the robot move
	 * based on a joystick. This is where the joystick drives the robot in a way
	 * that when the driver pushes forward it moves forward and it turns the
	 * robot with a rotation of the joystick.
	 * @param joy The joystick to use for driving
	 */
	public final void driveArcade(Joystick joy, boolean squared) {
		double y = joy.getY() * FORWARD_SENSITIVITY;
		double z = joy.getZ() * TURN_SENSITIVITY;
		driveTank(y - z, y + z, squared);
//		driveTrain.arcadeDrive(joy, squared);
	}
	
	/**
	 * This is a convenience method, so that it is easy to make the robot move
	 * based on a joystick. This is where the joystick drives the robot in a way
	 * that it is able to strafe left/right as well as being able to move forward
	 * and turn.
	 * @param joy The joystick to use for driving
	 */
	public final boolean driveMecanum(Joystick joy) {
		return driveMecanum(joy, false);
	}
	
	/**
	 * This is a convenience method, so that it is easy to make the robot move
	 * based on a joystick. This is where the joystick drives the robot in a way
	 * that it is able to strafe left/right as well as being able to move forward
	 * and turn.
	 * @param joy The joystick to use for driving
	 * @param squared Squared output
	 */
	public final boolean driveMecanum(Joystick joy, boolean squared) {
//		if (isTraction)
//			return false;
		double forward = joy.getY()*FORWARD_SENSITIVITY;
		double right = -joy.getX()*STRAFE_SENSITIVITY;
		double clockwise = -joy.getZ()*TURN_SENSITIVITY;
//		driveTrain.mecanumDrive_Polar(forward, 0, clockwise);
//		return true;
		driveIndependent(forward+clockwise+right, forward-clockwise-right, forward+clockwise-right, forward-clockwise+right, squared);
		return true;
	}
	
	/**
	 * This is a convenience method, so that it is easy to make the robot move
	 * based on a joystick. This is where the joystick drives the robot in a way
	 * that it is able to strafe left/right as well as being able to move forward
	 * and turn.
	 * @param joy The joystick to use for driving
	 */
	public final boolean driveMecanum(Joystick left, Joystick right) {
		return driveMecanum(left, right, false);
	}
	
	/**
	 * This is a convenience method, so that it is easy to make the robot move
	 * based on a joystick. This is where the joystick drives the robot in a way
	 * that it is able to strafe left/right as well as being able to move forward
	 * and turn.
	 * @param joy The joystick to use for driving
	 * @param squared Squared output
	 */
	public final boolean driveMecanum(Joystick left, Joystick right, boolean squared) {
		if (isTraction)
			return false;
		double yMove = left.getY()*FORWARD_SENSITIVITY;
		double xMove = left.getX()*STRAFE_SENSITIVITY;
		double zRot = -right.getX()*TURN_SENSITIVITY;
		driveIndependent(yMove+zRot+xMove, yMove-zRot-xMove, yMove+zRot-xMove, yMove-zRot+xMove, squared);
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
		isTraction = true;
		trainSwitcher.set(isTraction);
		return true;
//		if (isTraction)
//			return true;
//		if (isMovingSideways())
//			return false;
//		isTraction = true;
//		trainSwitcher.set(isTraction);
//		return true;
	}
	
	public final void switchToMecanum() {
		isTraction = false;
		trainSwitcher.set(isTraction);
	}
	
	public final boolean toggleDriveTrain() {
		if (isTraction) {
			BlackBoxProtocol.log("Switching drive train from Traction to Mecanum");
			switchToMecanum();
			return true;
		} else {
			BlackBoxProtocol.log("Switching drive train from Mecanum to Traction");
			return switchToTraction();
		}
	}
	
	public final void updateDriverStation() {
		SmartDashboard.putString("Drive Mode", isTraction ? "Traction" : "Mecanum");
		SmartDashboard.putNumber("Front Left", frontLeft.get());
		SmartDashboard.putNumber("Front Right", frontRight.get());
		SmartDashboard.putNumber("Back Left", backLeft.get());
		SmartDashboard.putNumber("Back Right", backRight.get());
	}
	
}
