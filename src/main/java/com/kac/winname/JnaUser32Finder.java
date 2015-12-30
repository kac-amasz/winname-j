package com.kac.winname;

public class JnaUser32Finder implements WindowFinder {

	public Window getActiveWindow() throws WindowSearchException {
		Window w = new Window();
		try {
			JnaUser32.Res r = JnaUser32.getForegroundWindow();
			w.processBin = r.processBin;
			w.title = r.title;
			w.processId = w.windowId = -1;
		} catch (Exception e) {
			throw new WindowSearchException(null, e);
		}
		return w;
	}
}
