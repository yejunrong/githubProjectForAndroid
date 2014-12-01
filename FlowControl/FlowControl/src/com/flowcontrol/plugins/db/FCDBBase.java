package com.flowcontrol.plugins.db;

import java.util.ArrayList;

import android.database.sqlite.SQLiteDatabase;

public abstract class FCDBBase {
	FCDBHelper helper_;
	protected ArrayList<String> columns_ = new ArrayList<String>();
	protected String tableName_;

	protected FCDBBase(String tableName, FCDBHelper helper) {
		helper_ = helper;
		tableName_ = tableName;
	}

	protected void CreateTable(SQLiteDatabase sql) {
		sql.execSQL(buildSQL());
	}

	private String buildSQL() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("create table if not exists ");
		buffer.append(tableName_);
		buffer.append("(_id INTEGER PRIMARY KEY AUTOINCREMENT ");

		for (int i = 0; i < columns_.size(); i++) {
			String columnName = columns_.get(i).split("_")[0];
			String columnType = columns_.get(i).split("_")[1];
			buffer.append("," + columnName);
			buffer.append(" " + columnType);
		}
		buffer.append(");");
		return buffer.toString();
	}
}
