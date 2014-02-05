package com.team2502.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.team2502.OI;
import com.team2502.subsystems.DriveSubsystem;
import com.team2502.subsystems.ShooterSubsystem;
import com.team2502.subsystems.VisionSubsystem;

/**
 * @author All of us.. we're all authors
 */
public abstract class CommandBase extends Command {
	
	private static OI oi;
	protected static DriveSubsystem driveSubsystem = new DriveSubsystem();
	protected static ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
	protected static VisionSubsystem visionSubsystem = new VisionSubsystem(1);
	
	public static void init() {
        oi = new OI();
		
		// Show what command your subsystem is running on the SmartDashboard
		SmartDashboard.putData(driveSubsystem);
		SmartDashboard.putData(shooterSubsystem);
		SmartDashboard.putData(visionSubsystem);
    }

    public CommandBase(String name) {
        super(name);
    }

    public CommandBase() {
        super();
    }
}
