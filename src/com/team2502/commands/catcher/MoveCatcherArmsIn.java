package com.team2502.commands.catcher;

import com.team2502.commands.CommandBase;

/**
 *
 * @author Josh Larson
 */
public class MoveCatcherArmsIn extends CommandBase {
	
	public MoveCatcherArmsIn() {
		requires(catcherSubsystem);
	}
	
	protected void initialize() {
		catcherSubsystem.moveArmsIn();
	}
	
	protected void execute() {
		catcherSubsystem.moveArmsIn();
	}
	
	protected boolean isFinished() {
		return true;
	}
	
	protected void end() {
		
	}
	
	protected void interrupted() {
		
	}
}
