package com.team2502;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.MotorSafety;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
import edu.wpi.first.wpilibj.tables.ITable;

/**
 *
 * @author Josh Larson
 */
public class CANJaguarSafety implements MotorSafety, PIDOutput, SpeedController, LiveWindowSendable {
	
	private boolean connected;
	private int canPort;
	private CANJaguarThread jaguarThread;
	private CANJaguar jaguar;
	
	public CANJaguarSafety(int canPort) {
		this.canPort = canPort;
		connected = false;
		jaguarThread = new CANJaguarThread();
		jaguar = null;
	}
	
	public void shutdown() {
		jaguarThread.shutdown();
	}
	
	public boolean isConnected() {
		return connected;
	}
	
	public void configEncoderCodesPerRev(int ticksPerRev) {
		if (!connected)
			return;
		for (int i = 0; i < 3; i++) {
			try { jaguar.configEncoderCodesPerRev(ticksPerRev); connected = true; break; }
			catch (CANTimeoutException ex) { connected = false; }
		}
	}
	
	public void setPositionReference(CANJaguar.PositionReference ref) {
		if (!connected)
			return;
		for (int i = 0; i < 3; i++) {
			try { jaguar.setPositionReference(ref); connected = true; break; }
			catch (CANTimeoutException ex) { connected = false; }
		}
	}
	
	public void changeControlMode(CANJaguar.ControlMode mode) {
		if (!connected)
			return;
		for (int i = 0; i < 3; i++) {
			try { jaguar.changeControlMode(mode); connected = true; break; }
			catch (CANTimeoutException ex) { connected = false; }
		}
	}
	
	public void disableControl() {
		if (!connected)
			return;
		for (int i = 0; i < 3; i++) {
			try { jaguar.disableControl(); connected = true; break; }
			catch (CANTimeoutException ex) { connected = false; }
		}
	}
	
	public void enableControl() {
		if (!connected)
			return;
		for (int i = 0; i < 3; i++) {
			try { jaguar.enableControl(); connected = true; break; }
			catch (CANTimeoutException ex) { connected = false; }
		}
	}
	
	public void setExpiration(double d) {
		if (!connected)
			return;
		jaguar.setExpiration(d);
	}

	public double getExpiration() {
		if (!connected)
			return -1;
		return jaguar.getExpiration();
	}

	public boolean isAlive() {
		if (!connected)
			return false;
		return jaguar.isAlive();
	}

	public void stopMotor() {
		if (!connected)
			return;
		jaguar.stopMotor();
	}

	public void setSafetyEnabled(boolean bln) {
		if (!connected)
			return;
		jaguar.setSafetyEnabled(bln);
	}

	public boolean isSafetyEnabled() {
		if (!connected)
			return false;
		return jaguar.isSafetyEnabled();
	}

	public String getDescription() {
		if (!connected)
			return "";
		return jaguar.getDescription();
	}

	public void pidWrite(double d) {
		if (!connected)
			return;
		jaguar.pidWrite(d);
	}

	public double get() {
		if (!connected)
			return 0;
		return jaguar.get();
	}

	public void set(double d, byte b) {
		if (!connected)
			return;
		jaguar.set(d, b);
	}

	public void set(double d) {
		if (!connected)
			return;
		jaguar.set(d);
	}

	public void disable() {
		if (!connected)
			return;
		jaguar.disable();
	}

	public void updateTable() {
		if (!connected)
			return;
		jaguar.updateTable();
	}

	public void startLiveWindowMode() {
		if (!connected)
			return;
		jaguar.startLiveWindowMode();
	}

	public void stopLiveWindowMode() {
		if (!connected)
			return;
		jaguar.stopLiveWindowMode();
	}

	public void initTable(ITable itable) {
		if (!connected)
			return;
		jaguar.initTable(itable);
	}

	public ITable getTable() {
		if (!connected)
			return null;
		return jaguar.getTable();
	}

	public String getSmartDashboardType() {
		if (!connected)
			return "";
		return jaguar.getSmartDashboardType();
	}
	
	public void setX(double x) {
		if (!connected)
			return;
		for (int i = 0; i < 3; i++) {
			try { jaguar.setX(x); connected = true; break; }
			catch (CANTimeoutException ex) { connected = false; }
		}
	}
	
	public boolean getForwardLimitOK() {
		if (!connected)
			return false;
		for (int i = 0; i < 3; i++) {
			try { return jaguar.getForwardLimitOK(); }
			catch (CANTimeoutException ex) { }
		}
		connected = false;
		return false;
	}
	
	public boolean getReverseLimitOK() {
		if (!connected)
			return false;
		for (int i = 0; i < 3; i++) {
			try { return jaguar.getReverseLimitOK(); }
			catch (CANTimeoutException ex) { }
		}
		connected = false;
		return false;
	}
	
	public double getOutputCurrent() {
		if (!connected)
			return 0;
		for (int i = 0; i < 3; i++) {
			try { return jaguar.getOutputCurrent(); }
			catch (CANTimeoutException ex) { }
		}
		connected = false;
		return 0;
	}
	
	public double getOutputVoltage() {
		if (!connected)
			return 0;
		for (int i = 0; i < 3; i++) {
			try { return jaguar.getOutputVoltage(); }
			catch (CANTimeoutException ex) { }
		}
		connected = false;
		return 0;
	}
	
	private class CANJaguarThread implements Runnable {
		
		private Thread myThread;
		private boolean running = true;
		
		public CANJaguarThread() {
			myThread = new Thread(this);
			running = true;
			myThread.start();
		}
		
		public void shutdown() {
			running = false;
			myThread.interrupt();
		}
		
		public void run() {
			while (running) {
				if (jaguar == null) {
					try {
						jaguar = new CANJaguar(canPort);
						connected = true;
					} catch (CANTimeoutException ex) {
						jaguar = null;
						connected = false;
					}
				} else {
					try {
						int hardware = jaguar.getHardwareVersion(); // 1=Jaguar  2=Black Jaguar
						connected = true;
					} catch (CANTimeoutException ex) {
						connected = false;
					}
				}
				try { Thread.sleep(100); } catch (InterruptedException e) { }
			}
		}
	}
	
}
