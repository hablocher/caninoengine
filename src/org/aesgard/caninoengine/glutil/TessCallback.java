package org.aesgard.caninoengine.glutil;

import static org.lwjgl.util.glu.GLU.gluErrorString;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLUtessellatorCallbackAdapter;

public class TessCallback extends GLUtessellatorCallbackAdapter {
    public void begin(int mode) {
        GL11.glBegin(mode);
    }
    
    public void end() {
    	GL11.glEnd();
    }
    
    public void error(int errnum) {
		// Get error message string
		String szError = gluErrorString(errnum);
		if (szError != null)
			System.out.println(szError);
    }

    public void vertex(Object data) {
    	 VertexData vertex = (VertexData) data;
    	 GL11.glVertex3d(vertex.dataDouble[0], vertex.dataDouble[1], vertex.dataDouble[2]);
    	 
    }
}
