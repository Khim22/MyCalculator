package com.example.deunique.mycalculator;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView upperPanel;
    TextView lowerPanel;
    List<Integer> buttonList;
    String[] operators;
    StringBuilder sb = new StringBuilder();
    double memNum;
    static int previousButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        upperPanel = findViewById(R.id.txt_upper_panel);
        buttonList = new ArrayList<Integer>(Arrays.asList(
                R.id.btn_clear,R.id.btn_mem,R.id.btn_open_bracket,R.id.btn_close_bracket,
                R.id.btn_plus,R.id.btn_1,R.id.btn_2,R.id.btn_3,
                R.id.btn_minus,R.id.btn_4,R.id.btn_5,R.id.btn_6,
                R.id.btn_multiply,R.id.btn_7,R.id.btn_8,R.id.btn_9,
                R.id.btn_divide,R.id.btn_0,R.id.btn_decimal_point,R.id.btn_equal
        ));

        operators = new String[]{"+","-","×","÷"};

        lowerPanel = findViewById(R.id.txt_lower_panel);
        
        for(int i = 0; i<buttonList.size();i++ ){
            Button button = findViewById(buttonList.get(i));
            button.setOnClickListener(new ButtonHandler());
        }

        memNum = 0;
        sb = new StringBuilder();

    }

    public class ButtonHandler implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Button btn = (Button)v;
            Log.d("sb after click",sb.toString());
            switch(btn.getId()){
                case R.id.btn_equal:
                    sb.append("=");
                    try {
                        if (evaluate(sb.toString())) {
                            sb = new StringBuilder();
                        } else {
                            Toast.makeText(getApplicationContext(), "Invalid expression", Toast.LENGTH_SHORT).show();
                        }
                    }catch(Exception e){
                        Log.e("InvalidCalulation",e.getMessage());
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Math Error")
                                .setMessage("The calculation returned with an error. Check your syntax again!")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setCancelable(false)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                    break;
                case R.id.btn_mem :
                    try{
                        memNum = Double.parseDouble(upperPanel.getText().toString());
                    }catch(NumberFormatException e){
                        Log.e("InvalidCalulation",e.getMessage());
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Math Error")
                                .setMessage("The calculation returned with an error. Check your syntax again!")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setCancelable(false)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                    break;
                case R.id.btn_clear:
                    lowerPanel.setText("0");
                    sb = new StringBuilder();
                    break;
                default: {
                    if(previousButton==R.id.btn_equal){
                        sb = new StringBuilder(lowerPanel.getText());
                    }
                    sb.append(btn.getText());
                    if(IsPreviousButtonOperator() && IsCurrentButtonOperator()){
                        sb.setLength(sb.length() - 1);
                    }
                    else if(lowerPanel.getText().toString().equals("0")){
                        lowerPanel.setText("");
                        lowerPanel.append(btn.getText());
                    }
                    else{
                        lowerPanel.append(btn.getText());
                    }
                }
            }

            previousButton = btn.getId();
        }

        private boolean evaluate(String exp) throws InvalidCalculationException{
            double result =0;
            String reducedExpression;
            Solver solver = new Solver();

            Log.i("Main:exp",exp);

            char lastInput=exp.charAt(exp.length()-2);
            if(Character.isDigit(lastInput)||lastInput==')'){
                upperPanel.setText(lowerPanel.getText().toString());
                double answer = solver.evaluate(exp);
                lowerPanel.setText(String.valueOf(answer));

                return true;
            }
            else{
                return false;
            }

        }

        private boolean IsPreviousButtonOperator(){
            String lastInput= sb.length()>1? sb.substring(sb.length()-2, sb.length()-1): "";
            return Arrays.asList(operators).contains(lastInput);
        }

        private boolean IsCurrentButtonOperator(){
            String currentInput= sb.substring(sb.length()-1,sb.length());
            return Arrays.asList(operators).contains(currentInput);
        }


    }
}
