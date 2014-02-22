package com.team2502.commands.vision;

import com.team2502.commands.CommandBase;
import com.team2502.subsystems.VisionSubsystem;

/**
 *
 * @author josh
 */
public class WaitForHot extends CommandBase {
	
	public WaitForHot() {
		requires(visionSubsystem);
	}
	
	public WaitForHot(long timeout) {
		requires(visionSubsystem);
		setTimeout(timeout/1000.0);
	}
	
	protected void initialize() {
		
	}
	
	protected void execute() {
		
	}
	
	protected boolean isFinished() {
		return isTimedOut();
	}
	
	protected void end() {
		
	}
	
	protected void interrupted() {
		end();
	}
}
