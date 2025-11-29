package com.example.tripplanningapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TasksActivity extends AppCompatActivity implements TaskAdapter.OnTaskDeleteListener {
    private RecyclerView tasksRecycleView;
    private Button BackButton;
    private TaskAdapter taskAdapter;
    private SharedPrefHelper prefHelper;
    private List<Task> displayTasks;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tasks);
        prefHelper = new SharedPrefHelper(this);
        tasksRecycleView = findViewById(R.id.tasksRecyclerView);
        BackButton = findViewById(R.id.BackButton);
        BackButton.setOnClickListener(v -> finish());


        setUpRecycleView();
        loadTasksFromIntent();

    }


    private void setUpRecycleView(){
        tasksRecycleView.setLayoutManager(new LinearLayoutManager(this));
        displayTasks = new ArrayList<>();
        taskAdapter = new TaskAdapter( displayTasks);
        taskAdapter.setOnTaskDeleteListener(this);
        tasksRecycleView.setAdapter(taskAdapter);
    }

    private void loadTasksFromIntent(){
        Intent intent = getIntent();
        boolean isFiltered = intent.getBooleanExtra("filtered", false);

        if (isFiltered) {
            ArrayList<String> taskIds = intent.getStringArrayListExtra("taskIds");

            if (taskIds != null && !taskIds.isEmpty()) {

                Set<String> idSet = new HashSet<>(taskIds);
                List<Task> allTasks = prefHelper.loadTasks();

                displayTasks.clear();

                for (Task t : allTasks) {
                    if (idSet.contains(t.getId())) {
                        displayTasks.add(t);
                    }
                }

                taskAdapter.notifyDataSetChanged();

                Toast.makeText(this, "Found " + displayTasks.size() + " task(s)", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // no filter show everything
        loadAllTasks();
    }


    private void loadAllTasks() {
        displayTasks.clear();
        displayTasks.addAll(prefHelper.loadTasks());
        taskAdapter.notifyDataSetChanged();

        if (displayTasks.isEmpty()) {
            Toast.makeText(this, "No tasks found. Add some tasks!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Showing " + displayTasks.size() + " task(s)", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean isFiltered = getIntent().getBooleanExtra("filtered",false);
        if(!isFiltered){
            loadAllTasks();
        }
    }




    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tasks Count",displayTasks.size());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onTaskDelete(Task task) {
        displayTasks.remove(task);
        prefHelper.deleteTask(task.getId());

        taskAdapter.notifyDataSetChanged();

        if(displayTasks.isEmpty()){
            Toast.makeText(this,"All tasks deleted",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task deleted. " + displayTasks.size() + " task(s) remaining", Toast.LENGTH_SHORT).show();

        }


    }
}





