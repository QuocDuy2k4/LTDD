package com.example.calculatorapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvResult;

    // Khai báo biến logic chính
    private double firstNumber = 0;
    private String currentOperator = null;
    private boolean isNewNumber = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Liên kết Views
        tvResult = findViewById(R.id.tvResult);

        // 2. Thiết lập hành động cho các nút
        setupButtonListeners();
    }

    private void setupButtonListeners() {
        // --- Liên kết Nút Số và Thập phân ---
        findViewById(R.id.btnZero).setOnClickListener(v -> appendNumber("0"));
        findViewById(R.id.btnOne).setOnClickListener(v -> appendNumber("1"));
        findViewById(R.id.btnTwo).setOnClickListener(v -> appendNumber("2"));
        findViewById(R.id.btnThree).setOnClickListener(v -> appendNumber("3"));
        findViewById(R.id.btnFour).setOnClickListener(v -> appendNumber("4"));
        findViewById(R.id.btnFive).setOnClickListener(v -> appendNumber("5"));
        findViewById(R.id.btnSix).setOnClickListener(v -> appendNumber("6"));
        findViewById(R.id.btnSeven).setOnClickListener(v -> appendNumber("7"));
        findViewById(R.id.btnEight).setOnClickListener(v -> appendNumber("8"));
        findViewById(R.id.btnNine).setOnClickListener(v -> appendNumber("9"));
        findViewById(R.id.btnDecimal).setOnClickListener(v -> appendNumber("."));

        // --- Liên kết Nút Phép Toán & Chức năng ---
        findViewById(R.id.btnPlus).setOnClickListener(v -> setOperator("+"));
        findViewById(R.id.btnMinus).setOnClickListener(v -> setOperator("-"));
        findViewById(R.id.btnMultiply).setOnClickListener(v -> setOperator("*"));
        findViewById(R.id.btnDivide).setOnClickListener(v -> setOperator("/"));
        findViewById(R.id.btnEquals).setOnClickListener(v -> calculateResult());
        findViewById(R.id.btnAC).setOnClickListener(v -> clearAll());
    }

    // Hàm xử lý khi người dùng bấm số
    private void appendNumber(String number) {
        String currentText = tvResult.getText().toString();

        if (isNewNumber) {
            if (number.equals(".")) {
                tvResult.setText("0.");
            } else {
                tvResult.setText(number);
            }
            isNewNumber = false;
        } else {
            if (number.equals(".") && currentText.contains(".")) {
                return;
            }
            tvResult.append(number);
        }
    }

    // Hàm xử lý khi người dùng bấm phép toán
    private void setOperator(String operator) {
        try {
            firstNumber = Double.parseDouble(tvResult.getText().toString());
            currentOperator = operator;
            isNewNumber = true;
        } catch (NumberFormatException e) {
            tvResult.setText("Error");
            clearAll();
        }
    }

    // Hàm xử lý khi người dùng bấm nút Bằng (=)
    private void calculateResult() {
        if (currentOperator == null) return;

        double secondNumber = Double.parseDouble(tvResult.getText().toString());
        double result = 0;

        switch (currentOperator) {
            case "+": result = firstNumber + secondNumber; break;
            case "-": result = firstNumber - secondNumber; break;
            case "*": result = firstNumber * secondNumber; break;
            case "/":
                if (secondNumber == 0) {
                    tvResult.setText("Error");
                    clearAll();
                    return;
                }
                result = firstNumber / secondNumber;
                break;
        }

        displayResult(result);

        firstNumber = result;
        currentOperator = null;
        isNewNumber = true;
    }

    // Hàm Xóa (AC)
    private void clearAll() {
        // Sử dụng tài nguyên chuỗi cho "0"
        tvResult.setText(getString(R.string.button_zero));
        firstNumber = 0;
        currentOperator = null;
        isNewNumber = true;
    }

    // Hàm trợ giúp: Định dạng kết quả double gọn gàng
    private void displayResult(double result) {
        if (result == (long) result) {
            tvResult.setText(String.format("%d", (long) result));
        } else {
            tvResult.setText(String.valueOf(result));
        }
    }
}