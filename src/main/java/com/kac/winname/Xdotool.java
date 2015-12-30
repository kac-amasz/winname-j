package com.kac.winname;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Xdotool {
	static final String CMD = "wid=$(xdotool getwindowfocus) && wpid=$(xdotool getwindowpid $wid) && wcomm=$(cat /proc/$wpid/comm) && wtitle=$(xdotool getwindowname $wid) && wexe=$(readlink -f /proc/$wpid/exe) && echo $wid && echo $wpid && echo $wcomm && echo $wtitle && echo $wexe";

	public static class Res {
		public List<String> out = new ArrayList<String>();
		public List<String> err = new ArrayList<String>();
		public int exit;
		public Exception exception;

		@Override
		public String toString() {
			return "Res [out=" + out + ", err=" + err + ", exit=" + exit + ", exception="
					+ exception + "]";
		}

	}

	public static Res execBashCmd(String cmd) {
		Res res = new Res();
		try {
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec(new String[] { "/bin/bash", "-c", cmd });
			res.exit = process.waitFor();
			BufferedReader buf = new BufferedReader(
					new InputStreamReader(process.getInputStream()));
			String line = "";
			while ((line = buf.readLine()) != null) {
				res.out.add(line);
			}
			buf.close();

			buf = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			while ((line = buf.readLine()) != null) {
				res.err.add(line);
			}
			buf.close();
		} catch (Exception e) {
			res.exception = e;
		}
		return res;
	}

	public static Res getFocusedWindow() {
		return execBashCmd(CMD);
	}

	public static void main(String[] args) {
		long t = System.currentTimeMillis();
		Res r = getFocusedWindow();
		System.out.println(r + " in " + (System.currentTimeMillis() - t));
	}

}
