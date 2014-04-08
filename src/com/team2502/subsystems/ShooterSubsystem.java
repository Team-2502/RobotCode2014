package com.team2502.subsystems;

import com.team2502.RobotMap;
import com.team2502.black_box.BlackBoxProtocol;

import com.team2502.commands.shooter.StopWinch;
import com.team2502.commands.shooter.WindWinchUp;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Jackson Turner
 *
 */
public class ShooterSubsystem extends Subsystem {
	
	private static final double P = 1;
	private static final double I = 0.02;
	private static final double D = 0;
	private static final boolean PID_TUNING = true;
	private static final int TICKS_PER_REVOLUTION = 360;
	private static final double LOADED_THRESHOLD = -1000;  // Analog trigger threshold 
	private static final double WINCH_SPEED = 1;  // Winch speed (between -1 and 1)
	private PIDController controller;
	private Encoder winchEncoder;
	private CANJaguar winch;
	private Solenoid latch;
	private AnalogChannel loadedSensor;
	private Compressor compressor;
	private WinchSafetyThread winchSafety;
	
	private double topWinchPosition = 1;
	private double bottomWinchPosition = 0;
	private double targetPosition;
	private long lastCANRetry = -1;
	
	public ShooterSubsystem() {
		winchSafety = new WinchSafetyThread();
		initalizeCANJaguar();
		latch = new Solenoid(RobotMap.SHOOTER_LATCH);
		loadedSensor = new AnalogChannel(RobotMap.SHOOTER_LOADED_SENSOR);
		compressor = new Compressor(RobotMap.COMPRESSOR_SWITCH, RobotMap.COMPRESSOR_RELAY);
		compressor.start();
		SmartDashboard.putNumber("P", P);
		SmartDashboard.putNumber("I", I);
		SmartDashboard.putNumber("D", D);
	}
	
	private boolean initalizeCANJaguar() {
		if (lastCANRetry != -1 && System.currentTimeMillis() - lastCANRetry < 200)
			return false;
		lastCANRetry = System.currentTimeMillis();
		for (int i = 0; i < 3; i++) {
			try {
				winch = new CANJaguar(RobotMap.SHOOTER_WINCH_CAN_PORT);
				winch.configEncoderCodesPerRev(360);
				winch.setPositionReference(CANJaguar.PositionReference.kQuadEncoder);
				winch.changeControlMode(CANJaguar.ControlMode.kPercentVbus);
				winch.disableControl();
				initializeWinch();
				if (!winchSafety.isRunning())
					winchSafety.start();
				return true;
			} catch (CANTimeoutException e){
				BlackBoxProtocol.log("CAN-Jaguar initilization failed: " + e.toString());
			}
		}
		return false;
	}
	
