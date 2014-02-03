package com.team2502.subsystems;

import com.team2502.RobotMap;
import com.team2502.black_box.BlackBoxProtocol;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author Jackson Turner
 *
 */
public class ShooterSubsystem extends Subsystem {

	private static final double LOADED_THRESHOLD = 0;  // Analog trigger threshold 
	private static final double WINCH_SPEED = 1;  // Winch speed (between -1 and 1)
	private CANJaguar winch;
	private Solenoid latch;
	private AnalogChannel loadedSensor;
	private Compressor compressor;

	private double topWinchPosition = 1;
	private double bottomWinchPosition = 0;
	private double targetPosition;

	public ShooterSubsystem() throws CANTimeoutException {
		winch = new CANJaguar(RobotMap.SHOOTER_WINCH_CAN_PORT);
		latch = new Solenoid(RobotMap.SHOOTER_LATCH);
		loadedSensor = new AnalogChannel(RobotMap.SHOOTER_LOADED_SENSOR);
		compressor = new Compressor(RobotMap.COMPRESSOR_SWITCH, RobotMap.COMPRESSOR_RELAY);
		compressor.start();
	}

	@Override
	protected void initDefaultCommand() {

	}

	@Deprecated
	public void moveToPosition(double position) throws CANTimeoutException {
		targetPosition = position;
		if (targetPosition < getWinchProgress())
			moveWinchDown();
		else if (targetPosition > getWinchProgress())
			moveWinchUp();
		else
			stopWinch();
	}

	public void moveWinchDown() throws CANTimeoutException {
		winch.setX(-WINCH_SPEED);
	}

	public void moveWinchUp() throws CANTimeoutException {
		winch.setX(WINCH_SPEED);
	}

	public void stopWinch() throws CANTimeoutException {
		winch.setX(0);
		winch.disableControl();
	}

	public boolean isDown() throws CANTimeoutException {
		return !winch.getReverseLimitOK();
	}

	public boolean isUp() throws CANTimeoutException {
		return !winch.getForwardLimitOK();
	}

	public boolean isLoaded() {
		return (loadedSensor.getVoltage() > LOADED_THRESHOLD);
	}

	public boolean isLatched() {
		return latch.get();
	}

	public double getWinchProgress() throws CANTimeoutException {
		return  1 - (winch.getPosition() - bottomWinchPosition) / topWinchPosition;
	}

	public void activateLatch() {
		latch.set(true);
	}

	public void deactivateLatch() {
		latch.set(false);
	}

	public void setCurrentEncoderPositionAsBottom() throws CANTimeoutException {
		bottomWinchPosition = winch.getPosition();
	}

	public void setCurrentEncoderPositionAsTop() throws CANTimeoutException {
		topWinchPosition = winch.getPosition();
	}

}
