package com.team2502;

/**
 *
 * @author Josh Larson
 */
public class ByteBuffer {
	
	private ByteOrder order;
	private boolean constantSize;
	private byte [] data;
	private int length;
	private int index;
	
	private ByteBuffer(int size, ByteOrder order, boolean constantSize) {
		this.length = size;
		this.index = 0;
		this.order = order;
		this.constantSize = constantSize;
		data = new byte[size];
	}
	
	public static ByteBuffer allocate(int size) {
		return allocate(size, ByteOrder.BIG_ENDIAN);
	}
	
	public static ByteBuffer allocate(int size, ByteOrder order) {
		return new ByteBuffer(size, order, true);
	}
	
	public static ByteBuffer wrap(byte [] data) {
		return wrap(data, ByteOrder.BIG_ENDIAN);
	}
	
	public static ByteBuffer wrap(byte [] data, ByteOrder order) {
		ByteBuffer bb = new ByteBuffer(data.length, order, true);
		System.arraycopy(data, 0, bb.data, 0, data.length);
		return bb;
	}
	
	public static ByteBuffer create() {
		return create(ByteOrder.BIG_ENDIAN);
	}
	
	public static ByteBuffer create(ByteOrder order) {
		return new ByteBuffer(0, order, false);
	}
	
	public void put(byte b) {
		resizeIfNeeded(1);
		put(b, index);
		index++;
	}
	
	public void put(byte b, int index) {
		if (index < 0 || index >= length)
			throw new ArrayIndexOutOfBoundsException();
		data[index] = b;
	}
	
	public void putShort(short s) {
		resizeIfNeeded(2);
		putShort(s, index);
		index += 2;
	}
	
	public void putShort(short s, int index) {
		if (index < 0 || index+1 >= length)
			throw new ArrayIndexOutOfBoundsException();
		s = order.modShort(s);
		data[index] = (byte)(s & 0xff);
		data[index+1] = (byte)((s>>8) & 0xff);
	}
	
	public void putInt(int i) {
		resizeIfNeeded(4);
		putInt(i, index);
		index += 4;
	}
	
	public void putInt(int i, int index) {
		if (index < 0 || index+3 >= length)
			throw new ArrayIndexOutOfBoundsException();
		i = order.modInt(i);
		data[index] = (byte)(i & 0xff);
		data[index+1] = (byte)((i>>8) & 0xff);
		data[index+2] = (byte)((i>>16) & 0xff);
		data[index+3] = (byte)((i>>24) & 0xff);
	}
	
	public void putFloat(float f) {
		resizeIfNeeded(4);
		putFloat(f, index);
		index += 4;
	}
	
	public void putFloat(float f, int index) {
		if (index < 0 || index+3 >= length)
			throw new ArrayIndexOutOfBoundsException();
		int i = order.modInt(Float.floatToIntBits(f));
		data[index+0] = (byte)((i>>0) & 0xff);
		data[index+1] = (byte)((i>>8) & 0xff);
		data[index+2] = (byte)((i>>16) & 0xff);
		data[index+3] = (byte)((i>>24) & 0xff);
	}
	
	public void putLong(long l) {
		resizeIfNeeded(8);
		putLong(l, index);
		index += 8;
	}
	
	public void putLong(long l, int index) {
		if (index < 0 || index+7 >= length)
			throw new ArrayIndexOutOfBoundsException();
		l = order.modLong(l);
		data[index+7] = (byte)((l>>0) & 0xff);
		data[index+6] = (byte)((l>>8) & 0xff);
		data[index+5] = (byte)((l>>16) & 0xff);
		data[index+4] = (byte)((l>>24) & 0xff);
		data[index+3] = (byte)((l>>32) & 0xff);
		data[index+2] = (byte)((l>>40) & 0xff);
		data[index+1] = (byte)((l>>48) & 0xff);
		data[index+0] = (byte)((l>>56) & 0xff);
	}
	
	public void putDouble(double d) {
		resizeIfNeeded(8);
		putDouble(d, index);
		index += 8;
	}
	
	public void putDouble(double d, int index) {
		if (index < 0 || index+7 >= length)
			throw new ArrayIndexOutOfBoundsException();
		long l = Double.doubleToLongBits(order.modDouble(d));
		data[index+0] = (byte)((l>>0) & 0xff);
		data[index+1] = (byte)((l>>8) & 0xff);
		data[index+2] = (byte)((l>>16) & 0xff);
		data[index+3] = (byte)((l>>24) & 0xff);
		data[index+4] = (byte)((l>>32) & 0xff);
		data[index+5] = (byte)((l>>40) & 0xff);
		data[index+6] = (byte)((l>>48) & 0xff);
		data[index+7] = (byte)((l>>56) & 0xff);
	}
	
	public void put(byte [] data) {
		put(data, 0, data.length);
	}
	
	public void put(byte [] data, int offset, int length) {
		resizeIfNeeded(length);
		for (int i = 0; i < length; i++) {
			this.data[index++] = data[offset+i];
		}
	}
	
	public void flip() {
		byte [] nData = new byte[length];
		System.arraycopy(data, 0, nData, 0, length);
		data = nData;
		length = index;
		index = 0;
	}
	
	public byte get() {
		resizeIfNeeded(1);
		byte b = get(index);
		index++;
		return b;
	}
	