	private void initializeWinch() {
		winchEncoder = new Encoder(RobotMap.SHOOTER_WINCH_ENCODER_A, RobotMap.SHOOTER_WINCH_ENCODER_B);
		winchEncoder.reset();
		winchEncoder.setDistancePerPulse(1);
		winchEncoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kDistance);
		winchEncoder.start();
		controller = new PIDController(P, I, D, winchEncoder, winch);
		controller.setOutputRange(-1, 1);
		controller.setPID(P, I, D);
	}
	
	protected void initDefaultCommand() {
		setDefaultCommand(new WindWinchUp());
	}
	
	public void moveToPosition(double position) {
		targetPosition = position;
		if (targetPosition < getWinchProgress()) {
			moveWinchDown();
		} else if (targetPosition > getWinchProgress()) {
			moveWinchUp();
		} else {
			stopWinch();
		}
	}
	
	public void moveWinchDown() {
		if (winch == null && !initalizeCANJaguar()) {
			BlackBoxProtocol.log("Could not move winch down, CAN is not initialized");
			return;
		}
		controller.disable();
		for (int i = 0; i < 3; i++) {
			try {
				winch.setX(-WINCH_SPEED);
				break;
			} catch (CANTimeoutException e){
				BlackBoxProtocol.log("CAN-Jaguar communication failed (moveWinchDown): " + e.toString());
			}
		}
	}
	
	public void moveWinchUp() {
		moveWinchUpPID();
	}
	
	public void moveWinchDownPID() {
		if (controller == null && !initalizeCANJaguar()) {
			return;
		}
		controller.setSetpoint(bottomWinchPosition);
		double range = Math.abs(topWinchPosition - bottomWinchPosition);
		controller.setAbsoluteTolerance(range * .01);
		controller.setContinuous(false);
		double p = SmartDashboard.getNumber("P");
		double i = SmartDashboard.getNumber("I");
		double d = SmartDashboard.getNumber("D");
		controller.setPID(	p, i, d);
		controller.enable();
	}
	
	public void moveWinchUpPID() {
		if (controller == null && !initalizeCANJaguar()) {
			return;
		}
		controller.setSetpoint(topWinchPosition);
		controller.enable();
	}
	
	public void forceWinchDown() {
		moveWinchDown();
	}
	
	public void forceWinchUp() {
		if (controller == null && !initalizeCANJaguar()) {
			return;
		}
		controller.disable();
		for (int i = 0; i < 3; i++) {
			try {
				winch.setX(WINCH_SPEED);
				break;
			} catch (CANTimeoutException e){
				BlackBoxProtocol.log("CAN-Jaguar communication failed (moveWinchUp): " + e.toString());
			}
		}
	}
	
	public void stopWinch() {
		if (controller == null && !initalizeCANJaguar()) {
			return;
		}
		controller.disable();
		for (int i = 0; i < 3; i++) {
			try {
				winch.disableControl();
				winch.setX(0);
				break;
			} catch (CANTimeoutException e){
				BlackBoxProtocol.log("CAN-Jaguar communication failed (stopWinch): " + e.toString());
			}
		}
	}
	
	public boolean isWinchProgressDown() {
		return getWinchProgress() >= .98;
	}
	
	public boolean isWinchProgressUp() {
		return getWinchProgress() <= 0.02;
	}

	public boolean isDown() {
		if (winch == null && !initalizeCANJaguar()) {
			BlackBoxProtocol.log("Could not detect reverse limit, CAN is not initialized");
			return false;
		}
		for (int i = 0; i < 3; i++) {
			try {
				return !winch.getReverseLimitOK();
			} catch (CANTimeoutException e){
				BlackBoxProtocol.log("CAN-Jaguar communication failed (isDown): " + e.toString());
			}
		}
		return false;
	}

	public boolean isUp() {
		if (winch == null && !initalizeCANJaguar()) {
			BlackBoxProtocol.log("Could not detect forward limit, CAN is not initialized");
			return false;
		}
		for (int i = 0; i < 3; i++) {
			try {
				return !winch.getForwardLimitOK();
			} catch (CANTimeoutException e){
				BlackBoxProtocol.log("CAN-Jaguar communication failed (isUp): " + e.toString());
			}
		}
		return false;
	}

	public boolean isLoaded() {
		return (loadedSensor.getVoltage() > LOADED_THRESHOLD);
	}
	
	public boolean isLatched() {
		return latch.get();
	}
	
	public double getWinchProgress() {
		double progress = 0;
		if (winchEncoder == null && !initalizeCANJaguar()) {
			return progress;
		}
		if (bottomWinchPosition > topWinchPosition) {
			if (bottomWinchPosition-topWinchPosition == 0)
				return 0;
			progress = 1-(bottomWinchPosition - (double)winchEncoder.getRaw()) / (bottomWinchPosition - topWinchPosition);
		} else {
			if (topWinchPosition-bottomWinchPosition == 0)
				return 0;
			progress = 1-(double)(winchEncoder.getRaw() - bottomWinchPosition) / (topWinchPosition - bottomWinchPosition);
		}
		return progress;
	}

	public void activateLatch() {
		latch.set(false);
	}

	public void deactivateLatch() {
		latch.set(true);
	}
	
	private void updateWinchData() {
		double range = Math.abs(topWinchPosition - bottomWinchPosition);
		controller.setAbsoluteTolerance(range * .01);
		controller.setContinuous(false);
		double p = SmartDashboard.getNumber("P");
		double i = SmartDashboard.getNumber("I");
		double d = SmartDashboard.getNumber("D");
		controller.setPID(p, i, d);
	}
	
	public void resetEncoder() {
		winchEncoder.reset();
		updateWinchData();
	}
	
	public void setCurrentEncoderPositionAsBottom() {
		if (winchEncoder == null && !initalizeCANJaguar()) {
			return;
		}
		bottomWinchPosition = winchEncoder.getRaw();
		updateWinchData();
		BlackBoxProtocol.log("Bottom Winch Position: " + bottomWinchPosition);
	}
	
	public void setCurrentEncoderPositionAsTop() {
		if (winchEncoder == null && !initalizeCANJaguar()) {
			return;
		}
		topWinchPosition = winchEncoder.getRaw();
		updateWinchData();
		BlackBoxProtocol.log("Top Winch Position: " + topWinchPosition);
	}
	
	public void startCompressor() {
		compressor.setRelayValue(Value.kForward);
	}
	
	public void stopCompressor() {
		compressor.setRelayValue(Value.kOff);
	}
	
	public void updateDriverStation() {
		SmartDashboard.putBoolean("Latched", !latch.get());
		SmartDashboard.putNumber("Ball Detector", loadedSensor.getVoltage());
		SmartDashboard.putBoolean("Compressing", compressor.enabled());
		SmartDashboard.putNumber("Winch Position", getWinchProgress()*100);
		if (winchEncoder != null) {
			SmartDashboard.putNumber("Winch Ticks", winchEncoder.getRaw()/TICKS_PER_REVOLUTION);
			SmartDashboard.putNumber("Winch Speed", winchEncoder.getRate()/TICKS_PER_REVOLUTION);
			SmartDashboard.putNumber("PID Error", controller.getError());
			try {
				SmartDashboard.putNumber("Winch Current", winch.getOutputCurrent());
				SmartDashboard.putNumber("Winch Voltage", winch.getOutputVoltage());
			} catch (CANTimeoutException e) {
				e.printStackTrace();
			}
		}
	}
	
	private class WinchSafetyThread implements Runnable {
		private Thread thread = new Thread(this);
		private boolean running = false;
		public void start() {
			if (!running) {
				running = true;
				thread.start();
			}
		}
		public void setRunning(boolean running) {
			this.running = running;
		}
		public boolean isRunning() {
			return running;
		}
		public void run() {
			SmartDashboard.putNumber("Min Winch Current", 50);
			SmartDashboard.putNumber("Max Winch Speed", 1.4);
			StopWinch stopCommand = new StopWinch();
			while (running) {
				if (winchEncoder == null) {
					try { Thread.sleep(100); } catch (InterruptedException e) { }
					continue;
				}
				try {
					double outputCurrent = winch.getOutputCurrent();
					double winchSpeed = Math.abs(winchEncoder.getRate()/TICKS_PER_REVOLUTION);
					double minWinchCurrent = SmartDashboard.getNumber("Min Winch Current");
					double maxWinchSpeed = SmartDashboard.getNumber("Max Winch Speed");
					if (outputCurrent >= minWinchCurrent && winchSpeed <= maxWinchSpeed) {
						stopWinch();
						stopCommand.start();
					}
				} catch (CANTimeoutException ex) {
					
				}
				try { Thread.sleep(30); } catch (InterruptedException e) { }
			}
		}
	}
	
}
