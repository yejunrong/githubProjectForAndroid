package com.flowcontrol.plugins.setting;

import com.flowcontrol.FCAppController;

public class FCSettingBase implements FCSettingInterface {
	public FCAppController mApp;

	public FCSettingBase(FCAppController controller) {
		mApp = controller;
	}

	public void showView() {

	}
}
