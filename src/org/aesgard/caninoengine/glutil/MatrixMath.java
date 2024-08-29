package org.aesgard.caninoengine.glutil;

import static org.aesgard.caninoengine.glutil.VectorMath.*;

public final class MatrixMath {

	///////////////////////////////////////////////////////////////////////////////
	//Creates a 4x4 rotation matrix, takes radians NOT degrees
	public static void gltRotationMatrix(float angle, float x, float y, float z, float[] mMatrix)
	{
		float vecLength, sinSave, cosSave, oneMinusCos;
		float xx, yy, zz, xy, yz, zx, xs, ys, zs;
	
		// If NULL vector passed in, this will blow up...
		if(x == 0.0f && y == 0.0f && z == 0.0f)
		{
			gltLoadIdentityMatrix(mMatrix);
			return;
		}
	
		// Scale vector
		vecLength = (float)Math.sqrt( x*x + y*y + z*z );
	
		// Rotation matrix is normalized
		x /= vecLength;
		y /= vecLength;
		z /= vecLength;
	
		sinSave = (float)Math.sin(angle);
		cosSave = (float)Math.cos(angle);
		oneMinusCos = 1.0f - cosSave;
	
		xx = x * x;
		yy = y * y;
		zz = z * z;
		xy = x * y;
		yz = y * z;
		zx = z * x;
		xs = x * sinSave;
		ys = y * sinSave;
		zs = z * sinSave;
	
		mMatrix[0] = (oneMinusCos * xx) + cosSave;
		mMatrix[4] = (oneMinusCos * xy) - zs;
		mMatrix[8] = (oneMinusCos * zx) + ys;
		mMatrix[12] = 0.0f;
	
		mMatrix[1] = (oneMinusCos * xy) + zs;
		mMatrix[5] = (oneMinusCos * yy) + cosSave;
		mMatrix[9] = (oneMinusCos * yz) - xs;
		mMatrix[13] = 0.0f;
	
		mMatrix[2] = (oneMinusCos * zx) - ys;
		mMatrix[6] = (oneMinusCos * yz) + xs;
		mMatrix[10] = (oneMinusCos * zz) + cosSave;
		mMatrix[14] = 0.0f;
		
		mMatrix[3] = 0.0f;
		mMatrix[7] = 0.0f;
		mMatrix[11] = 0.0f;
		mMatrix[15] = 1.0f;
	}
	
	///////////////////////////////////////////////////////////////////////////////
	// Load a matrix with the Idenity matrix
	public static void gltLoadIdentityMatrix(float[] m)
	{
	 	float[] identity = { 1.0f, 0.0f, 0.0f, 0.0f,
                             0.0f, 1.0f, 0.0f, 0.0f,
                             0.0f, 0.0f, 1.0f, 0.0f,
                             0.0f, 0.0f, 0.0f, 1.0f };
		m = identity;
	}
	
	// Creates a shadow projection matrix out of the plane equation
	// coefficients and the position of the light. The return value is stored
	// in destMat
	public static void gltMakeShadowMatrix(float[][] vPoints, float[] vLightPos, float[] destMat)
	{
	    float[] vPlaneEquation = new float[4];
	    float dot;

	    gltGetPlaneEquation(vPoints[0], vPoints[1], vPoints[2], vPlaneEquation);
	  
	    // Dot product of plane and light position
	    dot =   vPlaneEquation[0]*vLightPos[0] + 
	            vPlaneEquation[1]*vLightPos[1] + 
	            vPlaneEquation[2]*vLightPos[2] + 
	            vPlaneEquation[3]*vLightPos[3];

	    
	    // Now do the projection
	    // First column
	    destMat[0] = dot - vLightPos[0] * vPlaneEquation[0];
	    destMat[4] = 0.0f - vLightPos[0] * vPlaneEquation[1];
	    destMat[8] = 0.0f - vLightPos[0] * vPlaneEquation[2];
	    destMat[12] = 0.0f - vLightPos[0] * vPlaneEquation[3];

	    // Second column
	    destMat[1] = 0.0f - vLightPos[1] * vPlaneEquation[0];
	    destMat[5] = dot - vLightPos[1] * vPlaneEquation[1];
	    destMat[9] = 0.0f - vLightPos[1] * vPlaneEquation[2];
	    destMat[13] = 0.0f - vLightPos[1] * vPlaneEquation[3];

	    // Third Column
	    destMat[2] = 0.0f - vLightPos[2] * vPlaneEquation[0];
	    destMat[6] = 0.0f - vLightPos[2] * vPlaneEquation[1];
	    destMat[10] = dot - vLightPos[2] * vPlaneEquation[2];
	    destMat[14] = 0.0f - vLightPos[2] * vPlaneEquation[3];

	    // Fourth Column
	    destMat[3] = 0.0f - vLightPos[3] * vPlaneEquation[0];
	    destMat[7] = 0.0f - vLightPos[3] * vPlaneEquation[1];
	    destMat[11] = 0.0f - vLightPos[3] * vPlaneEquation[2];
	    destMat[15] = dot - vLightPos[3] * vPlaneEquation[3];
	}
}
