package org.aesgard.caninoengine.texture;

import java.nio.ByteBuffer;

public class Texture {

	private byte[] buffer;
	private ByteBuffer bb;
	private int width;
	private int height;
	
	public Texture(byte[] buffer, int width, int height) {
		this.buffer = buffer;
		this.width  = width;
		this.height = height;
	}
	
	public Texture(ByteBuffer buffer, int width, int height) {
		this.bb = buffer;
		this.width  = width;
		this.height = height;
	}
	
	public byte[] getBuffer() {
		return buffer;
	}

	public ByteBuffer getByteBuffer() {
	    return bb;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public void close() {
		buffer = null;
		if (bb != null) 
			bb.clear();
		bb = null;
		width = 0;
		height = 0;
	}
}
