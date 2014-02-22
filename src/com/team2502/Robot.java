package com.team2502;


import com.team2502.black_box.BlackBoxProtocol;
import com.team2502.commands.autonomous.AutonomousCommand;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import com.team2502.commands.CommandBase;
import edu.wpi.first.wpilibj.Watchdog;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	
	private AutonomousCommand autonomousCommand;
	
	public void robotInit() {
		autonomousCommand = new AutonomousCommand();
		
        // Initialize all subsystems
		OI.init();
		CommandBase.init();
		Watchdog.getInstance().setEnabled(false);
		getWatchdog().setEnabled(false);
		getWatchdog().setExpiration(500);
		BlackBoxProtocol.log("Robot Initialized");
		// Initialize Smart Dashboard
		try {
			SmartDashboard.getNumber("Auto Movement Time");
		} catch (Exception e) {
			SmartDashboard.putNumber("Auto Movement Time", 0);
		}
    }
	
	public void disabledInit() {
		BlackBoxProtocol.log("Robot Disabled");
		if (!BlackBoxProtocol.isStarted())
			BlackBoxProtocol.start(new String[]{"10.25.2.5"}, 1180, 10);
	}
	
	public void disabledPeriodic() {
		getWatchdog().feed();
		CommandBase.updateDriverStation();
	}
	
	public void autonomousInit() {
		BlackBoxProtocol.log("Autonomous Mode Started");
		autonomousCommand.setMovementForward(SmartDashboard.getNumber("Auto Movement Time"));
		autonomousCommand.start();
	}
	
	public void autonomousPeriodic() {
		getWatchdog().feed();
		Scheduler.getInstance().run();
		CommandBase.updateDriverStation();
	}
	
	public void teleopInit() {
		BlackBoxProtocol.log("Operator Control Mode Started");
		autonomousCommand.cancel();
	}
	
	public void teleopPeriodic() {
		getWatchdog().feed();
		Scheduler.getInstance().run();
		CommandBase.updateDriverStation();
	}
	
	public void testInit() {
		BlackBoxProtocol.log("Testing Mode Started");
	}
	
	public void testPeriodic() {
		getWatchdog().feed();
		LiveWindow.run();
	}
}
