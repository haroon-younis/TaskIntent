package com.example.taskintent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskintent.data.Task;
import com.example.taskintent.data.TaskRepository;

public class TaskActivity extends AppCompatActivity {

    private Task mTask = new Task(0, "title", "description", "status");
    private final TaskRepository sTaskRepository = TaskRepository.getInstance();

    private static final String TASK_ID = "com.example.taskintent";
    private static final String LOG_TAG = TaskActivity.class.getSimpleName();

    /** In case of state change, such as rotating the phone
     * store the taskId to display the same task element after the state change
     * N.B. small amounts of data, typically IDs can be stored as key, value pairs in a Bundle
     * the alternative is to abstract view data to a ViewModel which can be in scope in all
     * Activity states and more suitable for larger amounts of data */
    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        Log.d( LOG_TAG, "In onSaveInstanceState saving taskId ... ");
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(TASK_ID, mTask.getId()); // saving the Task ID
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d( LOG_TAG, "onCreate ... ");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        /* check for saved state due to changes such as rotation or back button
           and restore any saved state such as the taskId and retrieve the corresponding task */
        if (savedInstanceState != null){
            int taskId = savedInstanceState.getInt(TASK_ID, 0);
            mTask = sTaskRepository.getTask(taskId);
        } else {
            mTask = sTaskRepository.getFirstTask();
        }

        updateUI();

        Button buttonPrev = findViewById(R.id.buttonPrev);
        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // insert logic here
                if ( sTaskRepository.isFirst(mTask)) {
                    Toast toast = Toast.makeText(
                            getApplicationContext(),
                            "First!",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                } else {
                    mTask = sTaskRepository.getPrevTask(mTask);
                    updateUI();
                }

            }

        });

        Button buttonNext = findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if ( sTaskRepository.isLast(mTask)) {

                    Toast toast = Toast.makeText(
                            getApplicationContext(),
                            "Hurray! end of tasks, time to plant trees and read poetry!",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                } else {
                    mTask = sTaskRepository.getNextTask(mTask);
                    updateUI();
                }
            }
        });

    }

    private void updateUI() {

        // Task title
        TextView textViewTaskTitle = findViewById(R.id.textViewTaskTitle);
        textViewTaskTitle.setText(mTask.getTitle());

        // Task description
        TextView textViewTaskDescription = findViewById(R.id.textViewDescription);
        textViewTaskDescription.setText(mTask.getDescription());

        // Task status
        TextView textViewTaskStatus = findViewById(R.id.textViewStatus);
        textViewTaskStatus.setText(mTask.getStatus());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(LOG_TAG, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }

}
