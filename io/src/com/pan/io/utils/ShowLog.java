package com.pan.io.utils;

import android.util.Log;

public class ShowLog
{
	private static final boolean DEBUG = true;
	private static final String TAG = "ioStreamTest";
	public static void D(String msg)
	{
		if (DEBUG)
		{
			Log.d(TAG, msg);
		}
	}
	public static void I(String msg)
	{
		if (DEBUG)
		{
			Log.i(TAG, msg);
		}
	}
	public static void W(String msg)
	{
		if (DEBUG)
		{
			Log.w(TAG, msg);
		}
	}
	public static void E(String msg)
	{
		if (DEBUG)
		{
			Log.e(TAG, msg);
		}
	}
	public static void E(String msg,Throwable tr)
	{
		if (DEBUG)
		{
			Log.e(TAG, msg, tr);
		}
	}
}
