package com.team2502.subsystems;

import com.team2502.RobotMap;
import com.team2502.commands.vision.VisionUpdater;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Josh Larson
 */
public class VisionSubsystem extends Subsystem {
	
	private Solenoid ringLight;
	private DigitalInput hotTarget;
	private DigitalOutput startProcessing;
	private boolean processing;
	
	public VisionSubsystem() {
		ringLight = new Solenoid(RobotMap.VISION_RING_LIGHT);
		hotTarget = new DigitalInput(RobotMap.VISION_HOT_TARGET);
		startProcessing = new DigitalOutput(RobotMap.VISION_START_PROCESSING);
		processing = false;
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new VisionUpdater());
	}
	
	public void activateRingLight() {
		setRingLight(true);
	}
	
	public void deactivateRingLight() {
		setRingLight(false);
	}
	
	public void setRingLight(boolean on) {
		ringLight.set(on);
	}
	
	public boolean isTargetHot() {
		return hotTarget.get();
	}
	
	public void startProcessing() {
		setProcessing(true);
	}
	
	public void stopProcessing() {
		setProcessing(false);
	}
	
	public void setProcessing(boolean on) {
		startProcessing.set(on);
		processing = on;
	}
	
	public boolean isProcessing() {
		return processing;
	}
	
	public void updateDriverStation() {
		SmartDashboard.putBoolean("Pi Target Hot", isTargetHot());
		SmartDashboard.putBoolean("Pi Processing", processing);
	}
	
}
