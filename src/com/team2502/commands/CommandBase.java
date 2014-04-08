package com.team2502.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.team2502.OI;
import com.team2502.subsystems.CatcherSubsystem;
import com.team2502.subsystems.CollectorSubsystem;
import com.team2502.subsystems.DriveSubsystem;
import com.team2502.subsystems.ShooterSubsystem;
import com.team2502.subsystems.VisionSubsystem;

/**
 * @author All of us.. we're all authors
 */
public abstract class CommandBase extends Command {
	
	private static OI oi;
	private static long lastUpdate;
	protected static DriveSubsystem driveSubsystem = new DriveSubsystem();
	protected static ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
	protected static VisionSubsystem visionSubsystem = new VisionSubsystem();
	protected static CollectorSubsystem collectorSubsystem = new CollectorSubsystem();
	protected static CatcherSubsystem catcherSubsystem = new CatcherSubsystem();
	
	public static void init() {
        oi = new OI();
		
		// Show what command your subsystem is running on the SmartDashboard
		SmartDashboard.putData(driveSubsystem);
		SmartDashboard.putData(shooterSubsystem);
		SmartDashboard.putData(visionSubsystem);
		SmartDashboard.putData(collectorSubsystem);
		SmartDashboard.putData(catcherSubsystem);
		lastUpdate = System.currentTimeMillis();
    }
	
	public static void updateDriverStation() {
		long latency = System.currentTimeMillis() - lastUpdate;
		lastUpdate = System.currentTimeMillis();
		SmartDashboard.putNumber("Command Latency", latency);
		driveSubsystem.updateDriverStation();
		collectorSubsystem.updateDriverStation();
		shooterSubsystem.updateDriverStation();
		visionSubsystem.updateDriverStation();
		catcherSubsystem.updateDriverStation();
	}
	
	public static DriveSubsystem getDriveSubsystem() {
		return driveSubsystem;
	}
	
	public static CollectorSubsystem getCollectorSubsystem() {
		return collectorSubsystem;
	}
	
	public static ShooterSubsystem getShooterSubsystem() {
		return shooterSubsystem;
	}
	
	public static VisionSubsystem getVisionSubsystem() {
		return visionSubsystem;
	}
	
	public static CatcherSubsystem getCatcherSubsystem() {
		return catcherSubsystem;
	}
	
    public CommandBase(String name) {
        super(name);
    }

    public CommandBase() {
        super();
    }
}
