package com.example.tripplanningapp;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class SharedPrefHelper {

    private static final String PREF_NAME = "TASK_PREF";
    private static final String TASKS_KEY = "TASKS_LIST";

    private final SharedPreferences sharedPreferences;
    private final Gson gson;

    public SharedPrefHelper(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.gson = new Gson();
    }

    // Load tasks using gson
    public List<Task> loadTasks() {

        String json = sharedPreferences.getString(TASKS_KEY, null);
        if (json == null) return new ArrayList<>();

        Type type = new TypeToken<List<Task>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    // Save tasks using gson
    public void saveTasks(List<Task> tasks) {
        String json = gson.toJson(tasks);
        sharedPreferences.edit().putString(TASKS_KEY, json).apply();
    }

    // Add tasks
    public void addTask(Task task) {
        List<Task> tasks = loadTasks();
        tasks.add(task);
        saveTasks(tasks);
    }

    // Update tasks using id
    public void updateTask(Task updatedTask) {
        List<Task> tasks = loadTasks();
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId().equals(updatedTask.getId())) {
                tasks.set(i, updatedTask);
                break;
            }
        }
        saveTasks(tasks);
    }

    // Delete tasks using id
    public void deleteTask(String id) {
        List<Task> tasks = loadTasks();
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId().equals(id)) {
                tasks.remove(i);
                break;
            }
        }
        saveTasks(tasks);
    }


    public Task getTaskById(String taskId){
        List<Task> tasks = loadTasks();
        for (Task task : tasks){
            if (task.getId().equals(taskId)) {
                return task;
            }
        }
        return null;
    }

//    public void clearAllTasks(){
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.remove(TASKS_KEY);
//        editor.apply();
//    }

    public void loadDefaultTasksFromAssets(Context context) {
        // Only load if no tasks exist yet
        if (!loadTasks().isEmpty()) return;

        try {
            InputStream is = context.getAssets().open("tasks.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String json = new String(buffer, "UTF-8");

            Type type = new TypeToken<List<Task>>(){}.getType();
            List<Task> defaultTasks = new Gson().fromJson(json, type);

            if (defaultTasks != null && !defaultTasks.isEmpty()) {
                saveTasks(defaultTasks);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}



