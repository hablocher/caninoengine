package org.aesgard.caninoengine.glutil;

public final class VectorMath {
	
	private VectorMath() {}
	
	// Calculate the cross product of two vectors
	public static void gltVectorCrossProduct(final float[] vU, final float[] vV, float[] vResult)
	{
		vResult[0] = vU[1]*vV[2] - vV[1]*vU[2];
		vResult[1] = -vU[0]*vV[2] + vV[0]*vU[2];
		vResult[2] = vU[0]*vV[1] - vV[0]*vU[1];
	}
	
	// Scales a vector by it's length - creates a unit vector
	public static void gltNormalizeVector(float[] vNormal)
	{
	    float fLength = 1.0f / gltGetVectorLength(vNormal);
	    gltScaleVector(vNormal, fLength); 
	}
	
	// Gets the length of a vector
	public static float gltGetVectorLength(final float[] vVector)
	{
	    return (float)Math.sqrt(gltGetVectorLengthSqrd(vVector)); 
	}
	
	// Gets the length of a vector squared
	public static float gltGetVectorLengthSqrd(final float[] vVector)
	{
	    return (vVector[0]*vVector[0]) + (vVector[1]*vVector[1]) + (vVector[2]*vVector[2]); 
	}
	
	// Scales a vector by a scalar
	public static void gltScaleVector(float[] vVector, final float fScale)
	{
	    vVector[0] *= fScale; vVector[1] *= fScale; vVector[2] *= fScale; 
	}
	
	// Ambient a point by a 4x4 matrix
	public static void gltTransformPoint(final float[] vSrcVector, final float[] mMatrix, float[] vOut)
	{
	    vOut[0] = mMatrix[0] * vSrcVector[0] + mMatrix[4] * vSrcVector[1] + mMatrix[8] *  vSrcVector[2] + mMatrix[12];
	    vOut[1] = mMatrix[1] * vSrcVector[0] + mMatrix[5] * vSrcVector[1] + mMatrix[9] *  vSrcVector[2] + mMatrix[13];
	    vOut[2] = mMatrix[2] * vSrcVector[0] + mMatrix[6] * vSrcVector[1] + mMatrix[10] * vSrcVector[2] + mMatrix[14];    
	}
	

	// Given three points on a plane in counter clockwise order, calculate the unit normal
	public static void gltGetNormalVector(final float[] vP1, final float[] vP2, final float[] vP3, float[] vNormal)
	{
	    float[] vV1 = new float[3];
	    float[] vV2 = new float[3];
	    
	    gltSubtractVectors(vP2, vP1, vV1);
	    gltSubtractVectors(vP3, vP1, vV2);
	    
	    gltVectorCrossProduct(vV1, vV2, vNormal);
	    gltNormalizeVector(vNormal);
	}
	
	// Subtract one vector from another
	public static void gltSubtractVectors(final float[] vFirst, final float[] vSecond, float[] vResult) 
	{
	    vResult[0] = vFirst[0] - vSecond[0];
	    vResult[1] = vFirst[1] - vSecond[1];
	    vResult[2] = vFirst[2] - vSecond[2];
	}
	
	// Gets the three coefficients of a plane equation given three points on the plane.
	public static void gltGetPlaneEquation(float[] vPoint1, float[] vPoint2, float[] vPoint3, float[] vPlane)
	{
	    // Get normal vector from three points. The normal vector is the first three coefficients
	    // to the plane equation...
	    gltGetNormalVector(vPoint1, vPoint2, vPoint3, vPlane);
	    
	    // Final coefficient found by back substitution
	    vPlane[3] = -(vPlane[0] * vPoint3[0] + vPlane[1] * vPoint3[1] + vPlane[2] * vPoint3[2]);
	}
	
	// Rotates a vector using a 4x4 matrix. Translation column is ignored
	public static void gltRotateVector(final float[] vSrcVector, final float[] mMatrix, float[] vOut)
	{
	    vOut[0] = mMatrix[0] * vSrcVector[0] + mMatrix[4] * vSrcVector[1] + mMatrix[8] *  vSrcVector[2];
	    vOut[1] = mMatrix[1] * vSrcVector[0] + mMatrix[5] * vSrcVector[1] + mMatrix[9] *  vSrcVector[2];
	    vOut[2] = mMatrix[2] * vSrcVector[0] + mMatrix[6] * vSrcVector[1] + mMatrix[10] * vSrcVector[2];    	
	}
	
	// Get the dot product between two vectors
	public static float gltVectorDotProduct(final float[] vU, final float[] vV)
	{
	    return vU[0]*vV[0] + vU[1]*vV[1] + vU[2]*vV[2]; 
	}
}
