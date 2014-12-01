package com.flowcontrol.plugins.context;

import java.util.List;

import com.flowcontrol.FCAppController;
import com.flowcontrol.plugins.FCPlugin;
import com.flowcontrol.plugins.db.FCDBHelper;
import com.flowcontrol.plugins.db.FCDB_Information;
import com.flowcontrol.plugins.db.FCDB_SetInfo;
import com.flowcontrol.plugins.main.bean.MainInformationBean;
import com.flowcontrol.plugins.setting.bean.FCSettingBean;

public class FCLocationContext extends FCPlugin {
	public static final String NAME = "context";
	private FCDBHelper helper_;

	public FCLocationContext(FCAppController app) {
		super(app);
		helper_ = new FCDBHelper(app);
	}

	public String getName() {
		return NAME;
	}

	@Override
	public void enable() {
	}

	@Override
	public void disable() {
		// TODO Auto-generated method stub

	}

	private FCDB_Information getInformationTable() {
		return helper_.getAppInfoTable();
	}

	private FCDB_SetInfo getSetInfoTable() {
		return helper_.getSetInfoTable();
	}

	// app info table
	public List<MainInformationBean> getAllInformation() {
		return getInformationTable().getAllInformation();
	}

	public boolean insertAppInformation(MainInformationBean informationBean) {
		return getInformationTable().insertAppInformation(informationBean);
	}

	public int updateAppInformationLimitFlow(String appName, int appUid, long limitFlow) {
		return getInformationTable().updateAppInformationLimitFlow(appName, appUid, limitFlow);
	}

	public int updateAppInformationUserFlow(MainInformationBean informationBean) {
		return getInformationTable().updateAppInformationUserFlow(informationBean);
	}

	public MainInformationBean getAppInformation(String appName, int appUid) {
		return getInformationTable().getAppInformation(appName, appUid);
	}

	// set info table

	public void updateSettingInfo(Boolean isMessageRemind, Integer outOfFlow, Integer checkFlowMinute) {
		FCSettingBean bean = new FCSettingBean(isMessageRemind, outOfFlow, checkFlowMinute);
		getSetInfoTable().update(bean);
	}

	public FCSettingBean getSettingInfo() {
		return getSetInfoTable().getSettingInfo();
	}
}
