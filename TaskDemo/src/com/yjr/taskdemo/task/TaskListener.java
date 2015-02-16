package com.yjr.taskdemo.task;

public interface TaskListener {
	public void onTaskStarted(BaseTask task);

	// 在taskRun中自行调用
	public void onTaskProgressed(BaseTask task, int current, int total);

	public void onTaskFinished(BaseTask task);
}
