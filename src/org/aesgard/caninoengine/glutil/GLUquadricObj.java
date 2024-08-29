package org.aesgard.caninoengine.glutil;

import org.lwjgl.util.glu.Cylinder;
import org.lwjgl.util.glu.Disk;
import org.lwjgl.util.glu.Quadric;
import org.lwjgl.util.glu.Sphere;

public class GLUquadricObj extends Quadric {
	private static Sphere sphere = null;
	private static Cylinder cylinder = null;
	private static Disk disk = null;

	public void gluCylinder(double base, double top, double height, int slices,
			int stacks) {
		if (cylinder == null) {
			cylinder = new Cylinder();
		}
		cylinder.draw((float) base, (float) top, (float) height, slices, stacks);
	}

	public void gluDisk(double inner, double outer, int slices, int loops) {
		if (disk == null) {
			disk = new Disk();
		}
		disk.draw((float) inner, (float) outer, slices, loops);
	}

	public void gluSphere(double radius, int slices, int stacks) {
		if (sphere == null) {
			sphere = new Sphere();
		}
		sphere.draw((float) radius, slices, stacks);
	}

}
