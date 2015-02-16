package com.yjr.taskdemo.task;

import com.yjr.taskdemo.task.thread.ThreadPoolManager;

public class TaskCenter {

	private ThreadPoolManager threadPoolManager;
	private TaskIdGenerator taskIdGenerator = new TaskIdGenerator();
	private int maxThreadCount = 3;

	private static TaskCenter instance = null;

	public static TaskCenter getInstance() {
		if (instance == null) {
			instance = new TaskCenter();
		}

		return instance;
	}

	public void addTask(BaseTask baseTask) {
		if (baseTask == null) {
			return;
		}

		if (threadPoolManager == null) {
			threadPoolManager = new ThreadPoolManager(ThreadPoolManager.TYPE_FIFO, maxThreadCount);
		}

		baseTask.setTaskId(taskIdGenerator.generateNewTaskId());
		threadPoolManager.addAsyncTask(baseTask);
		startNext();
	}

	public void startNext() {
		if (threadPoolManager != null) {
			threadPoolManager.start();
		}
	}

	public void stop() {
		if (threadPoolManager != null) {
			threadPoolManager.stop();
		}
	}
}
