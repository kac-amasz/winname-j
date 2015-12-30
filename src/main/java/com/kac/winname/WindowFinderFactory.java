package com.kac.winname;

import com.sun.jna.Platform;

public class WindowFinderFactory {
	public WindowFinder getWindowFinder() throws Exception {
		if (Platform.isLinux()) {
			return new XdotoolFinder();
		} else if (Platform.isWindows()) {
			try {
				return (WindowFinder) Class
						.forName(WindowFinder.class.getPackage().getName() + ".JnaUser32Finder")
						.newInstance();
			} catch (LinkageError le) {
				throw new IllegalStateException("native libraries error", le);
			}
		} else {
			throw new UnsupportedOperationException("unsupported platform");
		}
	}
}