	public byte get(int index) {
		if (index < 0 || index >= length)
			throw new ArrayIndexOutOfBoundsException();
		return data[index];
	}
	
	public short getShort() {
		resizeIfNeeded(2);
		short s = getShort(index);
		index += 2;
		return s;
	}
	
	public short getShort(int index) {
		if (index < 0 || index+1 >= length)
			throw new ArrayIndexOutOfBoundsException();
		short s = (short) ((short)data[index] + ((short)data[index+1])<<8);
		return order.modShort(s);
	}
	
	public int getInt() {
		resizeIfNeeded(4);
		int i = getInt(index);
		index += 4;
		return i;
	}
	
	public int getInt(int index) {
		if (index < 0 || index+3 >= length)
			throw new ArrayIndexOutOfBoundsException();
		int i = 0;
		i += ((int)data[index+3])<<0;
		i += ((int)data[index+2])<<8;
		i += ((int)data[index+1])<<16;
		i += ((int)data[index+0])<<24;
		return order.modInt(i);
	}
	
	public float getFloat() {
		resizeIfNeeded(4);
		float f = getFloat(index);
		index += 4;
		return f;
	}
	
	public float getFloat(int index) {
		if (index < 0 || index+3 >= length)
			throw new ArrayIndexOutOfBoundsException();
		int i = 0;
		for (int ind = 0; ind < 4; ind++)
			i += ((int)data[index+ind]&0xFF) << (ind*8);
		return Float.intBitsToFloat(order.modInt(i));
	}
	
	public long getLong() {
		resizeIfNeeded(8);
		long l = getLong(index);
		index += 8;
		return l;
	}
	
	public long getLong(int index) {
		if (index < 0 || index+7 >= length)
			throw new ArrayIndexOutOfBoundsException();
		long l = 0;
		l += ((long)data[index+7])<<0;
		l += ((long)data[index+6])<<8;
		l += ((long)data[index+5])<<16;
		l += ((long)data[index+4])<<24;
		l += ((long)data[index+3])<<32;
		l += ((long)data[index+2])<<40;
		l += ((long)data[index+1])<<48;
		l += ((long)data[index+0])<<56;
		return order.modLong(l);
	}
	
	public double getDouble() {
		resizeIfNeeded(8);
		double d = getDouble(index);
		index += 8;
		return d;
	}
	
	public double getDouble(int index) {
		if (index < 0 || index+7 >= length)
			throw new ArrayIndexOutOfBoundsException();
		long l = 0;
		l += ((long)data[index+0])<<0;
		l += ((long)data[index+1])<<8;
		l += ((long)data[index+2])<<16;
		l += ((long)data[index+3])<<24;
		l += ((long)data[index+4])<<32;
		l += ((long)data[index+5])<<40;
		l += ((long)data[index+6])<<48;
		l += ((long)data[index+7])<<56;
		return Double.longBitsToDouble(order.modLong(l));
	}
	
	public void get(byte [] data) {
		get(data, 0, data.length);
	}
	
	public void get(byte [] data, int offset, int length) {
		if (index+length > this.length)
			throw new ArrayIndexOutOfBoundsException();
		for (int i = 0; i < length; i++) {
			data[offset+i] = this.data[index+i];
		}
		index += length;
	}
	
	public byte [] array() {
		byte [] d = new byte[length];
		System.arraycopy(data, 0, d, 0, length);
		return d;
	}
	
	public void position(int newPosition) {
		index = newPosition;
	}
	
	public int position() {
		return index;
	}
	
	public int length() {
		return length;
	}
	
	public boolean hasRemaining() {
		return index < length;
	}
	
	public int remaining() {
		return length - index;
	}
	
	public ByteBuffer order(ByteOrder order) {
		this.order = order;
		return this;
	}
	
	private void resizeIfNeeded(int appendSize) {
		if (index+appendSize < length || constantSize)
			return;
		length += appendSize;
		byte [] nData = new byte[length];
		System.arraycopy(data, 0, nData, 0, data.length);
		data = nData;
	}
	
	public static class ByteOrder {
		public static final ByteOrder BIG_ENDIAN = new ByteOrder(true);
		public static final ByteOrder LITTLE_ENDIAN = new ByteOrder(false);
		private boolean bigEndian;
		
		private ByteOrder(boolean bigEndian) {
			this.bigEndian = bigEndian;
		}
		
		private short modShort(short s) {
			if (bigEndian)
				return s;
			return (short)((short)(((s>>8)&0xff)) + (short)((s&0xff)<<8));
		}
		
		private int modInt(int i) {
			if (bigEndian)
				return i;
			return ((i&0xff)<<24)+((i&0xff00)<<8)+((i&0xff0000)>>8)+((i>>24)&0xff);
		}
		
		private float modFloat(float f) {
			if (bigEndian)
				return f;
			return Float.intBitsToFloat(modInt(Float.floatToIntBits(f)));
		}
		
		private long modLong(long l) {
			if (bigEndian)
				return l;
			long ret = 0;
			for (int i = 0; i < 8; i++) {
				ret += ((l>>(i*8))&0xFF)<<((7-i)*8);
			}
			return ret;
		}
		
		private double modDouble(double d) {
			if (bigEndian)
				return d;
			return Double.longBitsToDouble(modLong(Double.doubleToLongBits(d)));
		}
	}
}
