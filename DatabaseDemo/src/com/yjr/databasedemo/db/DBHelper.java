package com.yjr.databasedemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

	private DB_Friend table_friend_;

	public DBHelper(Context context) {
		super(context, "demo.db", null, 1);

		table_friend_ = new DB_Friend("friend", this);

		getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		table_friend_.createTable(db);

		Log.i("", "完成数据库创建");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO 数据库升级更新
	}

	public DB_Friend getFriendTable() {
		return table_friend_;
	}

}
