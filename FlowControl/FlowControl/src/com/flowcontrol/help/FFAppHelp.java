package com.flowcontrol.help;

import android.content.pm.ApplicationInfo;
import android.net.TrafficStats;

public class FFAppHelp {
	public static boolean filterApp(ApplicationInfo info) {
		// 有些系统应用是可以更新的，如果用户自己下载了一个系统的应用来更新了原来的，它还是系统应用，这个就是判断这种情况的
		if ((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
			return true;
		} else if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {// 判断是不是系统应用
			return true;
		}
		return false;
	}

	public static long getUserFlow(int uid) {
		// 如果返回-1，代表不支持使用该方法，注意必须是2.2以上的
		long rx = TrafficStats.getUidRxBytes(uid);
		// 如果返回-1，代表不支持使用该方法，注意必须是2.2以上的
		long tx = TrafficStats.getUidTxBytes(uid);
		long userFlow = 0;
		if ((rx + tx) > 0) {
			userFlow = rx + tx;
		}
		return userFlow;
	}
}
