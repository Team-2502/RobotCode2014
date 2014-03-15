package com.team2502.subsystems;

import com.team2502.ByteBuffer;
import com.team2502.RaspberryPi;
import com.team2502.RaspberryPi.PiCallback;
import com.team2502.RaspberryPi.Target;
import com.team2502.Robot;
import com.team2502.RobotMap;
import com.team2502.black_box.BlackBoxProtocol;
import com.team2502.commands.vision.VisionUpdater;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException;
import java.util.Vector;

/**
 *
 * @author Josh Larson
 */
public class VisionSubsystem extends Subsystem implements PiCallback {
	
	private Vector messages;
	private Solenoid ringLight;
	private RaspberryPi pi;
	private int targetsFound;
	private boolean leftHot;
	private boolean rightHot;
	private boolean started;
	
	public VisionSubsystem() {
		ringLight = new Solenoid(RobotMap.VISION_RING_LIGHT);
		messages = new Vector();
		pi = new RaspberryPi("10.25.2.30", this);
		targetsFound = 0;
		leftHot = false;
		rightHot = false;
		started = false;
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new VisionUpdater());
	}
	
	public boolean isStarted() {
		return started;
	}
	
	public boolean isConnected() {
		return pi.isConnected();
	}
	
	public void start() {
		if (isStarted())
			return;
		started = true;
		BlackBoxProtocol.log("Starting Vision System");
		pi.start();
		pi.setCompetitionMode(true);
	}
	
	private double distance(Target a, Target b) {
		double xSquare = (a.normX-b.normX)*(a.normX-b.normX);
		double ySquare = (a.normY-b.normY)*(a.normY-b.normY);
		return Math.sqrt(xSquare + ySquare);
	}
	
	public void onReceiveTargetList(Target [] list) {
		targetsFound = list.length;
		String stationInfo = "";
		boolean leftHot = false;
		boolean rightHot = false;
		for (int i = 0; i < list.length; i++) {
			Target a = list[i];
			BlackBoxProtocol.log("Target #" + i + ": Angle: " + a.angle);
			stationInfo += "#" + i + ": ("+a.normX + ","+a.normY + ") "+a.angle;
			if (i+1 < list.length)
				stationInfo += ", ";
			for (int j = 0; j < list.length; j++) {
				if (j != i)
					continue;
				Target b = list[j];
				double angDiff = Math.abs(a.angle - b.angle);
				double dist = distance(a, b);
				if (angDiff >= 60 && dist >= .15) {
					if (b.normX > a.normX && a.normY > b.normY) {
						leftHot = true;
						break;
					} else if (b.normX < a.normX && a.normY > b.normY) {
						rightHot = true;
						break;
					}
				}
			}
		}
		SmartDashboard.putString("PI Debug", "L:"+leftHot+" R: "+rightHot+" "+stationInfo);
	}
	
	public void activateRingLight() {
		setRingLight(true);
	}
	
	public void deactivateRingLight() {
		setRingLight(false);
	}
	
	public void setRingLight(boolean on) {
		ringLight.set(on);
	}
	
	public void addMessage(String message) {
		messages.addElement(message);
	}
	
	public void updateRaspberryPi() {
		if (!pi.isConnected())
			return;
		// Request Target Data
		pi.requestTargetList();
		pi.setCompetitionMode(Robot.isCompetitionMode());
		pi.setGameRecording(Robot.isCompetitionMode());
		if (Robot.isCompetitionMode()) {
			pi.sendMatchData(createMatchData());
		}
		try {
			int brightness = (int)SmartDashboard.getNumber("PI Brightness");
			pi.setBrightness(brightness);
		} catch (TableKeyNotDefinedException e) {
			SmartDashboard.putNumber("PI Brightness", pi.getBrightness());
		}
		try {
			String threshold = SmartDashboard.getString("PI Threshold");
			String rangeMin = "";
			String rangeMax = "";
			int maxIndex = 0;
			for (int i = 0; i < threshold.length() && threshold.charAt(i) != ' '; i++, maxIndex++) {
				rangeMin += threshold.charAt(i);
			}
			maxIndex++;
			while(maxIndex < threshold.length() && !Character.isDigit(threshold.charAt(maxIndex)))
				maxIndex++;
			for (int i = maxIndex; i < threshold.length() && threshold.charAt(i) != ' '; i++) {
				rangeMax += threshold.charAt(i);
			}
			rangeMin = rangeMin.trim();
			rangeMax = rangeMax.trim();
			pi.setThreshold(Integer.parseInt(rangeMin), Integer.parseInt(rangeMax));
		} catch (TableKeyNotDefinedException e) {
			SmartDashboard.putString("PI Threshold", pi.getRange().getLower() + " " + pi.getRange().getUpper());
		}
		try {
			String size = SmartDashboard.getString("PI Size");
			String width = "";
			String height = "";
			int heightIndex = 0;
			for (int i = 0; i < size.length() && size.charAt(i) != ' '; i++, heightIndex++) {
				width += size.charAt(i);
			}
			heightIndex++;
			while(heightIndex < size.length() && !Character.isDigit(size.charAt(heightIndex)))
				heightIndex++;
			for (int i = heightIndex; i < size.length() && size.charAt(i) != ' '; i++) {
				height += size.charAt(i);
			}
			width = width.trim();
			height = height.trim();
			pi.setSize(Integer.parseInt(width), Integer.parseInt(height));
		} catch (TableKeyNotDefinedException e) {
			SmartDashboard.putString("PI Size", pi.getWidth() + " " + pi.getHeight());
		}
	}
	
	public void updateDriverStation() {
		SmartDashboard.putBoolean("Connected to PI", pi.isConnected());
	}
	
	private ByteBuffer createMatchData() {
		DriverStation ds = DriverStation.getInstance();
		ByteBuffer data = ByteBuffer.create();
		data.putFloat((float)ds.getMatchTime()); // Match Time
		data.putFloat((float)ds.getBatteryVoltage()); // Battery Voltage
		data.put((byte)ds.getLocation()); // Driver Station #
		data.put((byte)getAlliance(ds)); // 0=Unk 1=Red 2=Blue
		data.put((byte)(ds.isEnabled() ? 1 : 0)); // 0=Disabled 1=Enabled
		data.put((byte)(ds.isFMSAttached() ? 1 : 0)); // 0=NoFMS 1=FMS
		data.put((byte)getRobotMode(ds)); // 0=Unk 1=Auto 2=Tele 3=Test
		data.put((byte)messages.size()); // Message count
		while (messages.size() > 0) {
			String message = (String)messages.elementAt(0);
			messages.removeElementAt(0);
			data.put(message.getBytes());
			data.put((byte)0);
		}
		return data;
	}
	
	private int getAlliance(DriverStation ds) {
		int val = ds.getAlliance().value;
		if (val == Alliance.kRed_val)
			return 1;
		if (val == Alliance.kBlue_val)
			return 2;
		return 0;
	}
	
	private int getRobotMode(DriverStation ds) {
		if (ds.isAutonomous())
			return 1;
		if (ds.isOperatorControl())
			return 2;
		if (ds.isTest())
			return 3;
		return 0;
	}
	
}
