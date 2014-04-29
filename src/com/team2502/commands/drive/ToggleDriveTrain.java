package com.team2502.commands.drive;

import com.team2502.black_box.BlackBoxProtocol;
import com.team2502.commands.CommandBase;

/**
 *
 * @author Josh Larson
 */
public class ToggleDriveTrain extends CommandBase {
	
	private boolean switchSucceed;
	
	public ToggleDriveTrain() {
		requires(driveSubsystem);
		switchSucceed = false;
	}
	
	protected void initialize() {
		
	}
	
	protected void execute() {
		switchSucceed = driveSubsystem.toggleDriveTrain();
		if (driveSubsystem.isTraction()) {
			BlackBoxProtocol.log("Switched to traction");
		} else {
			BlackBoxProtocol.log("Switched to mecanum");
		}
	}
	
	protected boolean isFinished() {
		return switchSucceed;
	}
	
	protected void end() {
		
	}
	
	protected void interrupted() {
		end();
	}
}
