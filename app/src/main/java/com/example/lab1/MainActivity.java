package com.example.lab1;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView resultTV, solutionTV;
    MaterialButton buttonC, buttonBOpen, buttonBClose;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    MaterialButton buttonMul, buttonDiv, buttonAdd, buttonSub, buttonEqual;
    MaterialButton buttonAC, buttonDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        resultTV = findViewById(R.id.result_tv);

        solutionTV = findViewById(R.id.solution_tv);

        assignID(buttonDot, R.id.button_decimal);
        assignID(button0, R.id.button_zero);
        assignID(button1, R.id.button_one);
        assignID(button2, R.id.button_two);
        assignID(button3, R.id.button_three);
        assignID(button4, R.id.button_four);
        assignID(button5, R.id.button_five);
        assignID(button6, R.id.button_six);
        assignID(button7, R.id.button_seven);
        assignID(button8, R.id.button_eight);
        assignID(button9, R.id.button_nine);
        assignID(buttonMul, R.id.button_multiply);
        assignID(buttonDiv, R.id.button_divide);
        assignID(buttonAdd, R.id.button_add);
        assignID(buttonSub, R.id.button_subtract);
        assignID(buttonEqual, R.id.button_equal);
        assignID(buttonBOpen, R.id.button_open_bracket);
        assignID(buttonBClose, R.id.button_close_bracket);
        assignID(buttonAC, R.id.button_all_clear);
        assignID(buttonC, R.id.button_c);

    }

    private void assignID(MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solutionTV.getText().toString();

        if (buttonText.equals("AC")) {
            solutionTV.setText("");
            resultTV.setText("0");
            return;
        }

        if (buttonText.equals("=")) {
            solutionTV.setText(resultTV.getText());
            return;
        }

        if (buttonText.equals("C")) {
            if (!dataToCalculate.isEmpty()) {
                dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length()-1);
            }

        } else {
            dataToCalculate = dataToCalculate+buttonText;
        }

        solutionTV.setText(dataToCalculate);

        String finalResult = getResults(dataToCalculate);

        if (!finalResult.equals("Err")) {
            resultTV.setText(finalResult);
        }
    }

    String getResults(String data) {
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();

            Object result = context.evaluateString(scriptable, data, "Javascript", 1, null);

            // Check if the result is undefined or null
            if (result == Context.getUndefinedValue()) {

                return "0";
            }

            return result.toString();

        } catch (Exception e) {
            return "Err";
        } finally {
            Context.exit();
        }
    }


}