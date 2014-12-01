package com.flowcontrol.plugins.message;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.TrafficStats;

import com.flowcontrol.FCAppController;
import com.flowcontrol.help.FFAppHelp;
import com.flowcontrol.log_manager.FCLog;
import com.flowcontrol.plugins.main.bean.MainInformationBean;

/**
 * 
 * 
 * @author yejunrong
 * 
 */
public class FCBootCompletedReceiver extends BroadcastReceiver {

	public FCBootCompletedReceiver() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		FCAppController mApp = (FCAppController) context.getApplicationContext();
		FCLog.i("FCBootCompletedReceiver  ");

		if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {// 开机启动广播
			FCLog.i("FCBootCompletedReceiver 开机启动广播=========");

			Intent serviceIntent = new Intent(context, FCServiceState_Message.class);
			context.startService(serviceIntent);

			serviceIntent = new Intent(context, FCServiceState_CheckFlow.class);
			context.startService(serviceIntent);

		} else if (Intent.ACTION_SHUTDOWN.equals(intent.getAction())) {// 关机广播
			FCLog.i("FCBootCompletedReceiver 关机广播========");
			List<PackageInfo> packs = context.getPackageManager().getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);

			for (int i = 0; i < packs.size(); i++) {
				PackageInfo p = packs.get(i);
				if ((p.versionName == null) || !FFAppHelp.filterApp(p.applicationInfo)) {
					continue;
				}

				String appname = p.applicationInfo.loadLabel(context.getPackageManager()).toString();
				// String pname = p.packageName;
				// String versionName = p.versionName;
				// int versionCode = p.versionCode;
				int uid = p.applicationInfo.uid;

				// 如果返回-1，代表不支持使用该方法，注意必须是2.2以上的
				long rx = TrafficStats.getUidRxBytes(uid);
				// 如果返回-1，代表不支持使用该方法，注意必须是2.2以上的
				long tx = TrafficStats.getUidTxBytes(uid);
				long userFlow = 0;
				if ((rx + tx) > 0) {
					userFlow = rx + tx;
				}

				MainInformationBean databaseBean = mApp.getLocationContext().getAppInformation(appname, uid);
				databaseBean.mUserFlow = userFlow + databaseBean.mUserFlow;
				int updateResult = mApp.getLocationContext().updateAppInformationUserFlow(databaseBean);
				FCLog.i("updateResult=" + updateResult);
				FCLog.i("手机即将关闭，需要 update 流量 appname=" + appname + " uid=" + uid + "  userFlow=" + userFlow + "  databaseBean.mUserFlow="
						+ databaseBean.mUserFlow);
			}

		}
	}

}
