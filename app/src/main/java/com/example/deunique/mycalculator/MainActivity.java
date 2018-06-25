package com.example.deunique.mycalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView upperPanel;
    TextView lowerPanel;
    List<Integer> buttonList;
    StringBuilder sb = new StringBuilder();
    double memNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        upperPanel = findViewById(R.id.txt_upper_panel);
        lowerPanel = findViewById(R.id.txt_lower_panel);

        int[] buttons =  new int[]{R.id.btn_clear,R.id.btn_mem,R.id.btn_clear_mem,R.id.btn_delete,
                                    R.id.btn_plus,R.id.btn_1,R.id.btn_2,R.id.btn_3,
                                    R.id.btn_minus,R.id.btn_4,R.id.btn_5,R.id.btn_6,
                                    R.id.btn_multiply,R.id.btn_7,R.id.btn_8,R.id.btn_9,
                                    R.id.btn_divide,R.id.btn_0,R.id.btn_decimal_point,R.id.btn_equal};
        for(int i = 0; i<buttons.length;i++ ){
            Button button = findViewById(buttons[i]);
            button.setOnClickListener(new ButtonHandler());
        }

        sb = new StringBuilder();
        memNum = 0;

    }

    public class ButtonHandler implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Button btn = (Button)v;
            switch(btn.getId()){
                case R.id.btn_equal:
                    lowerPanel.append("=");
                    evaluate(sb.toString());
                    break;
                case R.id.btn_mem :
                    memNum = Double.parseDouble(upperPanel.getText().toString());break;
                case R.id.btn_clear_mem :
                    memNum = 0 ;break;
                case R.id.btn_delete :
                    sb.deleteCharAt(sb.length()-1) ;
                    //panel.setText(sb.toString());
                    break;
                default: {sb.append(btn.getText());
                            lowerPanel.append(btn.getText());}
            }
        }

        private double evaluate(String expression){
            double result =0;
            String reducedExpression;

            reducedExpression= expression.replace('×','*');
            reducedExpression = reducedExpression.replace('÷','/');

            //check for start of string ->
            //      if (-) ignore
            //      if num add +
            //      if / or * null
            //split string into array 1 (num) and array 2 (operators)
            //do math




            return result;
        }

    }
}
