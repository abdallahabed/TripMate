package com.example.tripplanningapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> tasks;
    public interface OnTaskDeleteListener {
        void onTaskDelete(Task task);
    }
    private OnTaskDeleteListener deleteListener;

    public void setOnTaskDeleteListener(OnTaskDeleteListener listener) {
        this.deleteListener = listener;
    }

    // Constructor
    public TaskAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    // Create ViewHolder
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    /** Bind the data at position to the view holder. */
    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.taskNameTextView.setText(task.getTitle());
        holder.taskDateTextView.setText(task.getDate());
        holder.categoryTextView.setText(task.getCategory());
        holder.taskPriorityTextView.setText(task.getPriority());



        holder.editButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(),TaskEditActivity.class);
            intent.putExtra("mode","edit");
            intent.putExtra("taskId",task.getId());
            v.getContext().startActivity(intent);
        });

        holder.deleteButton.setOnClickListener(v -> {
            if(deleteListener != null){
                deleteListener.onTaskDelete(task);
            }
        });
    }



    //return items
    @Override
    public int getItemCount() {
        return tasks != null ? tasks.size() : 0;
    }

    //refresh page for search and filter to apply the changes
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    // ViewHolder class holds references to views for each item.
    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskNameTextView, taskDateTextView, categoryTextView, taskPriorityTextView;
        Button editButton, deleteButton;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskNameTextView = itemView.findViewById(R.id.taskNameTextView);
            taskDateTextView = itemView.findViewById(R.id.taskDateTextView);
            categoryTextView = itemView.findViewById(R.id.catageroyTextView);
            taskPriorityTextView = itemView.findViewById(R.id.taskPriorityTextView);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}