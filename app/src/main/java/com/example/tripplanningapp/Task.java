package com.example.tripplanningapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasks")
public class Task {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "category")
    private String category;

    @ColumnInfo(name = "priority")
    private String priority;

    public Task(String title, String description, String category, String date, String priority) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.date = date;
        this.priority = priority;
    }

    public Task() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", category='" + category + '\'' +
                ", priority='" + priority + '\'' +
                '}';
    }
}



//package com.example.tripplanningapp;
//
//public class Task {
//    private String id;
//    private String title;
//    private String description;
//    private String date;
//    private String category;
//    private String priority;
//
//
//
//    public Task(String id, String title, String description, String category, String date, String priority) {
//        this.id = id;
//        this.title = title;
//        this.description = description;
//        this.category = category;
//        this.date = date;
//        this.priority = priority;
//    }
//
//    public Task() {
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getDate() {
//        return date;
//    }
//
//    public void setDate(String date) {
//        this.date = date;
//    }
//
//    public String getCategory() {
//        return category;
//    }
//
//    public void setCategory(String category) {
//        this.category = category;
//    }
//
//    public String getPriority() {
//        return priority;
//    }
//
//    public void setPriority(String priority) {
//        this.priority = priority;
//    }
//
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//
//    @Override
//    public String toString() {
//        return "Task{" +
//                "id='" + id + '\'' +
//                ", title='" + title + '\'' +
//                ", description='" + description + '\'' +
//                ", date='" + date + '\'' +
//                ", category='" + category + '\'' +
//                ", priority='" + priority + '\'' +
//                '}';
//    }
//}
//
