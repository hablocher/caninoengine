package org.aesgard.caninoengine.util;

import java.net.URL;

public final class FileUtil {

	public static URL getURL(String filename) {
	    URL url = null;
	    try {
	        url = FileUtil.class.getResource(filename);
	    }
	    catch (Exception e) { }
	    return url;
	}

}
