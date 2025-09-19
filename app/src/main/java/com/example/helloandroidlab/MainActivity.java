package com.example.helloandroidlab;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewDisplay;

    // Button fields are kept for potential future use, e.g., changing button states.
    private Button button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    private Button buttonAdd, buttonSubtract, buttonMultiply, buttonDivide;
    private Button buttonEquals, buttonDecimal, buttonClear, buttonBackspace, buttonPercent;

    private String currentNumber = "0";
    private String firstOperand = "";
    private String currentOperator = "";
    private boolean operatorPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewDisplay = findViewById(R.id.textView);

        // Initialize number buttons
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);

        // Initialize operator buttons
        buttonAdd = findViewById(R.id.button11); // '+'
        buttonSubtract = findViewById(R.id.button12); // '-'
        buttonMultiply = findViewById(R.id.buttonMultiply); // 'X'
        buttonDivide = findViewById(R.id.buttonDivide); // '/'

        // Initialize other buttons
        buttonEquals = findViewById(R.id.button14); // '='
        buttonDecimal = findViewById(R.id.button15); // '.'
        buttonClear = findViewById(R.id.buttonClear); // 'C'
        buttonBackspace = findViewById(R.id.button16); // '<'
        buttonPercent = findViewById(R.id.buttonPercent); // '%'

        // Set click listeners
        button0.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);

        buttonAdd.setOnClickListener(this);
        buttonSubtract.setOnClickListener(this);
        buttonMultiply.setOnClickListener(this);
        buttonDivide.setOnClickListener(this);

        buttonEquals.setOnClickListener(this);
        buttonDecimal.setOnClickListener(this);
        buttonClear.setOnClickListener(this);
        buttonBackspace.setOnClickListener(this);
        buttonPercent.setOnClickListener(this);

        updateDisplay();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button0 || id == R.id.button1 || id == R.id.button2 || id == R.id.button3 ||
            id == R.id.button4 || id == R.id.button5 || id == R.id.button6 || id == R.id.button7 ||
            id == R.id.button8 || id == R.id.button9) {
            handleNumberClick(((Button) v).getText().toString());
        } else if (id == R.id.button15) { // Corrected: Was R.id.buttonDecimal
            handleDecimalClick();
        } else if (id == R.id.button11 || id == R.id.button12 || // Corrected: Was R.id.buttonAdd, R.id.buttonSubtract
                   id == R.id.buttonMultiply || id == R.id.buttonDivide) {
            handleOperatorClick(((Button) v).getText().toString());
        } else if (id == R.id.button14) { // Corrected: Was R.id.buttonEquals
            handleEqualsClick();
        } else if (id == R.id.buttonClear) {
            handleClearClick();
        } else if (id == R.id.button16) { // Corrected: Was R.id.buttonBackspace
            handleBackspaceClick();
        } else if (id == R.id.buttonPercent) {
            handlePercentClick();
        }
        updateDisplay();
    }

    private void handleNumberClick(String number) {
        if (operatorPressed) {
            currentNumber = "0";
            operatorPressed = false;
        }
        if (currentNumber.equals("0")) {
            currentNumber = number;
        } else {
            currentNumber += number;
        }
    }

    private void handleDecimalClick() {
        if (operatorPressed) {
            currentNumber = "0";
            operatorPressed = false;
        }
        if (!currentNumber.contains(".")) {
            currentNumber += ".";
        }
    }

    private void handleOperatorClick(String operator) {
        if (!currentOperator.isEmpty() && !operatorPressed) {
            calculateResult(); // Calculate previous operation if any
        }
        firstOperand = currentNumber;
        currentOperator = operator;
        operatorPressed = true;
    }

    private void handleEqualsClick() {
        if (currentOperator.isEmpty() || firstOperand.isEmpty()) {
            return;
        }
        calculateResult();
        currentOperator = ""; // Reset operator for next calculation
    }

    private void handleClearClick() {
        currentNumber = "0";
        firstOperand = "";
        currentOperator = "";
        operatorPressed = false;
    }

    private void handleBackspaceClick() {
        if (operatorPressed) {
            operatorPressed = false;
            currentOperator = "";
            currentNumber = firstOperand;
            firstOperand = "";
        } else if (currentNumber.length() > 1) {
            currentNumber = currentNumber.substring(0, currentNumber.length() - 1);
        } else if (currentNumber.length() == 1 && !currentNumber.equals("0")){
            currentNumber = "0";
        } else if (!firstOperand.isEmpty() && !currentOperator.isEmpty()){
            currentOperator = "";
            currentNumber = firstOperand;
            firstOperand = "";
        }
    }

    private void handlePercentClick() {
        if (currentNumber.isEmpty() || currentNumber.equals("Error")) return;
        try {
            double value = Double.parseDouble(currentNumber);
            currentNumber = String.valueOf(value / 100.0);
            operatorPressed = true; 
        } catch (NumberFormatException e) {
            currentNumber = "Error";
        }
    }

    private void calculateResult() {
        if (firstOperand.isEmpty() || currentOperator.isEmpty() || currentNumber.isEmpty() || currentNumber.equals("Error")) {
            return;
        }
        try {
            double num1 = Double.parseDouble(firstOperand);
            double num2 = Double.parseDouble(currentNumber);
            double result = 0;

            switch (currentOperator) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "X":
                    result = num1 * num2;
                    break;
                case "/":
                    if (num2 == 0) {
                        currentNumber = "Error";
                        firstOperand = "";
                        operatorPressed = true;
                        return;
                    }
                    result = num1 / num2;
                    break;
            }
            if (result == (long) result) {
                currentNumber = String.format("%d", (long) result);
            } else {
                currentNumber = String.format("%s", result);
            }
            firstOperand = ""; 
            operatorPressed = true; 
        } catch (NumberFormatException e) {
            currentNumber = "Error";
            firstOperand = "";
            operatorPressed = true;
        }
    }

    private void updateDisplay() {
        if (currentNumber.equals("Error")) {
            textViewDisplay.setText("Error");
        } else if (currentNumber.isEmpty()) {
             textViewDisplay.setText("0");
        } else {
            if (currentNumber.length() > 15 && currentNumber.contains(".")) {
                try {
                    double d = Double.parseDouble(currentNumber);
                     if (Math.abs(d) > 1e15 || (Math.abs(d) < 1e-4 && d != 0) ) {
                        textViewDisplay.setText(String.format(java.util.Locale.US, "%.6e", d));
                    } else {
                         textViewDisplay.setText(String.format(java.util.Locale.US, "%.9f", d).replaceAll("0*$","").replaceAll("\\.$",""));
                    }
                } catch (NumberFormatException e){
                     textViewDisplay.setText(currentNumber.substring(0,15));
                }
            } else if (currentNumber.length() > 20) { 
                 textViewDisplay.setText(currentNumber.substring(0,20));
            }
            else {
                textViewDisplay.setText(currentNumber);
            }
        }
    }
}
