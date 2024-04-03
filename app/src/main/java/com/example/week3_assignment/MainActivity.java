package com.example.week3_assignment;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Double firstValue, secondValue, result;
    private String enteringNumber = "", currentOperation = "", displayText = "";
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator);

        textView = findViewById(R.id.textView);

        int[] numberButtons = {R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9};
        int[] operationButtons = {R.id.buttonAdd, R.id.buttonSub, R.id.buttonMul, R.id.buttonDivide};

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button)v;
                String buttonText = b.getText().toString();

                switch (buttonText) {
                    case "C":
                        clearCalculator();
                        break;
                    case "=":
                        calculateResult();
                        break;
                    case "+":
                    case "-":
                    case "*":
                    case "/":
                        if (!enteringNumber.isEmpty()) {
                            prepareOperation(buttonText);
                        }
                        break;
                    default: // 숫자 버튼이 눌린 경우
                        appendNumber(buttonText);
                        break;
                }
            }
        };

        for (int id : numberButtons) {
            findViewById(id).setOnClickListener(listener);
        }

        for (int id : operationButtons) {
            findViewById(id).setOnClickListener(listener);
        }

        findViewById(R.id.buttonClear).setOnClickListener(listener);
        findViewById(R.id.buttonEquals).setOnClickListener(listener);
    }

    private void appendNumber(String number) {
        if (!flag) {
            clearCalculator();
        }
        enteringNumber += number;
        displayText += number;
        textView.setText(displayText);
    }

    private void prepareOperation(String operation) {
        if (firstValue == null) {
            firstValue = Double.parseDouble(enteringNumber);
        } else if (!enteringNumber.isEmpty()) {
            secondValue = Double.parseDouble(enteringNumber);
            calculateMiddleResult();
        }
        currentOperation = operation;
        enteringNumber = "";
        displayText += " " + operation + " ";
        textView.setText(displayText);
    }

    private void calculateResult() {
        if (!enteringNumber.isEmpty() && firstValue != null && !currentOperation.isEmpty()) {
            secondValue = Double.parseDouble(enteringNumber);
            calculateFinalResult();
            if(result != null) {
                displayText += " = " + result;
            }
            textView.setText(displayText);
            enteringNumber = String.valueOf(result);
        }
    }

    private void calculateMiddleResult() {
        switch (currentOperation) {
            case "+":
                firstValue += secondValue;
                break;
            case "-":
                firstValue -= secondValue;
                break;
            case "*":
                firstValue *= secondValue;
                break;
            case "/":
                if (secondValue != 0) {
                    firstValue /= secondValue;
                } else {
                    displayError();
                    return;
                }
                break;
        }
        enteringNumber = "";
        result = firstValue;
    }

    private void displayError() {
        displayText = "ERROR";
        textView.setText(displayText);
        flag = false;
    }
    private void calculateFinalResult() {
        calculateMiddleResult();
        flag = false;
    }

    private void clearCalculator() {
        firstValue = null;
        secondValue = null;
        enteringNumber = "";
        currentOperation = "";
        displayText = "";
        result = null;
        textView.setText(displayText);
        flag = true;
    }
}