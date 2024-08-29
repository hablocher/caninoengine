package org.aesgard.caninoengine.util;



public final class Constants {
	
	private Constants() {};
	
	public static double GLT_PI = 3.14159265358979323846;
	public static double GLT_PI_DIV_180 = 0.017453292519943296;
	public static double GLT_INV_PI_DIV_180 = 57.2957795130823229;
	
	public static float gltDegToRad(float x) {
		return ((x)*(float)GLT_PI_DIV_180);
	}
	
	public static byte[] int2byte(int[]src) {
	    int srcLength = src.length;
	    byte[]dst = new byte[srcLength << 2];
	    
	    for (int i=0; i<srcLength; i++) {
	        int x = src[i];
	        int j = i << 2;
	        dst[j++] = (byte) ((x >>> 0) & 0xff);           
	        dst[j++] = (byte) ((x >>> 8) & 0xff);
	        dst[j++] = (byte) ((x >>> 16) & 0xff);
	        dst[j++] = (byte) ((x >>> 24) & 0xff);
	    }
	    return dst;
	}
}
