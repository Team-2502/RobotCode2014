package com.team2502;

import com.team2502.black_box.BlackBoxProtocol;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;
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
	private SocketCallback callback;
	private Vector operations;
	private Thread thread;
	private SocketConnection comm;
	private InputStream inputStream;
	private OutputStream outputStream;
	private String address;
	private boolean initialized;
	private boolean running;
	
	public Socket(String ip, int port) {
		address = "socket://" + ip + ":" + port;
		operations = new Vector();
		thread = new Thread(this);
		initialized = false;
		running = true;
	}
	
	public void setCallback(SocketCallback callback) {
		this.callback = callback;
	}
	
	private boolean connectToSocket() {
		if (initialized)
			return true;
		try {
			comm = (SocketConnection)Connector.open(address, Connector.READ_WRITE);
			inputStream = comm.openInputStream();
			outputStream = comm.openOutputStream();
			initialized = true;
			BlackBoxProtocol.log("Successfully connected to " + address);
		} catch (IOException ex) {
			BlackBoxProtocol.log("Failed to connect to " + address);
			initialized = false;
		}
		return initialized;
	}
	
	public void run() {
		while (running) {
			if (!initialized) {
				if (connectToSocket())
					callback.onConnected();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {

				}
			} else {
				while (operations.size() > 0) {
					SocketOperation op = (SocketOperation)operations.elementAt(0);
					if (op.isRead()) {
						byte [] data = read(op.readData);
						if (data == null) {
							initialized = false;
							callback.onDisconnected();
							break;
						}
						callback.onRead(op.operationID, data);
					} else {
						boolean success = write(op.writeData);
						if (!success) {
							initialized = false;
							callback.onDisconnected();
							break;
						}
						callback.onWrite(op.operationID, success);
					}
					operations.removeElementAt(0);
				}
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {

				}
			}
		}
	}
	
	public void start() {
		running = true;
		if (!thread.isAlive())
			thread.start();
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
	
	public boolean isStarted() {
		return running;
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
	
	private byte [] read(byte [] data) {
		if (!initialized)
			return null;
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
	
	private ByteBuffer readNextBundle() {
		if (!initialized)
			return null;
		ByteBuffer bb = ByteBuffer.create();
		byte [] tmpData = new byte[1024];
		while ((tmpData = read(tmpData)) != null) {
			bb.put(tmpData);
		}
		return bb;
	}
	
	private boolean write(byte [] data) {
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
	
	public void read(int opID, int bufferSize) {
		operations.addElement(new SocketOperation(opID, bufferSize));
	}
	
	public void write(int opID, byte [] data) {
		operations.addElement(new SocketOperation(opID, data));
	}
	
	public interface SocketCallback {
		public void onConnected();
		public void onDisconnected();
		public void onRead(int opID, byte [] read);
		public void onWrite(int opID, boolean success);
	}
	
	private class SocketOperation {
		
		private int operationID;
		private byte [] readData;
		private byte [] writeData;
		
		public SocketOperation(int opID) {
			this.operationID = opID;
			readData = null;
			writeData = null;
		}
		
		public SocketOperation(int opID, int readSize) {
			this.operationID = opID;
			readData = new byte[readSize];
			writeData = null;
		}
		
		public SocketOperation(int opID, byte [] writeData) {
			this.operationID = opID;
			readData = null;
			this.writeData = writeData;
		}
		
		public boolean isRead() {
			return readData != null;
		}
		
		public boolean isWrite() {
			return writeData != null;
		}
		
	}
	
}
