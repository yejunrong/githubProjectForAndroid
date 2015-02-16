package com.yjr.taskdemo.task;

public class TaskIdGenerator {
	private long seedTaskId = 0;

	synchronized public long generateNewTaskId() {
		if (seedTaskId == 0) {
			seedTaskId = System.currentTimeMillis();
		}

		return seedTaskId++;
	}

}
