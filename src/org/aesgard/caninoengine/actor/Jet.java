package org.aesgard.caninoengine.actor;
import static org.aesgard.caninoengine.glutil.APITranslations.glColor3ub;
import static org.aesgard.caninoengine.glutil.APITranslations.glNormal3fv;
import static org.aesgard.caninoengine.glutil.APITranslations.glVertex3fv;
import static org.aesgard.caninoengine.glutil.VectorMath.gltGetNormalVector;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glNormal3f;
import static org.lwjgl.opengl.GL11.glVertex3f;


public class Jet {
	
	////////////////////////////////////////////////
	//This function just specifically draws the jet
	public void DrawJet(int nShadow)
	{
		float[] vNormal = new float[3];	// Storeage for calculated surface normal
	
		// Nose Cone /////////////////////////////
		// Set material color, note we only have to set to black
		// for the shadow once
		if(nShadow == 0)
			glColor3ub(128, 128, 128);
		else
			glColor3ub(0,0,0);
	
		// Nose Cone - Points straight down
		glBegin(GL_TRIANGLES);
			glNormal3f(0.0f, -1.0f, 0.0f);
			glNormal3f(0.0f, -1.0f, 0.0f);
			glVertex3f(0.0f, 0.0f, 60.0f);
			glVertex3f(-15.0f, 0.0f, 30.0f);
			glVertex3f(15.0f,0.0f,30.0f);
	
			// Verticies for this panel
			{
				float[][] vPoints = {{ 15.0f, 0.0f,  30.0f},
									{ 0.0f,  15.0f, 30.0f},
									{ 0.0f,  0.0f,  60.0f}};
		
				// Calculate the normal for the plane
				gltGetNormalVector(vPoints[0], vPoints[1], vPoints[2], vNormal);
				glNormal3fv(vNormal);
				glVertex3fv(vPoints[0]);
				glVertex3fv(vPoints[1]);
				glVertex3fv(vPoints[2]);
			}	
	
			{
				float[][] vPoints = {{ 0.0f, 0.0f, 60.0f },
									{ 0.0f, 15.0f, 30.0f },
									{ -15.0f, 0.0f, 30.0f }};
				
				gltGetNormalVector(vPoints[0], vPoints[1], vPoints[2], vNormal);
				glNormal3fv(vNormal);
				glVertex3fv(vPoints[0]);
				glVertex3fv(vPoints[1]);
				glVertex3fv(vPoints[2]);
			}
	
			// Body of the Plane ////////////////////////
			{
				float[][] vPoints = {{ -15.0f, 0.0f, 30.0f },
									{ 0.0f, 15.0f, 30.0f },
									{ 0.0f, 0.0f, -56.0f }};
			
				gltGetNormalVector(vPoints[0], vPoints[1], vPoints[2], vNormal);
				glNormal3fv(vNormal);
				glVertex3fv(vPoints[0]);
				glVertex3fv(vPoints[1]);
				glVertex3fv(vPoints[2]);
			}
	
			{
				float[][] vPoints = {{ 0.0f, 0.0f, -56.0f },
									{ 0.0f, 15.0f, 30.0f },
									{ 15.0f,0.0f,30.0f }};
				
				gltGetNormalVector(vPoints[0], vPoints[1], vPoints[2], vNormal);
				glNormal3fv(vNormal);
				glVertex3fv(vPoints[0]);
				glVertex3fv(vPoints[1]);
				glVertex3fv(vPoints[2]);
			}
	
			glNormal3f(0.0f, -1.0f, 0.0f);
			glVertex3f(15.0f,0.0f,30.0f);
			glVertex3f(-15.0f, 0.0f, 30.0f);
			glVertex3f(0.0f, 0.0f, -56.0f);
	
			///////////////////////////////////////////////
			// Left wing
			// Large triangle for bottom of wing
			{
				float[][] vPoints = {{ 0.0f,2.0f,27.0f },
									{ -60.0f, 2.0f, -8.0f },
									{ 60.0f, 2.0f, -8.0f }};
				
				gltGetNormalVector(vPoints[0], vPoints[1], vPoints[2], vNormal);
				glNormal3fv(vNormal);
				glVertex3fv(vPoints[0]);
				glVertex3fv(vPoints[1]);
				glVertex3fv(vPoints[2]);
			}
	
			{
				float[][] vPoints = {{ 60.0f, 2.0f, -8.0f},
									{0.0f, 7.0f, -8.0f},
									{0.0f,2.0f,27.0f }};
				
				gltGetNormalVector(vPoints[0], vPoints[1], vPoints[2], vNormal);
				glNormal3fv(vNormal);
				glVertex3fv(vPoints[0]);
				glVertex3fv(vPoints[1]);
				glVertex3fv(vPoints[2]);
			}
	
			{
				float[][] vPoints = {{60.0f, 2.0f, -8.0f},
									{-60.0f, 2.0f, -8.0f},
									{0.0f,7.0f,-8.0f }};
				
				gltGetNormalVector(vPoints[0], vPoints[1], vPoints[2], vNormal);
				glNormal3fv(vNormal);
				glVertex3fv(vPoints[0]);
				glVertex3fv(vPoints[1]);
				glVertex3fv(vPoints[2]);
			}
	
			{
				float[][] vPoints = {{0.0f,2.0f,27.0f},
									{0.0f, 7.0f, -8.0f},
									{-60.0f, 2.0f, -8.0f}};
			
				gltGetNormalVector(vPoints[0], vPoints[1], vPoints[2], vNormal);
				glNormal3fv(vNormal);
				glVertex3fv(vPoints[0]);
				glVertex3fv(vPoints[1]);
				glVertex3fv(vPoints[2]);
			}
	
			// Tail section///////////////////////////////
			// Bottom of back fin
			glNormal3f(0.0f, -1.0f, 0.0f);
			glVertex3f(-30.0f, -0.50f, -57.0f);
			glVertex3f(30.0f, -0.50f, -57.0f);
			glVertex3f(0.0f,-0.50f,-40.0f);
			{
				float[][] vPoints = {{ 0.0f,-0.5f,-40.0f },
									{30.0f, -0.5f, -57.0f},
									{0.0f, 4.0f, -57.0f }};
				
				gltGetNormalVector(vPoints[0], vPoints[1], vPoints[2], vNormal);
				glNormal3fv(vNormal);
				glVertex3fv(vPoints[0]);
				glVertex3fv(vPoints[1]);
				glVertex3fv(vPoints[2]);
			}
	
			{
				float[][] vPoints = {{ 0.0f, 4.0f, -57.0f },
									{ -30.0f, -0.5f, -57.0f },
									{ 0.0f,-0.5f,-40.0f }};
				
				gltGetNormalVector(vPoints[0], vPoints[1], vPoints[2], vNormal);
				glNormal3fv(vNormal);
				glVertex3fv(vPoints[0]);
				glVertex3fv(vPoints[1]);
				glVertex3fv(vPoints[2]);
			}
	
	
			{
				float[][] vPoints = {{ 30.0f,-0.5f,-57.0f },
									{ -30.0f, -0.5f, -57.0f },
									{ 0.0f, 4.0f, -57.0f }};
				
				gltGetNormalVector(vPoints[0], vPoints[1], vPoints[2], vNormal);
				glNormal3fv(vNormal);
				glVertex3fv(vPoints[0]);
				glVertex3fv(vPoints[1]);
				glVertex3fv(vPoints[2]);
			}
	
			{
				float[][] vPoints = {{ 0.0f,0.5f,-40.0f },
									{ 3.0f, 0.5f, -57.0f },
									{ 0.0f, 25.0f, -65.0f }};
				
				gltGetNormalVector(vPoints[0], vPoints[1], vPoints[2], vNormal);
				glNormal3fv(vNormal);
				glVertex3fv(vPoints[0]);
				glVertex3fv(vPoints[1]);
				glVertex3fv(vPoints[2]);
			}
	
	
			{
				float[][] vPoints = {{ 0.0f, 25.0f, -65.0f },
									{ -3.0f, 0.5f, -57.0f},
									{ 0.0f,0.5f,-40.0f }};
				
				gltGetNormalVector(vPoints[0], vPoints[1], vPoints[2], vNormal);
				glNormal3fv(vNormal);
				glVertex3fv(vPoints[0]);
				glVertex3fv(vPoints[1]);
				glVertex3fv(vPoints[2]);
			}
	
			{
				float[][] vPoints = {{ 3.0f,0.5f,-57.0f },
									{ -3.0f, 0.5f, -57.0f },
									{ 0.0f, 25.0f, -65.0f }};
				
				gltGetNormalVector(vPoints[0], vPoints[1], vPoints[2], vNormal);
				glNormal3fv(vNormal);
				glVertex3fv(vPoints[0]);
				glVertex3fv(vPoints[1]);
				glVertex3fv(vPoints[2]);
			}
	
	
		glEnd();
	}

}
