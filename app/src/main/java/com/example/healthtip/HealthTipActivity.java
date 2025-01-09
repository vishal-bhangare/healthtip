package com.example.healthtip;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class HealthTipActivity extends AppCompatActivity {
    private TextView welcomeText, bmiText, healthTipText;
    private Button gotItButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_tip);

        welcomeText = findViewById(R.id.welcomeText);
        bmiText = findViewById(R.id.bmiText);
        healthTipText = findViewById(R.id.healthTipText);
        gotItButton = findViewById(R.id.gotItButton);
        // Get data from intent
        String name = getIntent().getStringExtra("name");
        int age = getIntent().getIntExtra("age", 0);
        float weight = getIntent().getFloatExtra("weight", 0);
        float height = getIntent().getFloatExtra("height", 0);
        String preference = getIntent().getStringExtra("preference");
        String goal = getIntent().getStringExtra("goal");

        // Calculate BMI
        float bmi = calculateBMI(weight, height);

        // Set texts
        welcomeText.setText("Welcome, " + name + "!");
        bmiText.setText(String.format("%.1f (%s)", bmi, getBMICategory(bmi)));
        setBMITextColor(bmi);
        healthTipText.setText(generateHealthTip(preference, goal, age, bmi));

        gotItButton.setOnClickListener(v -> {
            finishAffinity(); // This will close all activities and exit the app
        });
    }

    private float calculateBMI(float weight, float height) {
        return (weight / ((height/100) * (height/100)));
    }

    private String getBMICategory(float bmi) {
        if (bmi < 18.5) return "Underweight";
        else if (bmi < 25) return "Normal weight";
        else if (bmi < 30) return "Overweight";
        else return "Obese";
    }
    private void setBMITextColor(float bmi) {
        int color;
        if (bmi < 18.5) {
            color = ContextCompat.getColor(this, android.R.color.holo_orange_dark); // Underweight - Orange
        } else if (bmi < 25) {
            color = ContextCompat.getColor(this, android.R.color.holo_green_dark);  // Normal - Green
        } else if (bmi < 30) {
            color = ContextCompat.getColor(this, android.R.color.holo_orange_dark); // Overweight - Orange
        } else {
            color = ContextCompat.getColor(this, android.R.color.holo_red_dark);    // Obese - Red
        }
        bmiText.setTextColor(color);
    }

    private String generateHealthTip(String preference, String goal, int age, float bmi) {
        StringBuilder tip = new StringBuilder();

        // Fitness preferences
        if (preference.equals("Fitness")) {
            if (goal.equals("Weight Loss")) {
                tip.append("🏃 Cardio Plan:\n");
                tip.append("• 30 minutes of cardio 5 times a week\n");
                tip.append("• Mix of HIIT and steady-state cardio\n");
                tip.append("• Try swimming, cycling, or dancing for variety\n\n");
                tip.append("💪 Remember: Create a 500-calorie daily deficit for healthy weight loss.");
            }
            else if (goal.equals("Muscle Gain")) {
                tip.append("💪 Strength Training:\n");
                tip.append("• Focus on compound exercises (squats, deadlifts, bench press)\n");
                tip.append("• Aim for 1.6-2.2g protein per kg body weight\n");
                tip.append("• Rest 48 hours between training same muscle groups\n\n");
                tip.append("🥗 Eat in a slight caloric surplus of 300-500 calories");
            }
            else if (goal.equals("Better Sleep")) {
                tip.append("🌙 Exercise timing:\n");
                tip.append("• Complete workouts at least 3 hours before bedtime\n");
                tip.append("• Morning exercises can improve sleep quality\n");
                tip.append("• Try gentle yoga or stretching before bed");
            }
            else if (goal.equals("Stress Management")) {
                tip.append("🧘 Stress-Relief Activities:\n");
                tip.append("• Try yoga or tai chi\n");
                tip.append("• Practice deep breathing exercises\n");
                tip.append("• Regular walking in nature\n");
                tip.append("• Join group fitness classes for social support");
            }
        }
        // Diet preferences
        else if (preference.equals("Diet")) {
            if (bmi > 30) {
                tip.append("🥗 Nutrition Guide:\n");
                tip.append("• Start with portion control\n");
                tip.append("• Fill half your plate with vegetables\n");
                tip.append("• Choose lean proteins\n");
                tip.append("• Avoid sugary drinks\n");
                tip.append("• Track calories using a food diary");
            }
            else if (bmi > 25) {
                tip.append("🥗 Healthy Eating Tips:\n");
                tip.append("• Include more vegetables and lean proteins\n");
                tip.append("• Choose whole grains over refined grains\n");
                tip.append("• Practice mindful eating\n");
                tip.append("• Drink water before meals");
            }
            else if (bmi < 18.5) {
                tip.append("🍽️ Weight Gain Tips:\n");
                tip.append("• Eat nutrient-dense foods\n");
                tip.append("• Add healthy fats like nuts and avocados\n");
                tip.append("• Have protein smoothies as snacks\n");
                tip.append("• Eat larger portions at regular intervals");
            }
            else {
                tip.append("🥗 Maintenance Diet:\n");
                tip.append("• Maintain balanced meals\n");
                tip.append("• Include all food groups\n");
                tip.append("• Stay hydrated with 8 glasses of water\n");
                tip.append("• Consider meal prepping");
            }
        }
        // Mental Health preferences
        else if (preference.equals("Mental Health")) {
            tip.append("🧠 Mental Wellness Tips:\n");
            tip.append("• Practice daily meditation\n");
            tip.append("• Maintain a gratitude journal\n");
            tip.append("• Set boundaries for digital devices\n");
            tip.append("• Connect with friends regularly");
        }
        // Sleep preferences
        else if (preference.equals("Sleep")) {
            tip.append("😴 Better Sleep Habits:\n");
            tip.append("• Maintain consistent sleep schedule\n");
            tip.append("• Create a relaxing bedtime routine\n");
            tip.append("• Keep bedroom cool and dark\n");
            tip.append("• Avoid screens 1 hour before bed");
        }

        // Age-specific additions
        if (age > 50) {
            tip.append("\n👴 Age-Specific Tips:\n");
            tip.append("• Include calcium-rich foods\n");
            tip.append("• Focus on balance exercises\n");
            tip.append("• Regular bone density checks\n");
            tip.append("• Stay socially active");
        }
        else if (age < 25) {
            tip.append("\n👶 Youth-Focused Tips:\n");
            tip.append("• Build healthy habits early\n");
            tip.append("• Focus on posture while using devices\n");
            tip.append("• Get adequate sleep for growth\n");
            tip.append("• Stay active with sports");
        }

        return tip.toString();
    }

}
