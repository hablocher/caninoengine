package org.aesgard.caninoengine.input;

import org.lwjgl.input.Keyboard;

public final class KeyboardUtil {
	
	public static float[] rotateUsingDefaultKeys(float xRot, float yRot) {
		if (Keyboard.next() || Keyboard.isRepeatEvent()) {
			if(Keyboard.isKeyDown(Keyboard.KEY_UP))
				xRot -= 5.0f;

			if(Keyboard.isKeyDown(Keyboard.KEY_DOWN))
				xRot += 5.0f;

			if(Keyboard.isKeyDown(Keyboard.KEY_LEFT))
				yRot -= 5.0f;

			if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
				yRot += 5.0f;

			return new float[]{xRot,yRot};
		}
		return null;
	}
	
	public static boolean isESC() {
		return Keyboard.isKeyDown(Keyboard.KEY_ESCAPE);
	}
}
