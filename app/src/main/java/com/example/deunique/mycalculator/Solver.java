package com.example.deunique.mycalculator;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solver {

    private List<Double> numbers;
    private List<String> operators;

    public double evaluate(String expression){
        //check for brackets

        if(expression.contains("(")|| expression.contains(")")){
            //TODO: TO clean up
            int positionOfOpenBracket = expression.indexOf("(");
            Log.i("openBrack",String.valueOf(positionOfOpenBracket));
            int positionOfCloseBracket = expression.indexOf(")");
            Log.i("closeBrack",String.valueOf(positionOfCloseBracket));
            Log.i("expLength",String.valueOf(expression.length()));
            String beforeBracket = expression.substring(0,expression.indexOf("("));
            Log.i("b4Brack",beforeBracket);
            String afterBracket = expression.substring(expression.indexOf(")")+1,expression.length());
            Log.i("aftBrack",afterBracket);

            String withinBracket = setNumbersAndOpsList(
                expression.substring(expression.indexOf("(")+1,expression.indexOf(")"))+"="
            );
            //TODO: TO clean up
            withinBracket+="=";
            Log.d("withinBrac",withinBracket);
            double answerWithinBrackets = solveReducedExpression(withinBracket);
            Log.d("ansWithinBrac",String.valueOf(answerWithinBrackets));
            //TODO: TO clean up

            if(positionOfOpenBracket==0){
                expression = "+" + String.valueOf(answerWithinBrackets)+ "*" + afterBracket + "=";
                Log.i("openAtStart",expression);
            }
            else if(positionOfCloseBracket==expression.length()-2){
                expression =  "+" + beforeBracket +"*"+ String.valueOf(answerWithinBrackets)+ "=";
                Log.i("closeAtEnd",expression);
            }

            Log.i("afterBracket",expression);
        }
        String reducedExp = setNumbersAndOpsList(expression);
        Log.i("Solver:reducedExp", reducedExp);

        return solveReducedExpression(reducedExp);
    }

    private String setNumbersAndOpsList(String expression){
        String reducedExpression = ExpressionParser.parseExpression(expression);
        numbers = new ArrayList<>();
        operators = new ArrayList<>();
        Log.i("reducedExp",reducedExpression);
        StringBuilder num = new StringBuilder();
        for(int i=0; i< reducedExpression.length();i++){
            String nextChar = reducedExpression.substring(i,i+1);
            Log.i("i",String.valueOf(i));
            Log.i("nextChar",nextChar);
            if(nextChar.matches(".*\\d+.*")||nextChar.matches("\\.")){
                num.append(nextChar);
                Log.i("numInMatch",num.toString());
            }
            else{
                Log.i("numNotMatch",num.toString());
                if(!num.toString().equals(""))
                    numbers.add(Double.parseDouble(num.toString()));
                num =  new StringBuilder();
                if(!nextChar.equals("="))
                    operators.add(nextChar);
            }
        }

        for(double n: numbers){
            Log.i("numbers b4",String.valueOf(n));
        }
        for(String s: operators){
            Log.i("operators b4",s);
        }

        return reducedExpression;
    }

    private double solveReducedExpression(String reduced){

        for(int j=1; j<operators.size();j++){
            if(operators.get(j).equals("*")||operators.get(j).equals("/")){
                simplifyNumbersList(j,operators.get(j));
                operators.set(j,null);
            }
        }

        numbers.removeAll(Collections.singleton(null));
        operators.removeAll(Collections.singleton(null));

        for(double n: numbers){
            Log.d("num aft times divide",String.valueOf(n));
        }
        for(String s: operators){
            Log.d("ops aft times divide",s);
        }

        for(int j=1; j<operators.size();j++){
            if(operators.get(j).equals("+")||operators.get(j).equals("-")){
                simplifyNumbersList(j,operators.get(j));
                operators.set(j,null);
            }
        }

        numbers.removeAll(Collections.singleton(null));
        operators.removeAll(Collections.singleton(null));

        for(double n: numbers){
            Log.d("num aft minus plus",String.valueOf(n));
        }
        for(String s: operators){
            Log.d("ops aft minus plus",s);
        }

        for (double j:numbers) {
            Log.i("solvedReduced:numbers", String.valueOf(j));
        }

        return numbers.get(0);
    }

    private void simplifyNumbersList(int i,String operator) {
        double num = 0;
        Log.i("simplifyNum:i(inside)", String.valueOf(i));
        Log.i("simplifyNum:op(inside)", operator);
        switch(operator){
            case "*": num = numbers.get(i-1) * numbers.get(i);break;
            case "/": num = numbers.get(i-1) / numbers.get(i);break;
            case "+": num = numbers.get(i-1) + numbers.get(i);break;
            case "-": num = numbers.get(i-1) - numbers.get(i);break;
            default: break;
        }
        Log.i("simplifyNum:num(inside)", String.valueOf(num));
        numbers.set(i,num);
        numbers.set(i-1,null);

    }


}
