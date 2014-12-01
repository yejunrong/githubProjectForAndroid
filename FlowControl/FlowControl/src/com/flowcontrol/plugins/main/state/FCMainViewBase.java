package com.flowcontrol.plugins.main.state;

import com.flowcontrol.FCAppController;
import com.flowcontrol.plugins.main.FCMainController;

public abstract class FCMainViewBase implements FCMainViewInterface {

	protected FCAppController mApp;
	protected FCMainController mMainController;

	public FCMainViewBase(FCAppController controller) {
		mMainController = controller.getMainController();
		mApp = controller;
	}

	public void refreshView() {
	}
}
