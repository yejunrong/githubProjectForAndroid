package com.yjr.databasedemo.db;

import java.util.ArrayList;

import android.database.sqlite.SQLiteDatabase;

public abstract class DBBase {

	protected String tableName_;
	protected DBHelper helper_;

	protected ArrayList<String> fields_ = new ArrayList<String>();

	public DBBase(String tableName, DBHelper helper) {

		tableName_ = tableName;
		helper_ = helper;
	}

	public void createTable(SQLiteDatabase sql) {
		sql.execSQL(buildSQL());
	}

	// create table
	protected String buildSQL() {

		StringBuffer sb = new StringBuffer();

		sb.append("create table if not exists ");
		sb.append(tableName_);
		sb.append("(_id INTEGER PRIMARY KEY AUTOINCREMENT");

		for (int i = 0; i < fields_.size(); i++) {

			String fieldString = fields_.get(i);
			String[] fieldStrings = fieldString.split(":");

			sb.append(",");
			sb.append(fieldStrings[0]);
			sb.append(" ");
			sb.append(fieldStrings[1]);
		}

		sb.append(");");

		return sb.toString();
	}
}
