package com.kac.winname;

public class XdotoolFinder implements WindowFinder {
	public Window getActiveWindow() throws WindowSearchException {
		Window w = new Window();
		Xdotool.Res r = Xdotool.getFocusedWindow();
		if (r.exit != 0 || r.exception != null) {
			throw new WindowSearchException("search command failed with code " + r.exit + ", out "
					+ r.out + ", err " + r.err, r.exception);
		}
		try {
			w.windowId = Long.parseLong(r.out.get(0));
			w.processId = Long.parseLong(r.out.get(1));
			w.title = r.out.get(3);
			w.processBin = r.out.get(4);
		} catch (Exception e) {
			throw new WindowSearchException(null, e);
		}
		return w;
	}
}
