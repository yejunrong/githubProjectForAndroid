package com.flowcontrol.plugins.main.bean;

public class MainInformationBean {
	public String mAppName; // 应用名称
	public boolean mIsNotClean;// 是否需要清除（也就是是否设置软件过滤），默认都是要清除的(mYES)，除非软件过滤了
	public String mPackageName;// 软件包名
	public String mVersionName;// 应用版本名称
	public int mVersionCode;// 应用版本号
	public int mUid;// 应用id
	public long mUserFlow;// 已经使用的流量
	public int mLimitUserFlow;// 限制使用多少流量，（默认是0:就是不受限制）

	public MainInformationBean(String appName, boolean isNotClean, String packageName, String versionName, int versionCode, int uid, long userFlow,
			int limitUserFlow) {
		mIsNotClean = isNotClean;
		mAppName = appName;
		mPackageName = packageName;
		mVersionName = versionName;
		mVersionCode = versionCode;
		mUid = uid;
		mUserFlow = userFlow;
		mLimitUserFlow = limitUserFlow;
	}

	public MainInformationBean() {
	};

}
