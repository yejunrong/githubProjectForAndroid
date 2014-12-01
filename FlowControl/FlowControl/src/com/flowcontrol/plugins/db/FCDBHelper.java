package com.flowcontrol.plugins.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FCDBHelper extends SQLiteOpenHelper {
	public final static String DB_Name_ = "fc_database.db";
	public final static int db_version_ = 1;
	private FCDB_Information information_table_;
	private FCDB_SetInfo setInfo_;

	public FCDBHelper(Context context) {
		super(context, DB_Name_, null, db_version_);
		information_table_ = new FCDB_Information("app_information_table", this);
		setInfo_ = new FCDB_SetInfo("setting_table", this);

		getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// create table
		information_table_.CreateTable(db);
		setInfo_.CreateTable(db);

		initTableData(db);
	}

	private void initTableData(SQLiteDatabase db) {
		setInfo_.insertOnlyOne(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public FCDB_Information getAppInfoTable() {
		return information_table_;
	}

	public FCDB_SetInfo getSetInfoTable() {
		return setInfo_;
	}
}
