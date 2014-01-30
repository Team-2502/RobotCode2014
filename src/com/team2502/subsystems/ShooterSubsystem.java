/**
 * 
 */
package com.team2502.subsystems;

import com.team2502.RobotMap;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author Jackson Turner
 *
 */
public class ShooterSubsystem extends Subsystem {
	
	private static final double LOADED_THRESHOLD = 0;  // Analog trigger threshold 
	//TODO winch control
	private Encoder winchEncoder;
	private DigitalInput limitSwitchUp;
	private DigitalInput limitSwitchDown;
	private Solenoid latch;
	private AnalogChannel loadedSensor;
	private Compressor compressor;
	
	public ShooterSubsystem() {
		winchEncoder = new Encoder(RobotMap.SHOOTER_WINCH_ENCODER_ONE, RobotMap.SHOOTER_WINCH_ENCODER_TWO);
		limitSwitchUp = new DigitalInput(RobotMap.SHOOTER_LIMIT_SWITCH_UP);
		limitSwitchDown = new DigitalInput(RobotMap.SHOOTER_LIMIT_SWITCH_DOWN);
		latch = new Solenoid(RobotMap.SHOOTER_LATCH);
		loadedSensor = new AnalogChannel(RobotMap.SHOOTER_LOADED_SENSOR);
		compressor = new Compressor(RobotMap.COMPRESSOR_PORT_ONE, RobotMap.COMPRESSOR_PORT_TWO);
	}

	@Override
	protected void initDefaultCommand() {
		
	}
	
	public void moveWinchDistance(double distance) {
		//TODO winch control
	}
	
	public void moveWinchDown() {
		//TODO winch control
	}
	
	public void moveWinchUp() {
		//TODO winch control
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
		return winchEncoder.getDistance();
	}
	
	public void activateLatch() {
		latch.set(true);
	}
	
	public void deactivateLatch() {
		latch.set(false);
	}
	
	public void resetEncoderPosition() {
		winchEncoder.reset();
	}
	
	public void setCurrentEncoderPositionAsTop() {
		//TODO I need to figure out how to do this.
	}

}
