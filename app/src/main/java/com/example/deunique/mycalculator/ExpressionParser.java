package com.example.deunique.mycalculator;

import android.util.Log;

public class ExpressionParser {

    public static String parseExpression(String expression){
        String reducedExpression;
        reducedExpression= expression.replace('×','*');
        reducedExpression = reducedExpression.replace('÷','/');

        String firstChar = reducedExpression.substring(0,1);
        if(firstChar.matches("\\d")){
            reducedExpression = "+"+ reducedExpression ;
        }

        Log.i("parseExp:reducedExp", reducedExpression);

        return reducedExpression;
    }
}
