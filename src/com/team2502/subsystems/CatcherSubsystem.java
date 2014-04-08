package com.team2502.subsystems;

import com.team2502.RobotMap;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author Josh Larson
 */
public class CatcherSubsystem extends Subsystem {
	
	private static final boolean ARM_SOLENOID_OUT = true;
	private Solenoid armSolenoid;
	
	public CatcherSubsystem() {
		armSolenoid = new Solenoid(RobotMap.CATCHER_ARM_SOLENOID);
	}
	
	public void initDefaultCommand() {
		
	}
	
	public void moveArmsOut() {
		armSolenoid.set(ARM_SOLENOID_OUT);
	}
	
	public void moveArmsIn() {
		armSolenoid.set(!ARM_SOLENOID_OUT);
	}
	
	public boolean isArmsOut() {
		return armSolenoid.get() == ARM_SOLENOID_OUT;
	}
	
	public void updateDriverStation() {
		
	}
	
}
