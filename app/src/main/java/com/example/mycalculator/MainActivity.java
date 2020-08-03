package com.example.mycalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText result;
    private EditText NewNumber;
    private TextView displayOperation;

    private Double operand1 = null;
    private String pendingOperation = "";

    private static final String State_pending_operation ="";
    private static final String State_operand1 ="operand1";//I tried to set empty string here, however, when rotate the screen, the operator will not be saved??

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result =(EditText) findViewById(R.id.result);
        NewNumber = (EditText) findViewById(R.id.newNumber);
        displayOperation =(TextView) findViewById(R.id.operation);

        Button button0 = (Button) findViewById(R.id.button0);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button button5 = (Button) findViewById(R.id.button5);
        Button button6 = (Button) findViewById(R.id.button6);
        Button button7 = (Button) findViewById(R.id.button7);
        Button button8 = (Button) findViewById(R.id.button8);
        Button button9 = (Button) findViewById(R.id.button9);
        Button buttonDot = (Button) findViewById(R.id.buttonDot);

        Button buttonDivide = (Button) findViewById(R.id.buttonDivide);
        Button buttonMultiply = (Button) findViewById(R.id.buttonMultiply);
        Button buttonPlus = (Button) findViewById(R.id.buttonPlus);
        Button buttonMinus = (Button) findViewById(R.id.buttonMinus);
        Button buttonEquals = (Button) findViewById(R.id.buttonEquals);

        //set tap action to number buttons and dot button, and assign the text content on these button to EditText: NewNumber
        View.OnClickListener listener = new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Button b = (Button) view;
                NewNumber.append(b.getText().toString());
            }
        };
        button0.setOnClickListener(listener);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button8.setOnClickListener(listener);
        button9.setOnClickListener(listener);
        buttonDot.setOnClickListener(listener);


        View.OnClickListener opListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view;
                String op = b.getText().toString();
                String value = NewNumber.getText().toString();
                try{
                    Double doubleValue = Double.valueOf(value);
                    performOperation(doubleValue, op);
                }catch(NumberFormatException e)
                {
                    NewNumber.setText("");
                }
                pendingOperation = op;
                displayOperation.setText(pendingOperation);
            }


        };

        buttonEquals.setOnClickListener(opListener);
        buttonDivide.setOnClickListener(opListener);
        buttonMultiply.setOnClickListener(opListener);
        buttonPlus.setOnClickListener(opListener);
        buttonMinus.setOnClickListener(opListener);

        }

//save the operand when rotate the screen
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(State_pending_operation,pendingOperation);
        if(operand1 != null)
        {
            outState.putDouble(State_operand1, operand1);//prevent the application from crashing when rotate the screen
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pendingOperation = savedInstanceState.getString(State_pending_operation);
        operand1 = savedInstanceState.getDouble(State_operand1);
        displayOperation.setText(pendingOperation);
    }

    private void performOperation(Double value, String operation)
        {
            if(null ==operand1){
                operand1 = value;
            }
            else
            {
                //The original calculation could operate two numbers, even when the operation textView shows "=", for example, result is 5, operation is "=",
                // users could tap 5 in the NewNumer EditText then tap "+", to get 10 in the result, it may not make sense. So I edited to a more reasonable way

               /*if(pendingOperation.equals("="))
                {
                    pendingOperation = operation;
                }*/
                switch (pendingOperation)
                {
                    case "=":
                        operand1 = value;
                        break;
                    case "/":
                        if(value == 0)
                            operand1 = 0.0;
                        else
                            operand1 /= value;
                        break;
                    case "*":
                        operand1 *= value;
                        break;
                    case "-":
                        operand1 -= value;
                        break;
                    case "+":
                        operand1 += value;
                        break;
                }
            }
            result.setText(operand1.toString());
            NewNumber.setText("");

        }

}