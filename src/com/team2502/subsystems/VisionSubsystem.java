package com.team2502.subsystems;

import com.team2502.RaspberryPi;
import com.team2502.RaspberryPi.Target;
import com.team2502.black_box.BlackBoxProtocol;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author Josh Larson
 */
public class VisionSubsystem extends Subsystem {
	
	private RaspberryPi [] pis;
	private int piCount;
	
	public VisionSubsystem(int piCount) {
		this.piCount = piCount;
		pis = new RaspberryPi[piCount];
		for (int i = 0; i < piCount; i++) {
			pis[i] = new RaspberryPi("10.25.2." + (30+piCount));
			pis[i].initialize();
			pis[i].startProcessing();
			pis[i].setGameRecording(false);
		}
	}
	
	public void initDefaultCommand() {
		
	}
	
	public RaspberryPi getPi(int index) {
		if (index >= piCount || index < 0)
			return null;
		return pis[index];
	}
	
	public void outputTargetInfo() {
		for (int i = 0; i < piCount; i++) {
			RaspberryPi pi = pis[i];
			Target [] targetList = pi.getTargetList();
			String log = pi.getAddress() + ":[";
			for (int t = 0; t < targetList.length; t++) {
				log += targetList[t].toString();
				if (t+1 < targetList.length)
					log += ", ";
			}
			log += "]";
			BlackBoxProtocol.log(log);
		}
	}
	
}
