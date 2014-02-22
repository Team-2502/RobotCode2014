package com.team2502.subsystems;

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
	
	private static final boolean UP_POSITION = true;
	private static final double BALL_PRESENT_THRESHOLD = 0;
	private Solenoid collectorPiston;
	private AnalogChannel ballInCollectorSensorOne;
	private AnalogChannel ballInCollectorSensorTwo;
	
	public CollectorSubsystem() {
		collectorPiston = new Solenoid(RobotMap.COLLECTOR_PISTON_PORT);
		ballInCollectorSensorOne = new AnalogChannel(RobotMap.COLLECTOR_SENSOR_ONE_PORT);
		ballInCollectorSensorTwo = new AnalogChannel(RobotMap.COLLECTOR_SENSOR_TWO_PORT);
	}
	
	protected void initDefaultCommand() {

	}
	
	public void moveCollectorDown() {
		collectorPiston.set(!UP_POSITION);
	}
	
	public void moveCollectorUp() {
		collectorPiston.set(UP_POSITION);
	}
	
	public boolean isBallInCollector() {
		return (ballInCollectorSensorOne.getVoltage() >= BALL_PRESENT_THRESHOLD || ballInCollectorSensorTwo.getVoltage() >= BALL_PRESENT_THRESHOLD);
	}
	
	public boolean isForkliftUp() {
		return collectorPiston.get() == UP_POSITION;
	}
	
	public boolean isForkliftDown() {
		return collectorPiston.get() == !UP_POSITION;
	}
	
	public void updateDriverStation() {
		SmartDashboard.putNumber("Ball Detector 1", ballInCollectorSensorOne.getVoltage());
		SmartDashboard.putNumber("Ball Detector 2", ballInCollectorSensorTwo.getVoltage());
	}
	
}
