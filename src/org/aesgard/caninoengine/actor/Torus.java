package org.aesgard.caninoengine.actor;


import static org.aesgard.caninoengine.glutil.APITranslations.cos;
import static org.aesgard.caninoengine.glutil.APITranslations.sin;
import static org.aesgard.caninoengine.glutil.VectorMath.gltNormalizeVector;
import static org.aesgard.caninoengine.glutil.VectorMath.gltRotateVector;
import static org.aesgard.caninoengine.glutil.VectorMath.gltTransformPoint;
import static org.aesgard.caninoengine.glutil.VectorMath.gltVectorDotProduct;
import static org.aesgard.caninoengine.util.BufferUtils.array2FloatBuffer;
import static org.aesgard.caninoengine.util.Constants.GLT_PI;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW_MATRIX;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glGetFloat;
import static org.lwjgl.opengl.GL11.glNormal3f;
import static org.lwjgl.opengl.GL11.glTexCoord1f;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex3f;

import java.nio.FloatBuffer;


public class Torus {
	
	// For best results, put this in a display list
	
	// Draw a torus (doughnut)  at z = fZVal... torus is in xy plane
	public void gltDrawTorus(float majorRadius, float minorRadius, int numMajor, int numMinor)
	{
	    float[] vNormal = new float[3];
	    double majorStep = 2.0f*GLT_PI / numMajor;
	    double minorStep = 2.0f*GLT_PI / numMinor;
	    int i, j;

	    for (i=0; i<numMajor; ++i) 
	    {
            double a0 = i * majorStep;
            double a1 = a0 + majorStep;
            float x0 = (float) Math.cos(a0);
            float y0 = (float) Math.sin(a0);
            float x1 = (float) Math.cos(a1);
            float y1 = (float) Math.sin(a1);

            glBegin(GL_TRIANGLE_STRIP);
	            for (j=0; j<=numMinor; ++j) 
	            {
	                double b = j * minorStep;
	                float c = (float) Math.cos(b);
	                float r = minorRadius * c + majorRadius;
	                float z = minorRadius * (float) Math.sin(b);
	
	                // First point
	                glTexCoord2f((float)(i)/(float)(numMajor), (float)(j)/(float)(numMinor));
	                vNormal[0] = x0*c;
	                vNormal[1] = y0*c;
	                vNormal[2] = z/minorRadius;
	                gltNormalizeVector(vNormal);
	                glNormal3f(vNormal[0],vNormal[1],vNormal[2]);
	                glVertex3f(x0*r, y0*r, z);
	
	                glTexCoord2f((float)(i+1)/(float)(numMajor), (float)(j)/(float)(numMinor));
	                vNormal[0] = x1*c;
	                vNormal[1] = y1*c;
	                vNormal[2] = z/minorRadius;
	                glNormal3f(vNormal[0],vNormal[1],vNormal[2]);
	                glVertex3f(x1*r, y1*r, z);
	            }
            glEnd();
	    }
	}
	
	// Draw a torus (doughnut), using the current 1D texture for light shading
	public void DrawTorus(float[] mTransform)
	{
	    float majorRadius = 0.35f;
	    float minorRadius = 0.15f;
	    int   numMajor = 40;
	    int   numMinor = 20;
	    float[] objectVertex       = new float[3];         // Vertex in object/eye space
	    float[] transformedVertex  = new float[3];    // New Transformed vertex   
	    double majorStep = 2.0f*GLT_PI / numMajor;
	    double minorStep = 2.0f*GLT_PI / numMinor;
	    int i, j;
	    
	    for (i=0; i<numMajor; ++i) 
	    {
	        double a0 = i * majorStep;
	        double a1 = a0 + majorStep;
	        float x0 = (float) Math.cos(a0);
	        float y0 = (float) Math.sin(a0);
	        float x1 = (float) Math.cos(a1);
	        float y1 = (float) Math.sin(a1);

	        glBegin(GL_TRIANGLE_STRIP);
		        for (j=0; j<=numMinor; ++j) 
		        {
		            double b = j * minorStep;
		            float c = (float) Math.cos(b);
		            float r = minorRadius * c + majorRadius;
		            float z = minorRadius * (float) Math.sin(b);
	
		            // First point
		            objectVertex[0] = x0*r;
		            objectVertex[1] = y0*r;
		            objectVertex[2] = z;
		            gltTransformPoint(objectVertex, mTransform, transformedVertex);
		            glVertex3f(transformedVertex[0],transformedVertex[1],transformedVertex[2]);
	
		            // Second point
		            objectVertex[0] = x1*r;
		            objectVertex[1] = y1*r;
		            objectVertex[2] = z;
		            gltTransformPoint(objectVertex, mTransform, transformedVertex);
		            glVertex3f(transformedVertex[0],transformedVertex[1],transformedVertex[2]);
		        }
	        glEnd();
	    }
	}
	
	// Draw a torus (doughnut), using the current 1D texture for light shading
	public void toonDrawTorus(float majorRadius, float minorRadius, int numMajor, int numMinor, float[] vLightDir)
	{
		float[] mModelViewMatrix = new float[16];
	    FloatBuffer fb = array2FloatBuffer(mModelViewMatrix);
	    float[] vNormal = new float[3];
	    float[] vTransformedNormal = new float[3];
	    double majorStep = 2.0f*GLT_PI / numMajor;
	    double minorStep = 2.0f*GLT_PI / numMinor;
	    int i, j;
	    
	    // Get the modelview matrix
	    glGetFloat(GL_MODELVIEW_MATRIX, fb);
	    
	    fb.get(mModelViewMatrix);
	    
	    // Normalize the light vector
	    gltNormalizeVector(vLightDir);
	    
	    // Draw torus as a series of triangle strips
	    for (i=0; i<numMajor; ++i) 
	    {
	        double a0 = i * majorStep;
	        double a1 = a0 + majorStep;
	        float x0 = (float) cos(a0);
	        float y0 = (float) sin(a0);
	        float x1 = (float) cos(a1);
	        float y1 = (float) sin(a1);

	        glBegin(GL_TRIANGLE_STRIP);
	        for (j=0; j<=numMinor; ++j) 
	        {
	            double b = j * minorStep;
	            float c = (float) cos(b);
	            float r = minorRadius * c + majorRadius;
	            float z = minorRadius * (float) sin(b);

	            // First point
	            vNormal[0] = x0*c;
	            vNormal[1] = y0*c;
	            vNormal[2] = z/minorRadius;
	            gltNormalizeVector(vNormal);
	            gltRotateVector(vNormal, mModelViewMatrix, vTransformedNormal);
	            
	            // Texture coordinate is set by intensity of light
	            glTexCoord1f(gltVectorDotProduct(vLightDir, vTransformedNormal));
	            glVertex3f(x0*r, y0*r, z);

	            // Second point
	            vNormal[0] = x1*c;
	            vNormal[1] = y1*c;
	            vNormal[2] = z/minorRadius;
	            gltNormalizeVector(vNormal);
	            gltRotateVector(vNormal, mModelViewMatrix, vTransformedNormal);
	            
	            // Texture coordinate is set by intensity of light
	            glTexCoord1f(gltVectorDotProduct(vLightDir, vTransformedNormal));
	            glVertex3f(x1*r, y1*r, z);
	        }
	        glEnd();
	    }
	}
}
