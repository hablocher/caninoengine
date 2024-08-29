package org.aesgard.caninoengine.glutil;

import static org.aesgard.caninoengine.util.BufferUtils.array2FloatBuffer;
import static org.aesgard.caninoengine.util.BufferUtils.array2IntBuffer;
import static org.aesgard.caninoengine.util.BufferUtils.array2ByteBuffer;
import static org.aesgard.caninoengine.util.BufferUtils.array2ShortBuffer;

import java.nio.IntBuffer;
import java.util.Random;

import org.aesgard.caninoengine.actor.Torus;
import org.lwjgl.opengl.ARBImaging;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;
import org.lwjgl.util.glu.GLUtessellator;
import org.lwjgl.util.glu.Sphere;


public final class APITranslations {
	public static final int RAND_MAX = 0x7fffffff;

	private static Sphere sphere = null;
	private static Cylinder cylinder = null;
	private static Torus torus = null;
	private static Random rand = new Random();
	private static TessCallback callback = new TessCallback();

	private APITranslations() {
	}

	// OPENGL
	public static void glColor3ub(int red, int green, int blue) {
		GL11.glColor3ub((byte) red, (byte) green, (byte) blue);
	}

	public static void glNormal3fv(float[] v) {
		GL11.glNormal3f(v[0], v[1], v[2]);
	}

	public static void glVertex3fv(float[] v) {
		GL11.glVertex3f(v[0], v[1], v[2]);
	}

	public static void glMultMatrixf(float[] m) {
		GL11.glMultMatrix(array2FloatBuffer(m));
	}

	public static void glMaterialfv(int face, int pname, float[] params) {
		GL11.glMaterial(face, pname, array2FloatBuffer(params));
	}

	public static void glLightfv(int light, int pname, float[] params) {
		GL11.glLight(light, pname, array2FloatBuffer(params));
	}

	public static void glLightModelfv(int pname, float[] params) {
		GL11.glLightModel(pname, array2FloatBuffer(params));
	}

	public static void glFogfv(int pname, float[] params) {
		GL11.glFog(pname, array2FloatBuffer(params));
	}

	public static void glDeleteTextures(int n, int[] textures) {
		GL11.glDeleteTextures(array2IntBuffer(textures));
	}

	public static void glGenTextures(int n, int[] textures) {
		IntBuffer b = array2IntBuffer(textures);
		GL11.glGenTextures(b);
		b.get(textures);
	}

	public static void glVertex2fv(float[] v) {
		GL11.glVertex2f(v[0], v[1]);
	}

	public static void glGetIntegerv(int pname, int[] params) {
		IntBuffer b = array2IntBuffer(params);
		GL11.glGetInteger(pname, b);
		for (int i = 0; i < params.length; i++) {
			params[i] = b.get(i);
		}
	}

	public static void glLoadMatrixf(float[] m) {
		GL11.glLoadMatrix(array2FloatBuffer(m));
	}

	public static void glConvolutionFilter2D(int target, int internalformat,
			int width, int height, int format, int type, final int[] data) {
		ARBImaging.glConvolutionFilter2D(target, internalformat, width, height,
				format, type, array2IntBuffer(data));
	}

	public static void glPixelMapfv(int map, int mapsize, float[] values) {
		GL11.glPixelMap(map, array2FloatBuffer(values));

	}

	public static void glTexGenfv(int coord, int pname, float[] params) {
		GL11.glTexGen(coord, pname, array2FloatBuffer(params));
	}

	public static void glDepthMask(int flag) {
		GL11.glDepthMask(flag == GL11.GL_FALSE ? false : true);
	}

	public static void gltDrawTorus(double majorRadius, double minorRadius,
			int numMajor, int numMinor) {
		if (torus == null) {
			torus = new Torus();
		}
		torus.gltDrawTorus((float) majorRadius, (float) minorRadius, numMajor,
				numMinor);
	}

	public static void glMap2f(int target, float u1, float u2, int ustride,
			int uorder, float v1, float v2, int vstride, int vorder,
			float[][][] points) {
		GL11.glMap2f(target, u1, u2, ustride, uorder, v1, v2, vstride, vorder,
				array2FloatBuffer(points));
	}

	public static void glMap1f(int target, float u1, float u2, int stride,
			int order, float[][] points) {
		GL11.glMap1f(target, u1, u2, stride, order, array2FloatBuffer(points));
	}
	
	public static void glVertex3dv( double[] v ) {
		GL11.glVertex3d(v[0], v[1], v[2]);
	}
	
