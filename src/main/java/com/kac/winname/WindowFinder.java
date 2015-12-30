package com.kac.winname;

public interface WindowFinder {
	public static class Window {
		public String title;
		public long windowId;
		public long processId;
		public String processBin;

		@Override
		public String toString() {
			return "Window [title=" + title + ", windowId=" + windowId + ", processId=" + processId
					+ ", processBin=" + processBin + "]";
		}

	}

	public static class WindowSearchException extends Exception {

		public WindowSearchException(String message, Throwable cause) {
			super(message, cause);
			// TODO Auto-generated constructor stub
		}

	}

	public Window getActiveWindow() throws WindowSearchException;
}
