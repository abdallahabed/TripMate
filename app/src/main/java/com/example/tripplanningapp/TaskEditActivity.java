package com.example.tripplanningapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class TaskEditActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText descriptionEditText;
    private DatePicker datePicker;
    private EditText dateEditText;

    private RadioGroup priorityRadioGroup;
    private RadioButton highRadio;
    private RadioButton lowRadio;
    private Spinner categorySpinner;
    private Button updateButton;
    private Button cancelButton;

    private SharedPrefHelper prefHelper;
    private String mode;
    private String taskId;
    private Task currentTask;
    private String selectedDate = "";

    private String[] categories;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        prefHelper = new SharedPrefHelper(this);

        Intent intent = getIntent();
        mode = intent.getStringExtra("mode");
        taskId = intent.getStringExtra("taskId");

        initializeViews();

        setupCategorySpinner();

        if ("edit".equals(mode) && taskId != null) {
            loadTaskData();
        }

        setupButtonListeners();

    }


    private void initializeViews() {
        nameEditText = findViewById(R.id.taskNameEditText);
        descriptionEditText = findViewById(R.id.taskDescriptionEditText);
//      datePicker = findViewById(R.id.datePicker);
        dateEditText = findViewById(R.id.dateEditText);
        priorityRadioGroup = findViewById(R.id.priorityRadioGroup);
        highRadio = findViewById(R.id.highRadio);
        lowRadio = findViewById(R.id.lowRadio);
        categorySpinner = findViewById(R.id.catagoreySpinner);

        updateButton = findViewById(R.id.UpdateButton);
        cancelButton = findViewById(R.id.cancelButton);

        setupDatePickerDialog();

        categorySpinner.setEnabled(true);

        if ("add".equals(mode)) {
            updateButton.setText("Add Task");
        } else if ("edit".equals(mode)) {
            updateButton.setText("Update Task");
        }
    }

    private void setupCategorySpinner() {
        categories  = getResources().getStringArray(R.array.task_categories);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categories
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
    }
    private void setupDatePickerDialog() {
        dateEditText.setOnClickListener(v -> {

            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    TaskEditActivity.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        dateEditText.setText(date);
                    },
                    year, month, day
            );

            dialog.show();
        });
    }


    private void loadTaskData() {
        currentTask = prefHelper.getTaskById(taskId);
        if (currentTask != null) {
            nameEditText.setText(currentTask.getTitle());
            descriptionEditText.setText(currentTask.getDescription());

//            // Set date
//            if (currentTask.getDate() != null && !currentTask.getDate().isEmpty()) {
//                String[] parts = currentTask.getDate().split("-");
//                int year = Integer.parseInt(parts[0]);
//                int month = Integer.parseInt(parts[1]) - 1;
//                int day = Integer.parseInt(parts[2]);
//                datePicker.updateDate(year, month, day);
//            }
            if (currentTask.getDate() != null && !currentTask.getDate().isEmpty()) {
                dateEditText.setText(currentTask.getDate());
            }
            // Set priority
            if ("High".equals(currentTask.getPriority())) highRadio.setChecked(true);
            else if ("Low".equals(currentTask.getPriority())) lowRadio.setChecked(true);

            // Set category
            for (int i = 0; i < categories.length; i++) {
                if (categories[i].equals(currentTask.getCategory())) {
                    categorySpinner.setSelection(i);
                    break;
                }
            }
        }
    }

    private void setupButtonListeners() {
        updateButton.setOnClickListener(v -> saveTask());

        cancelButton.setOnClickListener(v -> finish());
    }

    private void saveTask() {
        String name = nameEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();

        if (name.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get selected date from DatePicker
//        int day = datePicker.getDayOfMonth();
//        int month = datePicker.getMonth() + 1; // Month is 0-indexed
//        int year = datePicker.getYear();
//        String selectedDate = String.format("%04d-%02d-%02d", year, month, day);



        String selectedDate = dateEditText.getText().toString().trim();

        if (selectedDate.isEmpty()) {
            Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show();
            return;
        }
        // Get priority
        String priority = (priorityRadioGroup.getCheckedRadioButtonId() == R.id.highRadio) ? "High" : "Low";

        // Get category
        String category = categorySpinner.getSelectedItem().toString();

        if ("add".equals(mode)) {
            Task newTask = new Task();
            newTask.setId(String.valueOf(System.currentTimeMillis()));
            newTask.setTitle(name);
            newTask.setDescription(description);
            newTask.setDate(selectedDate);
            newTask.setPriority(priority);
            newTask.setCategory(category);

            prefHelper.addTask(newTask);
            Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show();
        } else if ("edit".equals(mode) && currentTask != null) {
            currentTask.setTitle(name);
            currentTask.setDescription(description);
            currentTask.setDate(selectedDate);
            currentTask.setPriority(priority);
            currentTask.setCategory(category);

            prefHelper.updateTask(currentTask);
            Toast.makeText(this, "Task updated", Toast.LENGTH_SHORT).show();
        }

        finish();
    }
}


