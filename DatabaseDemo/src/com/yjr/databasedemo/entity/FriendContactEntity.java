package com.yjr.databasedemo.entity;

import android.graphics.Bitmap;

public class FriendContactEntity {

	public String friendId;
	public String uid;
	public String friendName;
	public ESex sex;// 姓名
	public Bitmap large;// 大头像
	public Bitmap small;// 小头像

	public String avatar;

	public Long first_meet_time;// 第一次见面时间
	public Double first_meet_latitude;// 首次见面的纬度
	public Double first_meet_longitude;// 首次见面的经度

	public FriendContactEntity() {

	}

	/** 性别类型，男、女、未知 */
	public enum ESex {
		kMan, kFemale, kUnknown
	}
}
