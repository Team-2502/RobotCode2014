package com.team2502.commands.catcher;

import com.team2502.commands.CommandBase;

/**
 *
 * @author Josh Larson
 */
public class ToggleCatcherArms extends CommandBase {
	
	private boolean armsOut;
	
	public ToggleCatcherArms() {
		requires(catcherSubsystem);
	}
	
	protected void initialize() {
		armsOut = catcherSubsystem.isArmsOut();
		if (armsOut) {
			catcherSubsystem.moveArmsIn();
		} else {
			catcherSubsystem.moveArmsOut();
		}
	}
	
	protected void execute() {
		if (armsOut) {
			catcherSubsystem.moveArmsIn();
		} else {
			catcherSubsystem.moveArmsOut();
		}
	}
	
	protected boolean isFinished() {
		return true;
	}
	
	protected void end() {
		
	}
	
	protected void interrupted() {
		
	}
}
