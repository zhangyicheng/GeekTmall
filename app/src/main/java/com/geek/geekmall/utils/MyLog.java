package com.geek.geekmall.utils;

import android.util.Log;

public class MyLog {
	private static final String LOG_TAG ="geek";
	public static boolean DEBUG = true;

	public static final void debug(Class paramClass, String paramString) {
		if (DEBUG) {
			Log.d(LOG_TAG, ">>>>>>>>>>>" + paramClass.getSimpleName() + " "
					+ paramString);
		}
	}

	public static final void error(Class paramClass, String paramString) {
		if (DEBUG) {
			Log.e(LOG_TAG, ">>>>>>>>>>>" + paramClass.getSimpleName() + " "
					+ paramString);
		}
	}

	public static final void error(Class paramClass, String paramString,
			Throwable paramThrowable) {
		if (DEBUG) {
			Log.e(LOG_TAG, ">>>>>>>>>>>" + paramClass.getSimpleName() + " "
					+ paramString, paramThrowable);
		}
	}

	public static final void info(Class paramClass, String paramString) {
		if (DEBUG) {
			Log.i(LOG_TAG, ">>>>>>>>>>>" + paramClass.getSimpleName() + " "
					+ paramString);
		}
	}

	public static final void info(String paramString1, String paramString2) {
		if (DEBUG) {
			Log.i(paramString1, paramString2);
		}
	}
}
