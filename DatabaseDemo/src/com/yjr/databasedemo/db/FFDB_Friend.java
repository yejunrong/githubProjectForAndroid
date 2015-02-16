package com.yjr.databasedemo.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.yjr.databasedemo.entity.FriendContactEntity;

public class FFDB_Friend extends FFDBBase {

	// column name
	public final static String COLUMN_FriendId = "uuid"; // 好友id
	public final static String COLUMN_friendName = "friendName"; // 名字
	public final static String COLUMN_UserId = "userId"; // 用户id
	public final static String COLUMN_Sex = "sex"; // 性别
	public final static String COLUMN_avatar = "avatar";
	public final static String COLUMN_LargePhoto = "large_photo";
	public final static String COLUMN_SmallPhoto = "small_photo";
	public final static String COLUMN_HP = "hp"; // 当该值为0时，这个好友会被干掉
	public final static String COLUMN_first_meet_time = "first_meet_time";
	public final static String COLUMN_first_meet_latitude = "first_meet_latitude";
	public final static String COLUMN_first_meet_longitude = "first_meet_longitude";

	protected FFDB_Friend(String tableName, FFDBHelper helper) {
		super(tableName, helper);

		fields_.add(COLUMN_FriendId + ":TEXT");
		fields_.add(COLUMN_UserId + ":TEXT");
		fields_.add(COLUMN_friendName + ":TEXT");
		fields_.add(COLUMN_Sex + ":TEXT");
		fields_.add(COLUMN_avatar + ":TEXT");
		fields_.add(COLUMN_LargePhoto + ":BLOB");
		fields_.add(COLUMN_SmallPhoto + ":BLOB");

		fields_.add(COLUMN_HP + ":INTEGER");

		fields_.add(COLUMN_first_meet_time + ":INTEGER");
		fields_.add(COLUMN_first_meet_latitude + ":double");
		fields_.add(COLUMN_first_meet_longitude + ":double");
	}

	protected void insert(FriendContactEntity info) {

		SQLiteDatabase sql = helper_.getWritableDatabase();

		ContentValues values = _buildValues(info);
		String selection = COLUMN_FriendId + "=?  ";
		String[] selectionArgs = new String[] { info.friendId };
		Cursor cursor = sql.query(tableName_, null, selection, selectionArgs, null, null, null);

		long ret = -1;
		if (cursor.moveToFirst()) {
			ret = sql.update(tableName_, values, selection, selectionArgs);
		} else {
			ret = sql.insert(tableName_, null, values);
		}

		if (ret == -1) {
			Log.e(tableName_, " insert failed");
		} else {
			Log.w(tableName_, " save success");
		}
	}

	protected void delete(String friendId) {
		SQLiteDatabase sql = helper_.getWritableDatabase();
		sql.delete(tableName_, COLUMN_FriendId + "=? ", new String[] { friendId });
		Log.i(" delete ", " " + tableName_ + " friendId=" + friendId);
	}

	protected void removeAll() {
		SQLiteDatabase sql = helper_.getWritableDatabase();
		sql.delete(tableName_, null, null);
		Log.i(" delete ", "" + tableName_ + " success");
	}

	protected void setFriendNearDeath(String uid) {

		SQLiteDatabase database = helper_.getWritableDatabase();

		String whereClause = String.format("%1$s=?", COLUMN_FriendId);
		String[] whereArgs = new String[] { uid };

		ContentValues values = new ContentValues();
		values.put(COLUMN_HP, 0);

		int ret = database.update(tableName_, values, whereClause, whereArgs);

		if (ret >= 0) {
			Log.i(">>", " setFriendNearDeath success");
		} else {
			Log.i(">>", " setFriendNearDeath failed");
		}
	}

	/**
	 * 根据用户的id获取到所有的好友信息
	 * 
	 * @param helper
	 * @param userId
	 *            自己的id
	 * @return
	 */
	protected ArrayList<FriendContactEntity> getAllFriendInfoByUserId(String userId) {
		SQLiteDatabase sql = helper_.getWritableDatabase();

		String whereClause = String.format("%1$s=? AND %2$s=?", COLUMN_UserId, COLUMN_HP);
		String[] whereArgs = new String[] { userId, "1" };
		Cursor cursor = sql.query(tableName_, null, whereClause, whereArgs, null, null, null);

		ArrayList<FriendContactEntity> resultArrayList = new ArrayList<FriendContactEntity>();

		while (cursor.moveToNext()) {
			resultArrayList.add(_buildEntity(cursor));
		}

		return resultArrayList;
	}

	protected FriendContactEntity getFriend(String friendId) {

		SQLiteDatabase sql = helper_.getWritableDatabase();

		String whereClause = String.format("%1$s=?", COLUMN_FriendId);
		String[] whereArgs = new String[] { friendId };
		Cursor cursor = sql.query(tableName_, null, whereClause, whereArgs, null, null, null);

		FriendContactEntity result = null;

		if (cursor.moveToFirst()) {
			result = _buildEntity(cursor);
		}

		return result;
	}

	private FriendContactEntity _buildEntity(Cursor cursor) {

		FriendContactEntity data_of_friend = new FriendContactEntity();
		data_of_friend.friendId = cursor.getString(cursor.getColumnIndex(COLUMN_FriendId));
		data_of_friend.uid = cursor.getString(cursor.getColumnIndex(COLUMN_UserId));
		data_of_friend.friendName = cursor.getString(cursor.getColumnIndex(COLUMN_friendName));
		// data_of_friend.sex = ESex.valueOf(cursor.getString(cursor.getColumnIndex(COLUMN_Sex)));
		data_of_friend.avatar = cursor.getString(cursor.getColumnIndex(COLUMN_avatar));

		data_of_friend.first_meet_time = cursor.getLong(cursor.getColumnIndex(COLUMN_first_meet_time));
		data_of_friend.first_meet_latitude = cursor.getDouble(cursor.getColumnIndex(COLUMN_first_meet_latitude));
		data_of_friend.first_meet_longitude = cursor.getDouble(cursor.getColumnIndex(COLUMN_first_meet_longitude));

		return data_of_friend;
	}

	private ContentValues _buildValues(FriendContactEntity info) {

		ContentValues values = new ContentValues();

		if (info.friendId != null) {
			values.put(COLUMN_FriendId, info.friendId);
		}

		if (info.uid != null) {
			values.put(COLUMN_UserId, info.uid);
		}

		if (info.friendName != null) {
			values.put(COLUMN_friendName, info.friendName);
		}

		// if (info.sex != null) {
		// values.put(COLUMN_Sex, info.sex.toString());
		// }

		if (info.avatar != null) {
			values.put(COLUMN_avatar, info.avatar);
		}

		if (info.first_meet_time != null) {
			values.put(COLUMN_first_meet_time, info.first_meet_time);
		}

		if (info.first_meet_latitude != null) {
			values.put(COLUMN_first_meet_latitude, info.first_meet_latitude);
		}

		if (info.first_meet_longitude != null) {
			values.put(COLUMN_first_meet_longitude, info.first_meet_longitude);
		}

		values.put(COLUMN_HP, 1);

		return values;
	}
}
