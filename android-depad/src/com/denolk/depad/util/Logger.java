package com.denolk.depad.util;

import android.util.Log;

public class Logger {

	public static void e(Exception e) {
		String tag = e.getStackTrace()[1].getClassName() + " @ " + e.getStackTrace()[1].getMethodName();
		Log.e(tag, e.toString());
	}

	public static void i(String s) {
		String TAG = Thread.currentThread().getStackTrace()[3].getClassName() + " @ " + Thread.currentThread().getStackTrace()[3].getMethodName();
		Log.d(TAG, s);
	}

	public static void i(String format, Object... objects) {
		String TAG = Thread.currentThread().getStackTrace()[3].getClassName() + " @ " + Thread.currentThread().getStackTrace()[3].getMethodName();
		Log.d(TAG, String.format(format, objects));
	}

}
