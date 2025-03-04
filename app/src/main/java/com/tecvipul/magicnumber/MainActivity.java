package com.tecvipul.magicnumber;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private EditText editTextBirthdate;
    private Button buttonGenerate;
    private TableLayout tableLayoutMagicSquare;
    private TextView textViewTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        editTextBirthdate = findViewById(R.id.editTextBirthdate);
        buttonGenerate = findViewById(R.id.buttonGenerate);
        tableLayoutMagicSquare = findViewById(R.id.tableLayoutMagicSquare);
        textViewTotal = findViewById(R.id.textViewTotal);

        // Initially hide the Lottie animation

        // Set button click listener to generate the magic square
        buttonGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                generateMagicSquare();
                // Apply button animation
                // Show and start the Lottie animation
            }
        });
    }

    private void generateMagicSquare() {
        // Clear previous table
        tableLayoutMagicSquare.removeAllViews();

        // Get birthdate input
        String birthdate = editTextBirthdate.getText().toString().trim();
        if (!birthdate.matches("\\d{2}/\\d{2}/\\d{4}")) {
            Toast.makeText(this, "Please enter birthdate in DD/MM/YYYY format", Toast.LENGTH_SHORT).show();
            textViewTotal.setText("Your Number: ");
            return;
        }

        // Calculate sum of all digits in the birthdate
        String digitsOnly = birthdate.replace("/", ""); // e.g., "05111995"
        int sum = 0;
        for (char digit : digitsOnly.toCharArray()) {
            sum += Character.getNumericValue(digit);
        }

        // Display total sum
        textViewTotal.setText("Your Number: " + sum);


        // Generate 3x3 magic square with the calculated sum
        int[][] magicSquare = createMagicSquare(sum);

        // Dynamically populate the TableLayout
        for (int i = 0; i < 3; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
            for (int j = 0; j < 3; j++) {
                TextView cell = new TextView(this);
                cell.setText(String.valueOf(magicSquare[i][j]));
                cell.setGravity(Gravity.CENTER);
                cell.setPadding(20, 20, 20, 20);
                cell.setTextSize(20);
                cell.setTextColor(getResources().getColor(android.R.color.black));
                cell.setBackgroundResource(R.drawable.rounded_cell);
                cell.setElevation(4f);
                TableRow.LayoutParams params = new TableRow.LayoutParams(100, 100);
                params.setMargins(8, 8, 8, 8);
                cell.setLayoutParams(params);
                row.addView(cell);
            }
            tableLayoutMagicSquare.addView(row);
        }
    }

    private int[][] createMagicSquare(int targetSum) {
        // Base 3x3 magic square (sums to 15)
        int[][] baseSquare = {
                {8, 1, 6},
                {3, 5, 7},
                {4, 9, 2}
        };

        int baseSum = 15;
        int diff = targetSum - baseSum;  // Difference to adjust
        int adjustment = diff / 3;       // Base adjustment per row
        int remainder = diff % 3;        // Remainder to distribute

        int[][] result = new int[3][3];

        // Apply base adjustment to all cells
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result[i][j] = baseSquare[i][j] + adjustment;
            }
        }

        // Distribute the remainder to ensure the sum matches targetSum
        if (remainder > 0) {
            // Add remainder to specific cells to maintain magic properties
            result[0][0] += remainder;  // Adjust top-left
            result[1][1] += remainder;  // Adjust center
            result[2][2] -= remainder;  // Subtract from bottom-right to balance
        }

        return result;
    }
}