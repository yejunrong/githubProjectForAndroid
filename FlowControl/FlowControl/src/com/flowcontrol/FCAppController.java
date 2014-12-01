package com.flowcontrol;

import java.util.HashMap;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.os.Handler;
import android.view.KeyEvent;

import com.flowcontrol.log_manager.FCLog;
import com.flowcontrol.plugins.FCPlugin;
import com.flowcontrol.plugins.context.FCLocationContext;
import com.flowcontrol.plugins.main.FCMainController;
import com.flowcontrol.plugins.message.FCMessageController;
import com.flowcontrol.plugins.setting.FCSettingControll;
import com.flowcontrol.plugins.welcome.FCWelcomController;

public class FCAppController extends Application {
	protected HashMap<String, FCPlugin> mPlugins = new HashMap<String, FCPlugin>();
	protected Activity mActivity;
	protected Handler mMainHandler;
	public Service mService;
	FCLocationContext locationContext;

	@Override
	public void onCreate() {
		super.onCreate();
		locationContext = new FCLocationContext(this);
		mActivity = null;
		mMainHandler = new Handler();

		registerAll();
		getMessageController().enable();
	}

	public FCAppController() {
		FCLog.i("");
	}

	public FCAppController(Service service) {
		locationContext = new FCLocationContext(this);
		mMainHandler = new Handler();
		mService = service;
		registerAll();
		getMessageController().enable();
	}

	public Activity getActivity() {
		return mActivity;
	}

	public void setActivity(Activity activity) {
		mActivity = activity;
	}

	public Service getService() {
		return mService;
	}

	public Context getAndroidContext() {
		return this.getAndroidContext();
	}

	public Handler getMainHandler() {
		return mMainHandler;
	}

	/**
	 * register all plugins
	 */
	public void registerAll() {
		addPlugin(new FCWelcomController(this));
		addPlugin(new FCMainController(this));
		addPlugin(new FCMessageController(this));
		addPlugin(new FCSettingControll(this));

	}

	public void addPlugin(FCPlugin controller) {
		mPlugins.put(controller.getName(), controller);
	}

	public void removePlugin(String pluginName) {
		mPlugins.remove(pluginName);
	}

	public FCPlugin getPlugin(String name) {
		if (mPlugins.containsKey(name)) {
			return mPlugins.get(name);
		}
		return null;
	}

	public FCSettingControll getSettingControll() {
		return (FCSettingControll) getPlugin(FCSettingControll.NAME);
	}

	public FCMessageController getMessageController() {
		return (FCMessageController) getPlugin(FCMessageController.NAME);
	}

	public FCLocationContext getLocationContext() {
		return locationContext;
	}

	public FCWelcomController getWelcomeController() {
		return (FCWelcomController) getPlugin(FCWelcomController.NAME);
	}

	public FCMainController getMainController() {
		return (FCMainController) getPlugin(FCMainController.NAME);
	}

	public boolean processKey(int keyCode, KeyEvent event) {
		switch (event.getAction()) {
		case KeyEvent.KEYCODE_BACK:
			return true;

		default:
			break;
		}
		return false;

	}
}
