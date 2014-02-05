package com.team2502.subsystems;

import com.team2502.RobotMap;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author Jackson Turner
 *
 */
public class CollectorSubsystem extends Subsystem {

	private static final double BALL_PRESENT_THRESHOLD = 0;
	private Solenoid collectorPiston;
	private AnalogChannel ballInCollectorSensorOne;
	private AnalogChannel ballInCollectorSensorTwo;
	private DigitalInput upLimitSwitch;
	private DigitalInput downLimitSwitch;

	public CollectorSubsystem() {
		collectorPiston = new Solenoid(RobotMap.COLLECTOR_PISTON_PORT);
		ballInCollectorSensorOne = new AnalogChannel(RobotMap.COLLECTOR_SENSOR_ONE_PORT);
		ballInCollectorSensorTwo = new AnalogChannel(RobotMap.COLLECTOR_SENSOR_TWO_PORT);
		upLimitSwitch = new DigitalInput(RobotMap.COLLECTOR_UP_LIMIT);
		downLimitSwitch = new DigitalInput(RobotMap.COLLECTOR_DOWN_LIMIT);
	}

	@Override
	protected void initDefaultCommand() {

	}

	public void moveCollectorDown() {
		if (isForkliftUp())
			collectorPiston.set(true);
	}

	public void moveCollectorUp() {
		if (isForkliftDown())
			collectorPiston.set(false);
	}

	public boolean isBallInCollector() {
		return (ballInCollectorSensorOne.getVoltage() > BALL_PRESENT_THRESHOLD && ballInCollectorSensorTwo.getVoltage() > BALL_PRESENT_THRESHOLD);
	}

	public boolean isForkliftUp() {
		return upLimitSwitch.get();
	}
	
	public boolean isForkliftDown() {
		return downLimitSwitch.get();
	}

}
