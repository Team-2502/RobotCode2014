package com.team2502.commands;

import com.team2502.black_box.BlackBoxProtocol;

/**
 *
 * @author Josh Larson
 */
public class LogToBlackBox extends CommandBase {
	
	private String message;
	
	public LogToBlackBox(String message) {
		this.message = message;
	}
	
	protected void initialize() {
		BlackBoxProtocol.log(message);
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
