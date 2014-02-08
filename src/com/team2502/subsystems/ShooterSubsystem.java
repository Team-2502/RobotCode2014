package com.team2502.subsystems;

import com.team2502.RobotMap;
import com.team2502.black_box.BlackBoxProtocol;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Compressor;
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

	public ShooterSubsystem() {
		initalizeCANJaguar();
		latch = new Solenoid(RobotMap.SHOOTER_LATCH);
		loadedSensor = new AnalogChannel(RobotMap.SHOOTER_LOADED_SENSOR);
		compressor = new Compressor(RobotMap.COMPRESSOR_SWITCH, RobotMap.COMPRESSOR_RELAY);
		compressor.start();
	}

	private void initalizeCANJaguar() {
		boolean retry = true;
		while (retry) {
			try {
				winch = new CANJaguar(RobotMap.SHOOTER_WINCH_CAN_PORT);
				retry = false;
			} catch (CANTimeoutException e){
				BlackBoxProtocol.log("CAN-Jaguar initilization failed: " + e.toString());
			}
		}
	}
	
	protected void initDefaultCommand() {

	}
	
	public void moveToPosition(double position) {
		targetPosition = position;
		if (targetPosition < getWinchProgress())
			moveWinchDown();
		else if (targetPosition > getWinchProgress())
			moveWinchUp();
		else
			stopWinch();
	}

	public void moveWinchDown() {
		boolean retry = true;
		while (retry) {
			try {
				winch.setX(-WINCH_SPEED);
				retry = false;
			} catch (CANTimeoutException e){
				BlackBoxProtocol.log("CAN-Jaguar initilization failed: " + e.toString());
			}
		}
	}

	public void moveWinchUp() {
		boolean retry = true;
		while (retry) {
			try {
				winch.setX(WINCH_SPEED);
				retry = false;
			} catch (CANTimeoutException e){
				BlackBoxProtocol.log("CAN-Jaguar initilization failed: " + e.toString());
			}
		}
	}

	public void stopWinch() {
		boolean retry = true;
		while (retry) {
			try {
				winch.setX(0);
				retry = false;
			} catch (CANTimeoutException e){
				BlackBoxProtocol.log("CAN-Jaguar initilization failed: " + e.toString());
			}
		}
	}

	public boolean isDown() {
		boolean retry = true;
		while (retry) {
			try {
				return !winch.getReverseLimitOK();
			} catch (CANTimeoutException e){
				BlackBoxProtocol.log("CAN-Jaguar initilization failed: " + e.toString());
			}
		}
		return false;
	}

	public boolean isUp() {
		boolean retry = true;
		while (retry) {
			try {
				return !winch.getForwardLimitOK();
			} catch (CANTimeoutException e){
				BlackBoxProtocol.log("CAN-Jaguar initilization failed: " + e.toString());
			}
		}
		return false;
	}

	public boolean isLoaded() {
		return (loadedSensor.getVoltage() > LOADED_THRESHOLD);
	}

	public boolean isLatched() {
		return latch.get();
	}

	public double getWinchProgress() {
		boolean retry = true;
		while (retry) {
			try {
				return  1 - (winch.getPosition() - bottomWinchPosition) / topWinchPosition;
			} catch (CANTimeoutException e){
				BlackBoxProtocol.log("CAN-Jaguar initilization failed: " + e.toString());
			}
		}
		return 0;
	}

	public void activateLatch() {
		latch.set(true);
	}

	public void deactivateLatch() {
		latch.set(false);
	}

	public void setCurrentEncoderPositionAsBottom() {
		boolean retry = true;
		while (retry) {
			try {
				bottomWinchPosition = winch.getPosition();
				retry = false;
			} catch (CANTimeoutException e){
				BlackBoxProtocol.log("CAN-Jaguar initilization failed: " + e.toString());
			}
		}
	}

	public void setCurrentEncoderPositionAsTop() {
		boolean retry = true;
		while (retry) {
			try {
				topWinchPosition = winch.getPosition();
				retry = false;
			} catch (CANTimeoutException e){
				BlackBoxProtocol.log("CAN-Jaguar initilization failed: " + e.toString());
			}
		}
	}

}
