package org.aesgard.caninoengine.glutil;

import static org.aesgard.caninoengine.glutil.MatrixMath.gltRotationMatrix;
import static org.aesgard.caninoengine.glutil.VectorMath.gltRotateVector;
import static org.aesgard.caninoengine.glutil.VectorMath.gltVectorCrossProduct;
import static org.aesgard.caninoengine.util.BufferUtils.array2FloatBuffer;
import static org.aesgard.caninoengine.util.Constants.gltDegToRad;
import static org.lwjgl.opengl.GL11.glMultMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

/*
 
   0 1 2 3
 0 x x x x 
 1 x x x x
 2 x x x x
 3 x x x x
 
 00 01 02 03 04 05 06 07 08 09 10 11 12 13 14 15
 x  x  x  x  x  x  x  x  x  x  x  x  x  x  x  x
 
x vector --> 00 04 08 12 
y vector --> 01 05 09 13
z vector --> 02 06 10 14
t vector --> 03 07 11 15
 
 */

public class GLTFrame {
	public float[] vLocation = new float[3];
	public float[] vUp       = new float[3];
	public float[] vForward  = new float[3];
	
	public void gltInitFrame()
    {
		vLocation[0] = 0.0f;
		vLocation[1] = 0.0f;
		vLocation[2] = 0.0f;
    
		vUp[0] = 0.0f;
		vUp[1] = 1.0f;
		vUp[2] = 0.0f;
    
		vForward[0] = 0.0f;
		vForward[1] = 0.0f;
		vForward[2] = -1.0f;
    }
	
	//////////////////////////////////////////////////////////////////
	// Apply a camera transform given a frame of reference. This is
	// pretty much just an alternate implementation of gluLookAt using
	// floats instead of doubles and having the forward vector specified
	// instead of a point out in front of me. 
	public void gltApplyCameraTransform()
	{
	    float[] mMatrix  = new float[16];
	    float[] vAxisX   = new float[3];
	    float[] zFlipped = new float[3];
	    
	    zFlipped[0] = -vForward[0];
	    zFlipped[1] = -vForward[1];
	    zFlipped[2] = -vForward[2];
	    
	    // Derive X vector
	    gltVectorCrossProduct(vUp, zFlipped, vAxisX);
	    
	    // Populate matrix, note this is just the rotation and is transposed
	    mMatrix[0] = vAxisX[0];
	    mMatrix[4] = vAxisX[1];
	    mMatrix[8] = vAxisX[2];
	    mMatrix[12] = 0.0f;
	    
	    mMatrix[1] = vUp[0];
	    mMatrix[5] = vUp[1];
	    mMatrix[9] = vUp[2];
	    mMatrix[13] = 0.0f;
	    
	    mMatrix[2] = zFlipped[0];
	    mMatrix[6] = zFlipped[1];
	    mMatrix[10] = zFlipped[2];
	    mMatrix[14] = 0.0f;
	    
	    mMatrix[3] = 0.0f;
	    mMatrix[7] = 0.0f;
	    mMatrix[11] = 0.0f;
	    mMatrix[15] = 1.0f;
	    
	    // Do the rotation first
	    FloatBuffer m = BufferUtils.createFloatBuffer(16);
	    m.put(mMatrix);
	    m.flip();
	    glMultMatrix(m);
	    
	    // Now, translate backwards
	    glTranslatef(-vLocation[0], -vLocation[1], -vLocation[2]);
	}
	
	////////////////////////////////////////////////////////////////////
	// Apply an actors transform given it's frame of reference
	public void gltApplyActorTransform()
	{
	    float[] mTransform = new float[16];
	    gltGetMatrixFromFrame(mTransform);
	    glMultMatrix(array2FloatBuffer(mTransform));
	}
	
    
	///////////////////////////////////////////////////////////////////
	//Derives a 4x4 transformation matrix from a frame of reference
	public void gltGetMatrixFromFrame(float[] mMatrix)
	{
		float[] vXAxis = new float[3];       // Derived X Axis
	
		// Calculate X Axis
		gltVectorCrossProduct(vUp, vForward, vXAxis);
		
		// Just populate the matrix
		// X column vector
		mMatrix[0] = vXAxis[0];
		mMatrix[1] = vXAxis[1];
		mMatrix[2] = vXAxis[2];
		mMatrix[3] = 0.0f;
		
		// y column vector
		mMatrix[4] = vUp[0];
		mMatrix[5] = vUp[1];
		mMatrix[6] = vUp[2];
		mMatrix[7] = 0.0f;
		
		// z column vector
		mMatrix[8]  = vForward[0];
		mMatrix[9]  = vForward[1];
		mMatrix[10] = vForward[2];
		mMatrix[11] = 0.0f;
		
		// Translation/Location vector
		mMatrix[12]  = vLocation[0];
		mMatrix[13]  = vLocation[1];
		mMatrix[14] = vLocation[2];
		mMatrix[15] = 1.0f;
		
	}
	
	public void gltMoveFrameForward(float fStep)
	{
	    vLocation[0] += vForward[0] * fStep;
	    vLocation[1] += vForward[1] * fStep;
	    vLocation[2] += vForward[2] * fStep;
	}
	
	/////////////////////////////////////////////////////////
	// Rotate a frame around it's local Y axis
	public void gltRotateFrameLocalY(float fAngle)
	{
	    float[] mRotation = new float[16];
	    float[] vNewForward = new float[3];
	    
	    gltRotationMatrix((float)gltDegToRad(fAngle), 0.0f, 1.0f, 0.0f, mRotation);
	    gltRotationMatrix(fAngle, vUp[0], vUp[1], vUp[2], mRotation);

	    gltRotateVector(vForward, mRotation, vNewForward);
	    vForward[0] = vNewForward[0];
	    vForward[1] = vNewForward[1];
	    vForward[2] = vNewForward[2];
	}

}
