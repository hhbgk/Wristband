package com.hhbgk.wristband.util;

import android.util.Log;

public class Dbug {
	private static boolean IS_DEBUG = true;

	public static void openOrCloseDebug(boolean isOpen) {
		IS_DEBUG = isOpen;
	}

	public static void v(String tag, String msg) {
		if(IS_DEBUG){
			Log.v(tag, msg);
		}
	}
	public static void d(String tag, String msg) {
		if(IS_DEBUG){
			Log.d(tag, msg);
		}
	}
	public static void i(String tag, String msg) {
		if(IS_DEBUG){
			Log.i(tag, msg);
		}
	}
	public static void w(String tag, String msg) {
		if(IS_DEBUG){
			Log.w(tag, msg);
		}
	}
	public static void e(String tag, String msg) {
		if(IS_DEBUG){
			Log.e(tag, msg);
		}
	}
}

