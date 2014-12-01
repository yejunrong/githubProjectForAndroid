package com.flowcontrol.plugins.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.flowcontrol.plugins.setting.bean.FCSettingBean;

public class FCDB_SetInfo extends FCDBBase {
	public final String COLUMN_Msg_Remind = "msgRemind";
	public final String COLUMN_Use_Flow = "UseFlow";
	public final String COLUMN_Check_Time = "checkTime";

	protected FCDB_SetInfo(String tableName, FCDBHelper helper) {
		super(tableName, helper);
		columns_.add(COLUMN_Msg_Remind + "_TEXT");
		columns_.add(COLUMN_Use_Flow + "_INTEGER");
		columns_.add(COLUMN_Check_Time + "_INTEGER");
	}

	private ContentValues buildContentValues(FCSettingBean info) {
		ContentValues values = new ContentValues();
		if (info.mIsMessageRemind != null) {
			values.put(COLUMN_Msg_Remind, info.mIsMessageRemind.toString());
		}
		if (info.mCheckFlowMinute != null) {
			values.put(COLUMN_Check_Time, info.mCheckFlowMinute);
		}
		if (info.mOverstepFlow != null) {
			values.put(COLUMN_Use_Flow, info.mOverstepFlow);
		}
		return values;
	}

	public void update(FCSettingBean info) {

		SQLiteDatabase sql = helper_.getWritableDatabase();
		ContentValues values = buildContentValues(info);
		sql.update(tableName_, values, null, null);
	}

	public FCSettingBean getSettingInfo() {
		FCSettingBean bean = new FCSettingBean();
		SQLiteDatabase sql = helper_.getWritableDatabase();
		Cursor cursor = sql.query(tableName_, null, null, null, null, null, null);
		while (cursor.moveToNext()) {
			bean = cursorToSettingInfo(cursor);

		}
		return bean;
	}

	private FCSettingBean cursorToSettingInfo(Cursor cursor) {
		boolean msgRemind = Boolean.valueOf(cursor.getString(cursor.getColumnIndex(COLUMN_Msg_Remind)));
		int useFlow = cursor.getInt(cursor.getColumnIndex(COLUMN_Use_Flow));
		int checkTime = cursor.getInt(cursor.getColumnIndex(COLUMN_Check_Time));
		return new FCSettingBean(msgRemind, useFlow, checkTime);
	}

	protected void insertOnlyOne(SQLiteDatabase sql) {
		FCSettingBean bean = new FCSettingBean(true, 0, 0);
		ContentValues values = buildContentValues(bean);
		Cursor cursor = sql.query(tableName_, null, null, null, null, null, null);
		if (cursor.getCount() == 0) {
			sql.insert(tableName_, null, values);
		}
	}
}
