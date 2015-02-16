package com.yjr.taskdemo.task;

import android.util.Log;

import com.yjr.taskdemo.task.thread.ThreadPoolTask;

public abstract class BaseTask extends ThreadPoolTask {
	private int status = TaskConstants.TASK_STATUS_INIT; // 任务状态: 初始化，运行中，结束
	private int errCode = TaskConstants.TASK_RESULT_ERROR_OK; // 错误码
	private long taskId = 0;
	private TaskData taskData;
	private TaskListener taskListener;

	public BaseTask() {
	}

	@Override
	public void finalize() {
		Log.e("taskRun2", String.format("Task [%d] finalize!", getTaskId()));
	}

	public int getStatus() {
		return status;
	}

	public int getErrCode() {
		return errCode;
	}

	public TaskData getTaskData() {
		return taskData;
	}

	public void setTaskData(TaskData taskData) {
		this.taskData = taskData;
	}

	public void setTaskListener(TaskListener taskListener) {
		this.taskListener = taskListener;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	@Override
	final public void run() {
		if (taskListener != null) {
			taskListener.onTaskStarted(this);
		}

		taskRun();

		if (taskListener != null) {
			taskListener.onTaskFinished(this);
		}
	}

	public void notifyTaskProgressed(int current, int total) {
		if (taskListener != null) {
			taskListener.onTaskProgressed(this, current, total);
		}
	}

	abstract public void taskRun();
}
