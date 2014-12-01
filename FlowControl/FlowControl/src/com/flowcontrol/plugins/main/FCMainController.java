package com.flowcontrol.plugins.main;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.TrafficStats;

import com.flowcontrol.FCAppController;
import com.flowcontrol.R;
import com.flowcontrol.help.FFAppHelp;
import com.flowcontrol.plugins.FCPlugin;
import com.flowcontrol.plugins.main.bean.MainInformationBean;
import com.flowcontrol.plugins.main.state.FCMainViewBase;
import com.flowcontrol.plugins.main.state.FCMainView_AppList;
import com.flowcontrol.plugins.main.state.FCMainView_AppListDelete;
import com.flowcontrol.plugins.main.state.FCMainView_Detail;
import com.flowcontrol.plugins.main.ui.MainListAdapter;

public class FCMainController extends FCPlugin {
	public static final String NAME = "main";
	public List<MainInformationBean> mInformationBeans = new ArrayList<MainInformationBean>();
	FCMainViewBase mMainView;
	public boolean mMainListChang = false;
	public MainListAdapter mMainListAdapter;
	protected ProgressDialog dialog_;

	public FCMainController(FCAppController app) {
		super(app);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void enable() {
		dialog_ = new ProgressDialog(mApp.getActivity());
		dialog_.setCancelable(false);
		dialog_.setMessage(mApp.getResources().getString(R.string.loading));

		mInformationBeans = mApp.getLocationContext().getAllInformation();
		if (mInformationBeans.size() == 0) {
			showProgressDialog();
			mApp.getMainController().getAppAllthread.start();
		}
		mMainView = new FCMainView_AppList(mApp);
		showView();
	}

	public void showView() {

		mApp.getMainHandler().post(new Runnable() {
			public void run() {
				mMainView.showView();
			}
		});
	}

	@Override
	public void disable() {
		// TODO Auto-generated method stub

	}

	public void changToDelete() {
		mMainView = new FCMainView_AppListDelete(mApp);
		showView();
	}

	public void changToDetail(MainInformationBean bean) {
		if (mMainView.getClass() == FCMainView_AppList.class) {
			mMainView = new FCMainView_Detail(mApp, bean);

			showView();
		}
	}

	public void changToMainList() {
		if (mMainView.getClass() == FCMainView_Detail.class) {
			mMainView = new FCMainView_AppList(mApp);

			mApp.getMainHandler().post(new Runnable() {
				public void run() {
					mApp.getActivity().setContentView(R.layout.app_list);
					mMainView.showView();
				}
			});
		}
	}

	protected void showProgressDialog() {
		if (!dialog_.isShowing()) {
			dialog_.show();
		}
	}

	protected void dismissProgressDialog() {
		if (dialog_.isShowing()) {
			dialog_.dismiss();
		}
	}

	Thread getAppAllthread = new Thread(new Runnable() {
		public void run() {
			getInstalledApps();
		}
	});

	/**
	 * 获取系统所有已安装的APP信息
	 * 
	 * @param getSysPackages
	 * @return
	 */
	public ArrayList<MainInformationBean> getInstalledApps() {
		ArrayList<MainInformationBean> beans = new ArrayList<MainInformationBean>();
		List<PackageInfo> packs = mApp.getActivity().getPackageManager().getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);

		for (int i = 0; i < packs.size(); i++) {
			PackageInfo p = packs.get(i);
			if ((p.versionName == null) || !FFAppHelp.filterApp(p.applicationInfo)) {
				continue;
			}

			String appname = p.applicationInfo.loadLabel(mApp.getActivity().getPackageManager()).toString();
			String pname = p.packageName;
			String versionName = p.versionName;
			int versionCode = p.versionCode;
			int uid = p.applicationInfo.uid;

			long rx = TrafficStats.getUidRxBytes(uid);
			long tx = TrafficStats.getUidTxBytes(uid);
			long userFlow = 0;
			if ((rx + tx) > 0) {
				userFlow = rx + tx;
			}

			MainInformationBean newInfo = new MainInformationBean(appname, true, pname, versionName, versionCode, uid, userFlow, 0);

			boolean insertResult = mApp.getLocationContext().insertAppInformation(newInfo);
			if (!insertResult) {// 说明不是新增的
				MainInformationBean databaseBean = mApp.getLocationContext().getAppInformation(appname, uid);
				newInfo.mUserFlow = userFlow + databaseBean.mUserFlow;
				newInfo.mLimitUserFlow = databaseBean.mLimitUserFlow;
			}

			beans.add(newInfo);
		}

		mInformationBeans = beans;

		dismissProgressDialog();
		mMainView.refreshView();
		getAppAllthread.interrupt();
		return beans;
	}

}
