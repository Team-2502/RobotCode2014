package com.team2502.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.team2502.OI;
import com.team2502.subsystems.DriveSubsystem;

/**
 * @author Josh Larson
 */
public abstract class CommandBase extends Command {
	
	private static OI oi;
	protected static DriveSubsystem driveSubsystem = new DriveSubsystem();
	
	public static void init() {
        oi = new OI();
		
		// Show what command your subsystem is running on the SmartDashboard
		SmartDashboard.putData(driveSubsystem);
    }

    public CommandBase(String name) {
        super(name);
    }

    public CommandBase() {
        super();
    }
}
