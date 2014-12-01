package com.flowcontrol.plugins.message;

import android.content.Intent;

import com.flowcontrol.FCAppController;
import com.flowcontrol.plugins.FCPlugin;

public class FCMessageController extends FCPlugin {
	public static final String NAME = "messageController";

	public FCMessageController(FCAppController app) {
		super(app);
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void enable() {
		mApp.startService(new Intent(mApp, FCServiceState_Message.class));
		mApp.startService(new Intent(mApp, FCServiceState_CheckFlow.class));
	}

	@Override
	public void disable() {
	}

}
