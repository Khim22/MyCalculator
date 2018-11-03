package com.example.deunique.mycalculator;

import android.util.Log;

import java.util.Arrays;
import java.util.List;

public class ExpressionParser {
    private int positionOfCloseBracket;
    private int positionOfOpenBracket;
    String beforeBracket;
    String afterBracket;
    private List<String> operators =  Arrays.asList("+", "-", "×","÷");

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

    public String getExpressionAfterBracket(String fullExpression){
        return fullExpression.substring(fullExpression.indexOf(")")+1,fullExpression.length());
    }

    public String getExpressionBeforeBracket(String fullExpression){
        return fullExpression.substring(0,fullExpression.indexOf("("));
    }

    public void appendMultiplySymbolIfDoesNotExist(){
        if(!operators.contains(afterBracket.substring(0,1))){
            afterBracket = "*" + afterBracket;
        }
    }

    public String getExpressionWithinBracket(String expression){
        positionOfCloseBracket = expression.indexOf(")");
        positionOfOpenBracket = expression.indexOf("(");
        Log.i("expLength",String.valueOf(expression.length()));
        Log.i("openBrack",String.valueOf(positionOfOpenBracket));
        Log.i("closeBrack",String.valueOf(positionOfCloseBracket));

        beforeBracket = getExpressionBeforeBracket(expression);
        afterBracket = getExpressionAfterBracket(expression);
        Log.i("b4Brack",beforeBracket);
        Log.i("aftBrack",afterBracket);

        String newExpression = expression.substring(expression.indexOf("(")+1,expression.indexOf(")"));
        Log.i("newExpressionInBracket",newExpression);
        return newExpression.concat("=");
    }

    public String setExpressionAfterEvaluatingExpressionInBracket(String answerWithinBrackets, String expression){

        String newExpression;
        if(positionOfOpenBracket==0){
            if(!operators.contains(afterBracket.substring(0,1))){
                afterBracket = "*" + afterBracket;
            }
            newExpression = "+" + answerWithinBrackets + afterBracket + "=";
            Log.i("openAtStart",newExpression);
        }
        else if(positionOfCloseBracket==expression.length()-2){
            if(!operators.contains(beforeBracket.substring(beforeBracket.length()-1))){
                beforeBracket+= "*";
            }
            newExpression =  "+" + beforeBracket + String.valueOf(answerWithinBrackets)+ "=";
            Log.i("closeAtEnd",newExpression);
        }
        else{
            if(!operators.contains(afterBracket.substring(0,1))){
                afterBracket = "*" + afterBracket;
            }
            if(!operators.contains(beforeBracket.substring(beforeBracket.length()-1))){
                beforeBracket+= "*";
            }
            newExpression =  "+" + beforeBracket + String.valueOf(answerWithinBrackets)+ afterBracket + "=";
            Log.i("BrackAtMid",newExpression);
        }

        Log.i("afterBracket",newExpression);
        return newExpression;
    }
}
