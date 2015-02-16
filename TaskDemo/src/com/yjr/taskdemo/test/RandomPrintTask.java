package com.yjr.taskdemo.test;

import java.util.Random;

import android.util.Log;

import com.yjr.taskdemo.task.BaseTask;
import com.yjr.taskdemo.task.TaskListener;

public class RandomPrintTask extends BaseTask implements TaskListener {
	public RandomPrintTask() {
	}

	@Override
	public void taskRun() {
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			try {

				notifyTaskProgressed(i + 1, 10);

				Log.e("taskRun", String.format("TaskId:[%d], ThreadId:[%d], value:[%d]", getTaskId(), Thread.currentThread().getId(), i));
				Thread.sleep(random.nextInt(1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onTaskStarted(BaseTask task) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTaskProgressed(BaseTask task, int current, int total) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTaskFinished(BaseTask task) {
		// TODO Auto-generated method stub

	}

}
