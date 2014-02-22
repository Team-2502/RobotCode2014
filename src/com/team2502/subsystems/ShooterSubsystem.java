package com.team2502.subsystems;

import com.team2502.RobotMap;
import com.team2502.black_box.BlackBoxProtocol;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Jackson Turner
 *
 */
public class ShooterSubsystem extends Subsystem {
	
	private static final double LOADED_THRESHOLD = -1000;  // Analog trigger threshold 
	private static final double WINCH_SPEED = .8;  // Winch speed (between -1 and 1)
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
	
	private boolean initalizeCANJaguar() {
		for (int i = 0; i < 3; i++) {
			try {
				winch = new CANJaguar(RobotMap.SHOOTER_WINCH_CAN_PORT);
				winch.configEncoderCodesPerRev(360);
				winch.setPositionReference(CANJaguar.PositionReference.kQuadEncoder);
				//winch.setPID(winch.getP(), winch.getI(), winch.getD());
				return true;
			} catch (CANTimeoutException e){
				BlackBoxProtocol.log("CAN-Jaguar initilization failed: " + e.toString());
			}
		}
		return false;
	}
	
	protected void initDefaultCommand() {

	}
	
	public void moveToPosition(double position) {
		targetPosition = position;
		if (targetPosition < getWinchProgress()) {
			moveWinchDown();
		} else if (targetPosition > getWinchProgress()) {
			moveWinchUp();
		} else {
			stopWinch();
		}
	}
	
	public void moveWinchDown() {
		if (winch == null && !initalizeCANJaguar()) {
			BlackBoxProtocol.log("Could not move winch down, CAN is not initialized");
			return;
		}
		for (int i = 0; i < 3; i++) {
			try {
				winch.changeControlMode(CANJaguar.ControlMode.kPercentVbus);
				winch.disableControl();
				winch.setX(-WINCH_SPEED);
				break;
			} catch (CANTimeoutException e){
				BlackBoxProtocol.log("CAN-Jaguar communication failed (moveWinchDown): " + e.toString());
			}
		}
	}
	
	public void moveWinchUp() {
		if (winch == null && !initalizeCANJaguar()) {
			BlackBoxProtocol.log("Could not move winch up, CAN is not initialized");
			return;
		}
		for (int i = 0; i < 3; i++) {
			try {
//				winch.changeControlMode(CANJaguar.ControlMode.kPosition);
//				winch.enableControl();
//				winch.setX(topWinchPosition);
				winch.changeControlMode(CANJaguar.ControlMode.kPercentVbus);
				winch.disableControl();
				winch.setX(WINCH_SPEED);
				break;
			} catch (CANTimeoutException e){
				BlackBoxProtocol.log("CAN-Jaguar communication failed (moveWinchUp): " + e.toString());
			}
		}
	}
	
	public void forceWinchDown() {
		moveWinchDown();
	}
	
	public void forceWinchUp() {
		if (winch == null && !initalizeCANJaguar()) {
			BlackBoxProtocol.log("Could not move winch up, CAN is not initialized");
			return;
		}
		for (int i = 0; i < 3; i++) {
			try {
				winch.changeControlMode(CANJaguar.ControlMode.kPercentVbus);
				winch.disableControl();
				winch.setX(WINCH_SPEED);
				break;
			} catch (CANTimeoutException e){
				BlackBoxProtocol.log("CAN-Jaguar communication failed (moveWinchUp): " + e.toString());
			}
		}
	}

	public void stopWinch() {
		if (winch == null && !initalizeCANJaguar()) {
			BlackBoxProtocol.log("Could not stop winch, CAN is not initialized");
			return;
		}
		for (int i = 0; i < 3; i++) {
			try {
				winch.disableControl();
				winch.setX(0);
				break;
			} catch (CANTimeoutException e){
				BlackBoxProtocol.log("CAN-Jaguar communication failed (stopWinch): " + e.toString());
			}
		}
	}

	public boolean isDown() {
		if (winch == null && !initalizeCANJaguar()) {
			BlackBoxProtocol.log("Could not detect reverse limit, CAN is not initialized");
			return false;
		}
		for (int i = 0; i < 3; i++) {
			try {
				return !winch.getReverseLimitOK();
			} catch (CANTimeoutException e){
				BlackBoxProtocol.log("CAN-Jaguar communication failed (isDown): " + e.toString());
			}
		}
		return false;
	}

	public boolean isUp() {
		if (winch == null && !initalizeCANJaguar()) {
			BlackBoxProtocol.log("Could not detect forward limit, CAN is not initialized");
			return false;
		}
		for (int i = 0; i < 3; i++) {
			try {
				return !winch.getForwardLimitOK();
			} catch (CANTimeoutException e){
				BlackBoxProtocol.log("CAN-Jaguar communication failed (isUp): " + e.toString());
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
		if (winch == null && !initalizeCANJaguar()) {
			BlackBoxProtocol.log("Could not get winch progress, CAN is not initialized");
			return 0;
		}
		for (int i = 0; i < 3; i++) {
			try {
				double progress = 0;
				if (bottomWinchPosition > topWinchPosition) {
					if (bottomWinchPosition-topWinchPosition == 0)
						return 0;
					progress = 1-(bottomWinchPosition - winch.getPosition()) / (bottomWinchPosition - topWinchPosition);
				} else {
					if (topWinchPosition-bottomWinchPosition == 0)
						return 0;
					progress = 1-(winch.getPosition() - bottomWinchPosition) / (topWinchPosition - bottomWinchPosition);
				}
				return progress;
			} catch (CANTimeoutException e){
				BlackBoxProtocol.log("CAN-Jaguar communication failed (getWinchProgress): " + e.toString());
			}
		}
		return 0;
	}

	public void activateLatch() {
		latch.set(false);
	}

	public void deactivateLatch() {
		latch.set(true);
	}

	public void setCurrentEncoderPositionAsBottom() {
		if (winch == null && !initalizeCANJaguar()) {
			BlackBoxProtocol.log("Could not calibrate encoder bottom, CAN is not initialized");
			return;
		}
		for (int i = 0; i < 3; i++) {
			try {
				bottomWinchPosition = winch.getPosition();
				break;
			} catch (CANTimeoutException e){
				BlackBoxProtocol.log("CAN-Jaguar communication failed (setCurrentEncoderPositionAsBottom): " + e.toString());
			}
		}
		BlackBoxProtocol.log("Bottom Winch Position: " + bottomWinchPosition);
	}

	public void setCurrentEncoderPositionAsTop() {
		if (winch == null && !initalizeCANJaguar()) {
			BlackBoxProtocol.log("Could not calibrate encoder top, CAN is not initialized");
			return;
		}
		for (int i = 0; i < 3; i++) {
			try {
				topWinchPosition = winch.getPosition();
				break;
			} catch (CANTimeoutException e){
				BlackBoxProtocol.log("CAN-Jaguar communication failed (setCurrentEncoderPositionAsTop): " + e.toString());
			}
		}
		BlackBoxProtocol.log("Top Winch Position: " + topWinchPosition);
	}
	
	public void startCompressor() {
		compressor.setRelayValue(Value.kForward);
	}
	
	public void stopCompressor() {
		compressor.setRelayValue(Value.kOff);
	}
	
	public void updateDriverStation() {
		SmartDashboard.putBoolean("Latched", !latch.get());
		SmartDashboard.putNumber("Ball Detector", loadedSensor.getVoltage());
		SmartDashboard.putBoolean("Compressing", compressor.enabled());
		SmartDashboard.putNumber("Winch Position", getWinchProgress());
	}

}
