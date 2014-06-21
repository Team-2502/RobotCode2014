package com.team2502;


import com.team2502.black_box.BlackBoxProtocol;
import com.team2502.commands.autonomous.AutonomousCommand;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import com.team2502.commands.CommandBase;
import com.team2502.commands.vision.VisionUpdater;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Watchdog;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	
	private static final boolean COMPETITION_MODE = true;
	private AutonomousCommand autonomousCommand;
	
	public static boolean isCompetitionMode() {
		return DriverStation.getInstance().isFMSAttached() || COMPETITION_MODE;
	}
	
	private void updateConnections() {
		if (!BlackBoxProtocol.isStarted() && DriverStation.getInstance().isNewControlData()) {
			BlackBoxProtocol.start(new String[]{"10.25.2.5", "10.25.2.15"}, 1180, 10);
		}
	}
	
	public void robotInit() {
		autonomousCommand = new AutonomousCommand();
		
        // Initialize all subsystems
		OI.init();
		CommandBase.init();
		Watchdog.getInstance().setEnabled(false);
		getWatchdog().setEnabled(false);
		getWatchdog().setExpiration(500);
		BlackBoxProtocol.initialize();
		BlackBoxProtocol.log("Robot Initialized");
		// Initialize Smart Dashboard
		try {
			SmartDashboard.getNumber("Auto Movement Time");
		} catch (Exception e) {
			SmartDashboard.putNumber("Auto Movement Time", AutonomousCommand.DEFAULT_WAIT);
		}
		try {
			SmartDashboard.getNumber("Auto Movement Time After Shot");
		} catch (Exception e) {
			SmartDashboard.putNumber("Auto Movement Time After Shot", AutonomousCommand.DEFAULT_WAIT_AFTER);
		}
    }
	
	public void disabledInit() {
		BlackBoxProtocol.log("Robot Disabled");
		updateConnections();
	}
	
	public void disabledPeriodic() {
		updateConnections();
		getWatchdog().feed();
		CommandBase.updateDriverStation();
	}
	
	public void autonomousInit() {
		updateConnections();
		BlackBoxProtocol.log("Autonomous Mode Started");
		autonomousCommand.setMovementForward(SmartDashboard.getNumber("Auto Movement Time"));
		autonomousCommand.setAfterMovementForward(SmartDashboard.getNumber("Auto Movement Time After Shot"));
		autonomousCommand.start();
	}
	
	public void autonomousPeriodic() {
		updateConnections();
		getWatchdog().feed();
		Scheduler.getInstance().run();
		CommandBase.updateDriverStation();
	}
	
	public void teleopInit() {
		updateConnections();
		BlackBoxProtocol.log("Operator Control Mode Started");
		autonomousCommand.cancel();
	}
	
	public void teleopPeriodic() {
		updateConnections();
		getWatchdog().feed();
		Scheduler.getInstance().run();
		CommandBase.updateDriverStation();
	}
	
	public void testInit() {
		updateConnections();
		BlackBoxProtocol.log("Testing Mode Started");
	}
	
	public void testPeriodic() {
		updateConnections();
		getWatchdog().feed();
		LiveWindow.run();
	}
}
