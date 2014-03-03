package com.team2502;

import com.team2502.black_box.BlackBoxProtocol;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.SocketConnection;

/**
 *
 * @author Josh Larson
 */
public class Socket implements Runnable {
	
	public static final int READ_WRITE = Connector.READ_WRITE;
	public static final int READ = Connector.READ;
	public static final int WRITE = Connector.WRITE;
	private Thread thread;
	private SocketConnection comm;
	private InputStream inputStream;
	private OutputStream outputStream;
	private String address;
	private boolean initialized;
	
	public Socket(String ip, int port) {
		address = "socket://" + ip + ":" + port;
		initialized = false;
		thread = new Thread(this);
		thread.start();
	}
	
	private boolean connectToSocket() {
		if (initialized)
			return true;
		try {
			comm = (SocketConnection)Connector.open(address, Connector.READ_WRITE);
			inputStream = comm.openInputStream();
			outputStream = comm.openOutputStream();
			initialized = true;
		} catch (IOException ex) {
			BlackBoxProtocol.log("Failed to connect to " + address);
			initialized = false;
		}
		return initialized;
	}
	
	public void run() {
		while (!connectToSocket()) {
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
			}
		}
	}
	
	public void close() {
		outputStream = null;
		inputStream = null;
		try {
			comm.close();
		} catch (IOException ex) {
		}
	}
	
	public boolean isConnected() {
		return initialized;
	}
	
	/*
	public boolean skip(int bytes) {
		try {
			if (inputStream != null)
				inputStream.skip(bytes);
			else
				return false;
			return true;
		} catch (IOException ex) {
			return false;
		}
	}
	
	public int available() {
		try {
			if (inputStream != null)
				return inputStream.available();
			return -1;
		} catch (IOException ex) {
			return -1;
		}
	}
	
	public boolean flush() {
		try {
			if (outputStream != null)
				outputStream.flush();
			else
				outputStream = null;
			return true;
		} catch (IOException ex) {
			return false;
		}
	}
	*/
	
	public byte [] read(int bufferSize) {
		if (!initialized)
			return null;
		byte [] data = new byte[bufferSize];
		int readSize;
		try {
			if (inputStream != null)
				readSize = inputStream.read(data);
			else
				readSize = -1;
		} catch (IOException ex) {
			return null;
		}
		if (readSize == -1)
			return null;
		byte [] smallerData = new byte[readSize];
		System.arraycopy(data, 0, smallerData, 0, readSize);
		return smallerData;
	}
	
	public ByteBuffer readNextBundle() {
		if (!initialized)
			return null;
		ByteBuffer bb = ByteBuffer.create();
		byte [] tmpData;
		while ((tmpData = read(1024)) != null) {
			bb.put(tmpData);
		}
		return bb;
	}
	
	public boolean write(byte [] data) {
		if (!initialized)
			return false;
		try {
			if (outputStream != null)
				outputStream.write(data);
			else
				return false;
			return true;
		} catch (IOException ex) {
			BlackBoxProtocol.log("Failed to write to stream of length " + data.length);
			return false;
		}
	}
	
}
