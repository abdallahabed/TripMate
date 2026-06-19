package com.example.tripplanningapp;

import android.content.Context;

import java.util.List;

public class TaskRepository {

    private final TaskDao taskDao;

    public TaskRepository(Context context) {
        TaskDatabase db = TaskDatabase.getInstance(context);
        taskDao = db.taskDao();
    }

    public List<Task> loadTasks() {
        return taskDao.getAllTasksList();
    }

    public void addTask(Task task) {
        taskDao.insert(task);
    }

    public void updateTask(Task updatedTask) {
        taskDao.update(updatedTask);
    }

    public void deleteTask(Task task) {
        taskDao.delete(task);
    }

    public Task getTaskById(int taskId) {
        return taskDao.getTaskById(taskId);
    }
    public void seedDefaultTasksIfEmpty() {
        if (!taskDao.getAllTasksList().isEmpty()) return;

        taskDao.insert(new Task("Pack Suitcase", "Include clothes, shoes, and toiletries", "Packing", "2025-12-01", "High"));
        taskDao.insert(new Task("Prepare Snacks", "Buy or pack snacks for the trip", "Food", "", "Low"));
        taskDao.insert(new Task("Check Passport", "Ensure passport is valid for travel", "Documents", "2025-11-28", "High"));
        taskDao.insert(new Task("Pack Winter Clothes", "Include jackets, gloves, and hats", "Clothes", "", "High"));
        taskDao.insert(new Task("Bring Medicine", "Include prescription and first aid", "Medicine", "2025-12-02", "High"));
        taskDao.insert(new Task("Pack Other Essentials", "Charger, headphones, travel pillow", "Other", "", "Low"));
        taskDao.insert(new Task("Pack Toiletries", "Toothbrush, toothpaste, shampoo", "Packing", "", "Low"));
        taskDao.insert(new Task("Buy Snacks", "Fruit, nuts, chocolate bars", "Food", "2025-11-30", "High"));
        taskDao.insert(new Task("Print Tickets", "Print flight and train tickets", "Documents", "2025-11-29", "High"));
        taskDao.insert(new Task("Pack Casual Clothes", "T-shirts, jeans, jackets", "Clothes", "", "Low"));
        taskDao.insert(new Task("Bring Painkillers", "Paracetamol, ibuprofen", "Medicine", "", "High"));
        taskDao.insert(new Task("Pack Travel Pillow", "For long flights or bus rides", "Other", "", "Low"));
        taskDao.insert(new Task("Pack Electronics", "Phone, charger, power bank", "Other", "2025-12-01", "High"));
    }
}