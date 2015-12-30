package com.kac.winname;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.win32.StdCallLibrary;

public class JnaUser32 {
	private static final int MAX_TITLE_LENGTH = 1024;

	public static class Res {
		public String title;
		public String processBin;

		@Override
		public String toString() {
			return "Res [title=" + title + ", processBin=" + processBin + "]";
		}

	}

	public JnaUser32() {
		Object o = Kernel32.INSTANCE;
		o = User32.INSTANCE;
		o = Psapi.INSTANCE;
	}

	public static Res getForegroundWindow() throws Exception {
		Res res = new Res();
		char[] buffer = new char[MAX_TITLE_LENGTH * 2];
		HWND foreground = User32.INSTANCE.GetForegroundWindow();
		User32.INSTANCE.GetWindowTextW(foreground, buffer, MAX_TITLE_LENGTH);
		res.title = Native.toString(buffer);

		PointerByReference pointer = new PointerByReference();
		User32.INSTANCE.GetWindowThreadProcessId(foreground, pointer);
		Pointer process = Kernel32.INSTANCE.OpenProcess(
				Kernel32.PROCESS_QUERY_INFORMATION | Kernel32.PROCESS_VM_READ, false,
				pointer.getValue());
		Psapi.INSTANCE.GetModuleFileNameExW(process, null, buffer, MAX_TITLE_LENGTH);
		res.processBin = Native.toString(buffer);
		return res;
	}

	public static void main(String[] args) throws Exception {
		long t = System.currentTimeMillis();
		Res r = getForegroundWindow();
		System.out.println(r + " in " + (System.currentTimeMillis() - t));
	}

	public interface Psapi extends com.sun.jna.Library {
		Psapi INSTANCE = (Psapi) Native.loadLibrary("psapi", Psapi.class);

		public int GetModuleBaseNameW(Pointer hProcess, Pointer hmodule, char[] lpBaseName,
				int size);

		public int GetModuleFileNameExW(Pointer hProcess, Pointer hmodule, char[] lpBaseName,
				int size);
	}

	public interface Kernel32 extends com.sun.jna.Library {
		Kernel32 INSTANCE = (Kernel32) Native.loadLibrary("kernel32", Kernel32.class);

		public static int PROCESS_QUERY_INFORMATION = 0x0400;
		public static int PROCESS_VM_READ = 0x0010;

		public int GetLastError();

		public Pointer OpenProcess(int dwDesiredAccess, boolean bInheritHandle, Pointer pointer);
	}

	public interface User32 extends StdCallLibrary {
		User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class);

		public int GetWindowThreadProcessId(HWND hWnd, PointerByReference pref);

		public HWND GetForegroundWindow();

		public int GetWindowTextW(HWND hWnd, char[] lpString, int nMaxCount);
	}
}