package com.flowcontrol.plugins;

import com.flowcontrol.FCAppController;

public abstract class FCPlugin {

	protected static FCAppController mApp;

	public FCPlugin(FCAppController app) {
		mApp = app;
	}

	public abstract String getName();

	public abstract void enable();

	public abstract void disable();
}
