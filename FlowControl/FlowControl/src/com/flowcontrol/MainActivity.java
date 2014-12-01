package com.flowcontrol;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

public class MainActivity extends Activity {
	FCAppController mApp;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mApp = (FCAppController) this.getApplication();
		mApp.setActivity(this);
		mApp.getWelcomeController().enable();
		this.getActionBar().hide();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		return mApp.processKey(keyCode, event);
	}

}
