package com.yjr.taskdemo.task;

import java.util.HashMap;
import java.util.Map;

public class TaskData {
	private Map<String, Object> extraData = new HashMap<String, Object>();

	public void addData(String k, Object v) {
		extraData.put(k, v);
	}

	public Object getData(String k) {
		return extraData.get(k);
	}
}
