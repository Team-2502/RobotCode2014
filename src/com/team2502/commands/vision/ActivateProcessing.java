package com.team2502.commands.vision;

import com.team2502.black_box.BlackBoxProtocol;
import com.team2502.commands.CommandBase;

/**
 *
 * @author Josh Larson
 */
public class ActivateProcessing extends CommandBase {
	
	public ActivateProcessing() {
		requires(visionSubsystem);
	}
	
	protected void initialize() {
		BlackBoxProtocol.log("Started pi processing");
		visionSubsystem.setProcessing(true);
	}
	
	protected void execute() {
		
	}
	
	protected boolean isFinished() {
		return true;
	}
	
	protected void end() {
		
	}
	
	protected void interrupted() {
		
	}
}
