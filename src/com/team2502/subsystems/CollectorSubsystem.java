package com.team2502.subsystems;

import com.team2502.Robot;
import com.team2502.RobotMap;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Jackson Turner
 *
 */
public class CollectorSubsystem extends Subsystem {
	
	private static final boolean UP_POSITION = false;
	private Solenoid collectorPiston;
	
	public CollectorSubsystem() {
		collectorPiston = new Solenoid(RobotMap.COLLECTOR_PISTON_PORT);
	}
	
	protected void initDefaultCommand() {

	}
	
	public void moveForkliftDown() {
		collectorPiston.set(!UP_POSITION);
	}
	
	public void moveForkliftUp() {
		collectorPiston.set(UP_POSITION);
	}
	
	public boolean isForkliftUp() {
		return collectorPiston.get() == UP_POSITION;
	}
	
	public boolean isForkliftDown() {
		return collectorPiston.get() == !UP_POSITION;
	}
	
	public void updateDriverStation() {
		
	}
	
}
