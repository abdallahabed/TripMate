package com.example.tripplanningapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    void insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);

    @Query("SELECT * FROM tasks WHERE id = :taskId LIMIT 1")
    Task getTaskById(int taskId);

    @Query("SELECT * FROM tasks")
    List<Task> getAllTasksList();

    @Query("SELECT * FROM tasks")
    LiveData<List<Task>> getAllTasks();

    @Query("SELECT * FROM tasks WHERE id IN (:ids)")
    List<Task> getTasksByIds(List<Integer> ids);
}