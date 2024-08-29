package org.aesgard.caninoengine.texture;

import org.aesgard.caninoengine.image.ImageType;
import org.aesgard.caninoengine.image.TargaLoader;

public final class TextureLoader {
	
	public Texture Load(ImageType type, String fileName) {
		switch (type) {
		case TGA:
			TargaLoader l = new TargaLoader();
			l.load(fileName);
			l.printHeaders();
			Texture texture = 
				new Texture(l.getData(), l.getWidth(), l.getHeight());
			return texture;
		}
		return null;
	}
	

	
}
