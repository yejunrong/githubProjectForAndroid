package com.yjr.databasedemo;

import android.app.Activity;
import android.os.Bundle;

import com.yjr.databasedemo.db.FFDBHelper;
import com.yjr.databasedemo.db.FFDB_Friend;

public class MainActivity extends Activity {
	FFDBHelper mHelper;
	FFDB_Friend mFriendTable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mHelper = new FFDBHelper(this);
		mFriendTable = mHelper.getFriendTable();
	}
}
