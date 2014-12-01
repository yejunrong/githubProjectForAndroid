package com.flowcontrol.plugins.message;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.flowcontrol.FCAppController;
import com.flowcontrol.log_manager.FCLog;
import com.flowcontrol.plugins.context.FCLocationContext;

public class FCServiceState_Message extends Service {

	FCAppController mAppController;
	FCLocationContext mContext;

	@Override
	public IBinder onBind(Intent intent) {
		FCLog.i("FCMessageService  onBind");
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		FCLog.i("FCMessageService  onCreate");

		mAppController = (FCAppController) this.getApplication();
		mAppController.mService = this;
		mContext = mAppController.getLocationContext();
		mContext.enable();

	}

}