	public static void glVertexPointer(int size, int type, int stride, float[] pointer) {
		GL11.glVertexPointer(size, stride, array2FloatBuffer(pointer));
	}
	
	public static void glDrawElements(int mode, int count, int type, byte[] indices) {
		GL11.glDrawElements(mode, array2ByteBuffer(indices));
	}

	public static void glDrawElements(int mode, int count, int type, short[] indices) {
		GL11.glDrawElements(mode, array2ShortBuffer(indices));
	}
	
	public static void glDrawElements(int mode, int count, int type, int[] indices) {
		GL11.glDrawElements(mode, array2IntBuffer(indices));
	}

	// GLU
	public static void glutSolidSphere(float radius, int slices, int stacks) {
		if (sphere == null) {
			sphere = new Sphere();
		}
		sphere.setTextureFlag(true);
		sphere.draw(radius, slices, stacks);
	}

	public static void glutSolidCone(double base, double height, int slices,
			int stacks) {
		if (cylinder == null) {
			cylinder = new Cylinder();
		}
		cylinder.draw((float) base, 0f, (float) height, slices, stacks);
	}

	public static void gltDrawSphere(float radius, int slices, int stacks) {
		glutSolidSphere(radius, slices, stacks);
	}

	public static void gltInitFrame(GLTFrame frame) {
		frame.gltInitFrame();
	}

	public static void gltApplyCameraTransform(GLTFrame frame) {
		frame.gltApplyCameraTransform();
	}

	public static void gltApplyActorTransform(GLTFrame frame) {
		frame.gltApplyActorTransform();
	}

	public static void gltMoveFrameForward(GLTFrame frame, float step) {
		frame.gltMoveFrameForward(step);
	}

	public static void gltRotateFrameLocalY(GLTFrame frame, double fAngle) {
		frame.gltRotateFrameLocalY((float) fAngle);
	}

	public static GLUquadricObj gluNewQuadric() {
		return new GLUquadricObj();
	}

	public static void gluQuadricDrawStyle(GLUquadricObj obj, int style) {
		obj.setDrawStyle(style);
	}

	public static void gluQuadricNormals(GLUquadricObj obj, int normals) {
		obj.setNormals(normals);
	}

	public static void gluQuadricOrientation(GLUquadricObj obj, int orientation) {
		obj.setOrientation(orientation);
	}

	public static void gluQuadricTexture(GLUquadricObj obj, boolean textureFlag) {
		obj.setTextureFlag(textureFlag);
	}

	public static void gluCylinder(GLUquadricObj obj, double base, double top,
			double height, int slices, int stacks) {
		obj.gluCylinder(base, top, height, slices, stacks);
	}

	public static void gluDisk(GLUquadricObj obj, double inner, double outer,
			int slices, int loops) {
		obj.gluDisk(inner, outer, slices, loops);
	}

	public static void gluSphere(GLUquadricObj obj, double radius, int slices,
			int stacks) {
		obj.gluSphere(radius, slices, stacks);
	}

	public static void gluDeleteQuadric(GLUquadricObj obj) {
		obj = null;
	}
	
	public static void gluTessCallback(GLUtessellator tess, int which, TessallationType callBack) {
		switch (callBack) {
		default:
			tess.gluTessCallback(which, callback);
			break;
		}
	}
	
	public static void gluTessBeginPolygon(GLUtessellator  tess,  Object  data) {
		tess.gluBeginPolygon();
	}
	
	public static void gluTessBeginContour(GLUtessellator tess) {
		tess.gluTessBeginContour();
	}
	
	public static void gluTessVertex(GLUtessellator tess, double[] location, VertexData data) {
		tess.gluTessVertex(location, 0, data);
	}
	
	public static void gluTessEndContour(GLUtessellator tess) {
		tess.gluTessEndContour();
	}
	
	public static void gluTessEndPolygon(GLUtessellator tess) {
		tess.gluTessEndPolygon();
	}
	
	public static void gluDeleteTess(GLUtessellator tess) {
		tess.gluDeleteTess();
	}
	
	public static void gluTessProperty(GLUtessellator  tess,  int  which,  double  data) {
		tess.gluTessProperty(which, data);
	}

	// MATH
	public static double cos(float angle) {
		return Math.cos((double) angle);
	}

	public static double sin(float angle) {
		return Math.sin((double) angle);
	}

	public static double cos(double angle) {
		return Math.cos(angle);
	}

	public static double sin(double angle) {
		return Math.sin(angle);
	}
	
	public static double fabs(float x) {
		return Math.abs(x);
	}

	public static int rand() {
		return rand.nextInt(RAND_MAX);
	}
}
