package org.aesgard.caninoengine.util;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.HashMap;
import java.util.Map;

public final class BufferUtils {
	
	private static Map<Integer, FloatBuffer> floatBuffers = new HashMap<Integer, FloatBuffer>();
	private static Map<Integer, ByteBuffer> byteBuffers = new HashMap<Integer, ByteBuffer>();
	private static Map<Integer, ShortBuffer> shortBuffers = new HashMap<Integer, ShortBuffer>();
	private static Map<Integer, IntBuffer> intBuffers = new HashMap<Integer, IntBuffer>();
	
	private BufferUtils() {}

	public final static FloatBuffer array2FloatBuffer(float[] array) {
		if (array == null)
			return null;
		FloatBuffer b = null;
		int size = array.length;
		if (floatBuffers.containsKey(size)) {
			b = floatBuffers.get(size);
		} else {
			b = org.lwjgl.BufferUtils.createFloatBuffer(size);
		}
	    b.put(array);
	    b.flip();
	    return b;
	}
	
	public final static ByteBuffer array2ByteBuffer(byte[] array) {
		if (array == null)
			return null;
		ByteBuffer b = null;
		int size = array.length;
		if (byteBuffers.containsKey(size)) {
			b = byteBuffers.get(size);
		} else {
			b = org.lwjgl.BufferUtils.createByteBuffer(size);
		}
	    b.put(array);
	    b.flip();
	    return b;
	}
	
	public final static ShortBuffer array2ShortBuffer(short[] array) {
		if (array == null)
			return null;
		ShortBuffer b = null;
		int size = array.length;
		if (shortBuffers.containsKey(size)) {
			b = shortBuffers.get(size);
		} else {
			b = org.lwjgl.BufferUtils.createShortBuffer(size);
		}
	    b.put(array);
	    b.flip();
	    return b;
	}
	
	public final static ByteBuffer array2ByteBuffer(byte[][] array) {
		if (array == null || (array != null && array.length==0))
			return null;
		ByteBuffer b = null;
		int size = array.length * array[0].length;
		if (byteBuffers.containsKey(size)) {
			b = byteBuffers.get(size);
		} else {
			b = org.lwjgl.BufferUtils.createByteBuffer(size);
		}
		for (int i = 0; i < array.length; i++) {
		    b.put(array[i]);
		}
	    b.flip();
	    return b;
	}

	public final static IntBuffer array2IntBuffer(int[] array) {
		if (array == null)
			return null;
		IntBuffer b = null;
		int size = array.length;
		if (intBuffers.containsKey(size)) {
			b = intBuffers.get(size);
		} else {
			b = org.lwjgl.BufferUtils.createIntBuffer(size);
		}
	    b.put(array);
	    b.flip();
	    return b;
	}
	
	public final static FloatBuffer array2FloatBuffer(float array[][][])
	{
		if (array == null || (array != null && array.length==0))
			return null;
		FloatBuffer b = null;
		int size = array.length * array[0].length * array[0][0].length;
		if (floatBuffers.containsKey(size)) {
			b = floatBuffers.get(size);
		} else {
			b = org.lwjgl.BufferUtils.createFloatBuffer(size);
		}
		for(int i = 0; i < array.length; i++)
			for(int j = 0; j < array[0].length; j++)
				for(int k = 0; k < array[0][0].length; k++)
					b.put(array[i][j][k]);
	    b.flip();
	    return b;
	}
	
	public final static FloatBuffer array2FloatBuffer(float array[][])
	{
		if (array == null || (array != null && array.length==0))
			return null;
		FloatBuffer b = null;
		int size = array.length * array[0].length;
		if (floatBuffers.containsKey(size)) {
			b = floatBuffers.get(size);
		} else {
			b = org.lwjgl.BufferUtils.createFloatBuffer(size);
		}
		for(int i = 0; i < array.length; i++)
			for(int j = 0; j < array[0].length; j++)
	    b.flip();
	    return b;
	}
}
