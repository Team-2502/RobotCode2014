package com.team2502.subsystems;

import com.team2502.RaspberryPi;
import com.team2502.RaspberryPi.Target;
import com.team2502.RobotMap;
import com.team2502.black_box.BlackBoxProtocol;
import com.team2502.commands.vision.VisionUpdater;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Josh Larson
 */
public class VisionSubsystem extends Subsystem {
	
	private Solenoid ringLight;
	private RaspberryPi pi;
	
	public VisionSubsystem() {
		ringLight = new Solenoid(RobotMap.VISION_RING_LIGHT);
		pi = new RaspberryPi("10.25.2.30");
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
	
	public void updateRaspberryPi() {
		// Request Target Data
		Target [] targets = pi.getTargetList();
		for (int i = 0; i < targets.length; i++) {
			BlackBoxProtocol.log("Target #" + i + ": Angle: " + targets[i].angle);
		}
	}
	
	public void updateDriverStation() {
		SmartDashboard.putBoolean("Connected to PI", pi.isConnected());
	}
	
}
