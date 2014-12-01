package com.flowcontrol.log_manager;

import android.util.Log;

public final class FCLog {
	private static String tag = "FloWControl";

	public static void i(String msg) {
		Log.i(tag, msg);
	}

	public static void e(String msg) {
		Log.e(tag, msg);
	}

	public static void d(String msg) {
		Log.d(tag, msg);
	}

	public static void w(String msg) {
		Log.w(tag, msg);
	}
}
