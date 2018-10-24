package com.example.deunique.mycalculator;

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
    StringBuilder sb = new StringBuilder();
    double memNum;
    static int previousButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        upperPanel = findViewById(R.id.txt_upper_panel);
        buttonList = new ArrayList<Integer>(Arrays.asList(
                R.id.btn_clear,R.id.btn_mem,R.id.btn_clear_mem,R.id.btn_delete,
                R.id.btn_plus,R.id.btn_1,R.id.btn_2,R.id.btn_3,
                R.id.btn_minus,R.id.btn_4,R.id.btn_5,R.id.btn_6,
                R.id.btn_multiply,R.id.btn_7,R.id.btn_8,R.id.btn_9,
                R.id.btn_divide,R.id.btn_0,R.id.btn_decimal_point,R.id.btn_equal
        ));

        lowerPanel = findViewById(R.id.txt_lower_panel);
        lowerPanel.setText("0");
        
        for(int i = 0; i<buttonList.size();i++ ){
            Button button = findViewById(buttonList.get(i));
            button.setOnClickListener(new ButtonHandler());
        }


        memNum = 0;

    }

    public class ButtonHandler implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Button btn = (Button)v;
            sb = new StringBuilder();
            sb.append(lowerPanel.getText());

            switch(btn.getId()){
                case R.id.btn_equal:
                    lowerPanel.append("=");
                    if(!evaluate(sb.toString()))
                        Toast.makeText(getApplicationContext(),"Invalid expression",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_mem :
                    memNum = Double.parseDouble(upperPanel.getText().toString());break;
                case R.id.btn_clear_mem :
                    memNum = 0 ;break;
                case R.id.btn_delete :
                    sb.deleteCharAt(sb.length()-1) ;
                    lowerPanel.setText(sb.toString());
                    break;
                case R.id.btn_clear:
                    lowerPanel.setText("0");
                    break;
                default: {
                    appendText(btn.getText().toString());
//                    sb.append(btn.getText());
//                    lowerPanel.append(btn.getText());
                }
            }

            previousButton = btn.getId();
        }

        private boolean evaluate(String expression){
            double result =0;
            String reducedExpression;
            Solver solver = new Solver();

            String exp = lowerPanel.getText().toString();
            Log.i("Main:exp",exp);

            char lastInput=exp.charAt(exp.length()-2);
            if(Character.isDigit(lastInput)){
                upperPanel.setText(lowerPanel.getText().toString());
                double answer = solver.evaluate(exp);
                lowerPanel.setText(String.valueOf(answer));

                return true;
            }
            else{
                return false;
            }

        }

        private void appendText(String input){
            Log.d("panel",String.valueOf(lowerPanel.getText()));
            if(lowerPanel.getText().toString().equals("0"))
                lowerPanel.setText("");
            sb.append(input);
            Log.d("sb",sb.toString());
            lowerPanel.append(input);
        }

    }
}
