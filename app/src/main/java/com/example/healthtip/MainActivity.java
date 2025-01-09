package com.example.healthtip;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private EditText nameInput, ageInput, weightInput, heightInput;

    private Spinner preferencesSpinner, goalsSpinner;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        nameInput = findViewById(R.id.nameInput);
        ageInput = findViewById(R.id.ageInput);
        weightInput = findViewById(R.id.weightInput);
        heightInput = findViewById(R.id.heightInput);
        preferencesSpinner = findViewById(R.id.preferencesSpinner);
        goalsSpinner = findViewById(R.id.goalsSpinner);
        submitButton = findViewById(R.id.submitButton);

        // Setup spinners
        ArrayAdapter<CharSequence> preferencesAdapter = ArrayAdapter.createFromResource(
                this, R.array.preferences_array, android.R.layout.simple_spinner_item);
        preferencesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        preferencesSpinner.setAdapter(preferencesAdapter);

        ArrayAdapter<CharSequence> goalsAdapter = ArrayAdapter.createFromResource(
                this, R.array.goals_array, android.R.layout.simple_spinner_item);
        goalsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        goalsSpinner.setAdapter(goalsAdapter);

        submitButton.setOnClickListener(v -> {
            if (validateInputs()) {
                launchHealthTipActivity();
            }
        });
    }

    private boolean validateInputs() {
        if (nameInput.getText().toString().isEmpty() ||
                ageInput.getText().toString().isEmpty() ||
                weightInput.getText().toString().isEmpty() ||
                heightInput.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void launchHealthTipActivity() {
        Intent intent = new Intent(this, HealthTipActivity.class);
        intent.putExtra("name", nameInput.getText().toString());
        intent.putExtra("age", Integer.parseInt(ageInput.getText().toString()));
        intent.putExtra("weight", Float.parseFloat(weightInput.getText().toString()));
        intent.putExtra("height", Float.parseFloat(heightInput.getText().toString()));
        intent.putExtra("preference", preferencesSpinner.getSelectedItem().toString());
        intent.putExtra("goal", goalsSpinner.getSelectedItem().toString());
        startActivity(intent);
    }
}
