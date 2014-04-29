package com.team2502.commands.catcher;

import com.team2502.black_box.BlackBoxProtocol;
import com.team2502.commands.CommandBase;

/**
 *
 * @author Josh Larson
 */
public class MoveCatcherArmsOut extends CommandBase {
	
	public MoveCatcherArmsOut() {
		requires(catcherSubsystem);
	}
	
	protected void initialize() {
		BlackBoxProtocol.log("Catcher out");
		catcherSubsystem.moveArmsOut();
	}
	
	protected void execute() {
		catcherSubsystem.moveArmsOut();
	}
	
	protected boolean isFinished() {
		return true;
	}
	
	protected void end() {
		
	}
	
	protected void interrupted() {
		
	}
}
