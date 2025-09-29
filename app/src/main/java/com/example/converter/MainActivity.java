package com.example.converter;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etNumber;
    Spinner spinnerFrom, spinnerTo;
    Button btnConvert;
    TextView tvResult;

    String[] systems = {"Decimal", "Binary", "Octal", "Hexadecimal"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // ✅ Will work if XML is correct

        etNumber = findViewById(R.id.etNumber);
        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        btnConvert = findViewById(R.id.btnConvert);
        tvResult = findViewById(R.id.tvResult);

        // Setup spinner adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, systems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

        // Button click listener
        btnConvert.setOnClickListener(v -> {
            String input = etNumber.getText().toString().trim();
            if (input.isEmpty()) {
                tvResult.setText("Please enter a number");
                return;
            }

            String from = spinnerFrom.getSelectedItem().toString();
            String to = spinnerTo.getSelectedItem().toString();

            try {
                // Step 1: Convert input to decimal
                int decimalValue = convertToDecimal(input, from);

                // Step 2: Convert decimal to target system
                String result = convertFromDecimal(decimalValue, to);

                tvResult.setText("Result: " + result);

            } catch (NumberFormatException e) {
                tvResult.setText("Invalid number for " + from);
            }
        });
    }

    // Convert input string from source system to decimal
    private int convertToDecimal(String input, String fromSystem) {
        switch (fromSystem) {
            case "Binary":
                return Integer.parseInt(input, 2);
            case "Octal":
                return Integer.parseInt(input, 8);
            case "Hexadecimal":
                return Integer.parseInt(input, 16);
            default: // Decimal
                return Integer.parseInt(input);
        }
    }

    // Convert decimal to target system
    private String convertFromDecimal(int value, String toSystem) {
        switch (toSystem) {
            case "Binary":
                return Integer.toBinaryString(value);
            case "Octal":
                return Integer.toOctalString(value);
            case "Hexadecimal":
                return Integer.toHexString(value).toUpperCase();
            default: // Decimal
                return String.valueOf(value);
        }
    }
}
