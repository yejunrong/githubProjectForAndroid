package com.yjr.databasedemo;

import android.app.Activity;
import android.os.Bundle;

import com.yjr.databasedemo.db.DBHelper;
import com.yjr.databasedemo.db.DB_Friend;

public class MainActivity extends Activity {
	DBHelper mHelper;
	DB_Friend mFriendTable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mHelper = new DBHelper(this);
		mFriendTable = mHelper.getFriendTable();
	}
}
