package com.team2502;


import com.team2502.black_box.BlackBoxProtocol;
import com.team2502.commands.autonomous.AutonomousCommand;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import com.team2502.commands.CommandBase;

public class Robot extends IterativeRobot {
	
    Command autonomousCommand;
	
    public void robotInit() {
		autonomousCommand = new AutonomousCommand();
		
        // Initialize all subsystems
		OI.init();
        CommandBase.init();
		BlackBoxProtocol.start(new String[]{"10.25.2.15","10.25.2.10","10.25.2.20"}, 1180, 25);
    }
	
    public void autonomousInit() {
        autonomousCommand.start();
    }
	
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
        autonomousCommand.cancel();
    }

    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
    
    public void testPeriodic() {
        LiveWindow.run();
    }
}
