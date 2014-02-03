package com.team2502.subsystems;

import com.team2502.RobotMap;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author Jackson Turner
 *
 */
public class ShooterSubsystem extends Subsystem {

	private static final double LOADED_THRESHOLD = 0;  // Analog trigger threshold 
	private static final double WINCH_SPEED = 1;  // Winch speed (between -1 and 1)
	private Jaguar winch;
	private Encoder winchEncoder;
	private DigitalInput limitSwitchUp;
	private DigitalInput limitSwitchDown;
	private Solenoid latch;
	private AnalogChannel loadedSensor;
	private Compressor compressor;

	private double topWinchPosition = 1;
	private double bottomWinchPosition = 0;
	private double targetPosition;

	public ShooterSubsystem() {
		winchEncoder = new Encoder(RobotMap.SHOOTER_WINCH_ENCODER_A, RobotMap.SHOOTER_WINCH_ENCODER_B);
		limitSwitchUp = new DigitalInput(RobotMap.SHOOTER_LIMIT_SWITCH_UP);
		limitSwitchDown = new DigitalInput(RobotMap.SHOOTER_LIMIT_SWITCH_DOWN);
		latch = new Solenoid(RobotMap.SHOOTER_LATCH);
		loadedSensor = new AnalogChannel(RobotMap.SHOOTER_LOADED_SENSOR);
		compressor = new Compressor(RobotMap.COMPRESSOR_SWITCH, RobotMap.COMPRESSOR_RELAY);
		compressor.start();
	}

	@Override
	protected void initDefaultCommand() {

	}

	public void moveToPosition(double position) {
		targetPosition = position;
		if (targetPosition < getWinchPosition())
			moveWinchDown();
		else if (targetPosition > getWinchPosition())
			moveWinchUp();
		else
			stopWinch();
	}

	public void moveWinchDown() {
		if (!isDown())
			winch.set(-WINCH_SPEED);
		else
			stopWinch();
	}

	public void moveWinchUp() {
		if (!isUp())
			winch.set(WINCH_SPEED);
		else
			stopWinch();
	}

	public void stopWinch() {
		winch.set(0);
		winch.stopMotor();
	}

	public boolean isDown() {
		return limitSwitchDown.get();
	}

	public boolean isUp() {
		return limitSwitchUp.get();
	}

	public boolean isLoaded() {
		return (loadedSensor.getVoltage() > LOADED_THRESHOLD);
	}

	public boolean isLatched() {
		return latch.get();
	}

	public double getWinchPosition() {
		return (winchEncoder.getDistance() - bottomWinchPosition) / topWinchPosition;
	}

	public void activateLatch() {
		latch.set(true);
	}

	public void deactivateLatch() {
		latch.set(false);
	}

	public void setCurrentEncoderPositionAsBottom() {
		bottomWinchPosition = winchEncoder.getDistance();
	}

	public void setCurrentEncoderPositionAsTop() {
		topWinchPosition = winchEncoder.getDistance();
	}

}
