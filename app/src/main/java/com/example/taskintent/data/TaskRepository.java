package com.example.taskintent.data;

import android.util.Log;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class TaskRepository {

    private static TaskRepository sTodoRepository;

    private final ArrayList<Task> mTasks = new ArrayList<>();

    public static TaskRepository getInstance() {
        if (sTodoRepository == null) {
            sTodoRepository = new TaskRepository();
        }
        return sTodoRepository;
    }

    private TaskRepository(){
        initTestData();
    }

    private void initTestData() {

        for (int i=0; i < 5; i++){
            Task task = new Task(i,"Test title " + i,"Test description " + i,
                    "To be set!");
            mTasks.add(task);
        }
    }

    public Task getNextTask(Task task) {

        int currentTaskId = task.getId();
        int nextTaskId = 0;

        if (currentTaskId < mTasks.size() - 1){
            nextTaskId = currentTaskId + 1;
        } else  if (currentTaskId == mTasks.size() - 1){
            nextTaskId = currentTaskId;
        } else {
            Log.d(TAG, "getNextTask: currentTaskId out of bound, reset to 0!");
        }

        return mTasks.get(nextTaskId);

    }

    public Task getPrevTask(Task task) {

        int currentTaskId = task.getId();
        int nextTaskId = 0;

        if (currentTaskId < mTasks.size() - 1){
            nextTaskId = currentTaskId - 1; // instead of doing +1 we -1
        } else  if (currentTaskId == mTasks.size() - 1) {
            nextTaskId = currentTaskId;
        } else {
            Log.d(TAG, "getPrevTask: currentTaskId out of bound, reset to 0!");
            currentTaskId--;
        }

        return mTasks.get(nextTaskId);

    }

    public boolean isLast(Task mTask) {
        return mTask.getId() == mTasks.size() - 1;
    }

    public boolean isFirst(Task mTask) {
        return mTask.getId() == 0;
    }

    public Task getFirstTask() {
        return mTasks.get(0);
    }

    public Task getTask(int taskId) {
        return mTasks.get(taskId);
    }
}
