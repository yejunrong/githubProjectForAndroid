package com.flowcontrol.plugins.setting;

import java.util.ArrayList;
import java.util.List;

import com.flowcontrol.FCAppController;
import com.flowcontrol.plugins.FCPlugin;
import com.flowcontrol.plugins.main.bean.MainInformationBean;
import com.flowcontrol.plugins.setting.state.FCSetting_MainView;
import com.flowcontrol.plugins.setting.state.FCSetting_SoftwareFilter;

public class FCSettingControll extends FCPlugin {
	public static String NAME = "setting";
	FCAppController mApp;
	FCSettingBase mCurrentStateBase;
	public List<MainInformationBean> mInformationBeans = new ArrayList<MainInformationBean>();

	public FCSettingControll(FCAppController app) {
		super(app);
		mApp = app;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void enable() {
		mInformationBeans = mApp.getLocationContext().getAllInformation();
		toSettingMainPage();
	}

	@Override
	public void disable() {

	}

	public void showView() {
		mApp.getMainHandler().post(new Runnable() {
			public void run() {
				mCurrentStateBase.showView();
			}
		});
	}

	public void toSettingMainPage() {
		mInformationBeans = mApp.getLocationContext().getAllInformation();
		mCurrentStateBase = new FCSetting_MainView(mApp);
		showView();
	}

	public void toSettingFilterPage() {
		mCurrentStateBase = new FCSetting_SoftwareFilter(mApp);
		showView();
	}
}
