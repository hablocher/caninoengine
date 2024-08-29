package org.aesgard.caninoengine.glutil;

public final class FrameMath {
	private FrameMath() {}
	
	/////////////////////////////////////////////////////////
	// March a frame of reference forward. This simply moves
	// the location forward along the forward vector.
	public static void gltMoveFrameForward(GLTFrame pFrame, float fStep)
	{
	    pFrame.vLocation[0] += pFrame.vForward[0] * fStep;
	    pFrame.vLocation[1] += pFrame.vForward[1] * fStep;
	    pFrame.vLocation[2] += pFrame.vForward[2] * fStep;
	}

}
