package com.flowcontrol.plugins.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.flowcontrol.plugins.main.bean.MainInformationBean;

public class FCDB_Information extends FCDBBase {
	private final String COLUMN_AppName = "appName"; // 应用名称
	private final String COLUMN_Uid = "uid";// 应用id
	private final String COLUMN_IsNotClean = "isNotClean";// 是否需要清除（也就是是否设置软件过滤），默认都是要清除的(mYES)，除非软件过滤了
	private final String COLUMN_PackageName = "packageName";// 软件包名
	private final String COLUMN_VersionName = "versionName";// 应用版本名称
	private final String COLUMN_VersionCode = "versionCode";// 应用版本号
	private final String COLUMN_LimitUserFlow = "limitUserFlow";// 限制使用多少流量，（默认是0:就是不受限制）
	private final String COLUMN_UserFlow = "userFlow";// 已经使用的流量

	protected FCDB_Information(String tableName, FCDBHelper helper) {
		super(tableName, helper);
		columns_.add(COLUMN_AppName + "_TEXT");
		columns_.add(COLUMN_Uid + "_TEXT");
		columns_.add(COLUMN_IsNotClean + "_TEXT");
		columns_.add(COLUMN_PackageName + "_TEXT");
		columns_.add(COLUMN_VersionName + "_TEXT");
		columns_.add(COLUMN_VersionCode + "_TEXT");
		columns_.add(COLUMN_LimitUserFlow + "_TEXT");
		columns_.add(COLUMN_UserFlow + "_TEXT");
	}

	public boolean insertAppInformation(MainInformationBean informationBean) {
		SQLiteDatabase sql = helper_.getWritableDatabase();
		ContentValues values = buildContentValues(informationBean);

		Cursor cursor = sql.query(tableName_, null, COLUMN_AppName + "=? and " + COLUMN_Uid + "=? ", new String[] { informationBean.mAppName,
				informationBean.mUid + "" }, null, null, null);
		boolean isExist = false;
		while (cursor.moveToNext()) {
			isExist = true;
		}

		if (!isExist) {
			sql.insert(tableName_, null, values);
		}
		cursor.close();
		return !isExist;
	}

	/**
	 * 获取所有的App 信息
	 * 
	 * @param helper
	 * @return
	 */
	public List<MainInformationBean> getAllInformation() {
		List<MainInformationBean> returnBeans = new ArrayList<MainInformationBean>();
		SQLiteDatabase sqLiteDatabase = helper_.getReadableDatabase();
		Cursor cursor = sqLiteDatabase.query(tableName_, null, null, null, null, null, null);
		while (cursor.moveToNext()) {

			returnBeans.add(getBeanFromCursor(cursor));
		}

		return returnBeans;
	}

	/**
	 * 更新App 的限制流量
	 * 
	 * @param appName
	 * @param appUid
	 * @param limitFlow
	 * @param helper
	 * @return
	 */
	public int updateAppInformationLimitFlow(String appName, int appUid, long limitFlow) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(COLUMN_LimitUserFlow, limitFlow + "");
		SQLiteDatabase sqLiteDatabase = helper_.getWritableDatabase();

		return sqLiteDatabase.update(tableName_, contentValues, COLUMN_AppName + "=? and " + COLUMN_Uid + "=? ",
				new String[] { appName, appUid + "" });

	}

	/** 更新App信息 */
	public int updateAppInformationUserFlow(MainInformationBean informationBean) {
		ContentValues values = buildContentValues(informationBean);
		SQLiteDatabase sqLiteDatabase = helper_.getWritableDatabase();

		return sqLiteDatabase.update(tableName_, values, COLUMN_AppName + "=? and " + COLUMN_Uid + "=? ", new String[] { informationBean.mAppName,
				informationBean.mUid + "" });

	}

	/**
	 * 根据app名称和appUID 获取该APP
	 * 
	 * @param appName
	 * @param appUid
	 * @return
	 */
	public MainInformationBean getAppInformation(String appName, int appUid) {
		MainInformationBean bean = null;
		SQLiteDatabase sqLiteDatabase = helper_.getWritableDatabase();

		Cursor cursor = sqLiteDatabase.query(tableName_, null, COLUMN_AppName + "=? and " + COLUMN_Uid + "=? ",
				new String[] { appName, appUid + "" }, null, null, null);
		while (cursor.moveToNext()) {
			bean = getBeanFromCursor(cursor);
		}
		return bean;
	}

	private MainInformationBean getBeanFromCursor(Cursor cursor) {
		MainInformationBean bean = new MainInformationBean();

		bean.mAppName = cursor.getString(cursor.getColumnIndex(COLUMN_AppName));
		bean.mIsNotClean = Boolean.valueOf(cursor.getString(cursor.getColumnIndex(COLUMN_IsNotClean)));
		bean.mPackageName = cursor.getString(cursor.getColumnIndex(COLUMN_PackageName));
		bean.mVersionCode = Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_VersionCode)));
		bean.mVersionName = cursor.getString(cursor.getColumnIndex(COLUMN_VersionName));
		bean.mUserFlow = Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_UserFlow)));
		bean.mLimitUserFlow = Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_LimitUserFlow)));
		bean.mUid = Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_Uid)));

		return bean;
	}

	private ContentValues buildContentValues(MainInformationBean informationBean) {
		ContentValues values = new ContentValues();
		if (informationBean.mAppName != null) {
			values.put(COLUMN_AppName, informationBean.mAppName);
		}
		values.put(COLUMN_IsNotClean, Boolean.toString(informationBean.mIsNotClean));
		if (informationBean.mPackageName != null) {
			values.put(COLUMN_PackageName, informationBean.mPackageName);
		}
		values.put(COLUMN_Uid, informationBean.mUid + "");
		values.put(COLUMN_VersionCode, informationBean.mVersionCode);
		if (informationBean.mVersionName != null) {
			values.put(COLUMN_VersionName, informationBean.mVersionName);
		}
		values.put(COLUMN_UserFlow, informationBean.mUserFlow + "");
		values.put(COLUMN_LimitUserFlow, informationBean.mLimitUserFlow + "");

		return values;
	}

}
