package com.example.tripplanningapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;



import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText searchEditText;
    private RadioGroup priorityRadioGroup;
    private RadioButton highRadio;
    private RadioButton lowRadio;
    private CheckBox dateCheckBox;
    private Switch catageroysSwitch;
    private Spinner catageroySpinner;
    private Button searchButton;
    private Button addButton;
    private Button viewButton;
    private SharedPrefHelper sharedPreferences;
    private List<Task> Tasks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intializeViews();
        setUpCategoreySpinner();
        sharedPreferences = new SharedPrefHelper(this);

        sharedPreferences.loadDefaultTasksFromAssets(this);

        Tasks = sharedPreferences.loadTasks();

        setUpEventListeners();
        loadTasks();


    }
    private void intializeViews(){
        searchEditText = findViewById(R.id.searchEditText);
        priorityRadioGroup = findViewById(R.id.priorityRadioGroup);
        highRadio = findViewById(R.id.highRadio);
        lowRadio = findViewById(R.id.lowRadio);
        dateCheckBox = findViewById(R.id.withDateCheckbox);
        catageroysSwitch = findViewById(R.id.catagoreySwitch);
        catageroySpinner = findViewById(R.id.catagoreySpinner);
        searchButton = findViewById(R.id.searchButton);
        addButton = findViewById(R.id.addButton);
        viewButton = findViewById(R.id.viewButton);


    }
    private void setUpCategoreySpinner(){
        catageroySpinner.setVisibility(View.GONE);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.task_categories,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        catageroySpinner.setAdapter(adapter);
    }

    private void setUpEventListeners(){
        catageroysSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                catageroySpinner.setVisibility(View.VISIBLE);
            } else {
                catageroySpinner.setVisibility(View.GONE);
            }
        });

        searchButton.setOnClickListener(v -> performSearch());

        // View all tasks
        viewButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TasksActivity.class);
            intent.putExtra("filtered", false);
            startActivity(intent);
        });

        // Add task
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TaskEditActivity.class);
            intent.putExtra("mode", "add");
            startActivity(intent);
        });
    }

    private void loadTasks(){
        Tasks = sharedPreferences.loadTasks();
    }
    private void performSearch() {
        String searchQuery = searchEditText.getText().toString().trim().toLowerCase();
        String selectedPriority = null;
        boolean withDateOnly = dateCheckBox.isChecked();
        String selectedCategory = null;

        // Priority selection
        int selectedRadioId = priorityRadioGroup.getCheckedRadioButtonId();
        if (selectedRadioId == R.id.highRadio) {
            selectedPriority = "High";
        } else if (selectedRadioId == R.id.lowRadio) {
            selectedPriority = "Low";
        }

        // Category selection
        if (catageroysSwitch.isChecked()) {
            selectedCategory = catageroySpinner.getSelectedItem().toString();
        }

        // Filter tasks
        List<Task> filteredTasks = filterTasks(searchQuery, selectedPriority, withDateOnly, selectedCategory);

        if (filteredTasks.isEmpty()) {
            Toast.makeText(this, "No tasks found matching your criteria", Toast.LENGTH_SHORT).show();
        } else {
            // Pass filtered tasks to TasksActivity
            Intent intent = new Intent(MainActivity.this, TasksActivity.class);
            intent.putExtra("filtered", true);

            ArrayList<String> taskIds = new ArrayList<>();
            for (Task task : filteredTasks) {
                taskIds.add(task.getId());
            }
            intent.putStringArrayListExtra("taskIds", taskIds);

            startActivity(intent);
        }
    }

    private List<Task> filterTasks(String query, String priority, boolean withDateOnly, String category) {
        List<Task> filtered = new ArrayList<>();

        for (Task task : Tasks) {
            boolean matches = true;

            // Search query (name & description)
            if (!query.isEmpty()) {
                boolean nameMatch = task.getTitle() != null && task.getTitle().toLowerCase().contains(query);
                boolean descMatch = task.getDescription() != null && task.getDescription().toLowerCase().contains(query);
                if (!nameMatch && !descMatch) {
                    matches = false;
                }
            }

            // Priority filter
            if (priority != null && !task.getPriority().equals(priority)) {
                matches = false;
            }

            // Date filter
            if (withDateOnly && (task.getDate() == null || task.getDate().isEmpty())) {
                matches = false;
            }

            // Category filter
            if (category != null && !task.getCategory().equals(category)) {
                matches = false;
            }

            if (matches) {
                filtered.add(task);
            }
        }

        return filtered;
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTasks();
    }
}