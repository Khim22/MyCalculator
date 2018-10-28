package com.example.deunique.mycalculator;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Solver {

    List<Double> numbers = new ArrayList<>();
    List<String> operators = new ArrayList<>();

    public double evaluate(String expression){
        String reducedExp = parseExpression(expression);
        Log.i("Solver:reducedExp", reducedExp);

        return solveReducedExpression(reducedExp);
    }

    private String parseExpression(String expression){
        String reducedExpression;
        reducedExpression= expression.replace('×','*');
        reducedExpression = reducedExpression.replace('÷','/');


        String firstChar = reducedExpression.substring(0,1);
        if(firstChar.matches("\\d")){
            reducedExpression = "+"+ reducedExpression ;
        }

        Log.i("parseExp:reducedExp", reducedExpression);

        StringBuilder num = new StringBuilder();
        for(int i=0; i< reducedExpression.length();i++){
            String nextChar = reducedExpression.substring(i,i+1);
            Log.i("parseExp:nextChar", nextChar);
            if(nextChar.matches(".*\\d+.*")){
                num.append(nextChar);
            }
            else{
                if(!num.toString().equals(""))
                    numbers.add(Double.parseDouble(num.toString()));
                num =  new StringBuilder();
                if(!nextChar.equals("="))
                    operators.add(nextChar);
            }
        }

        for(double n: numbers){
            Log.d("numbers b4",String.valueOf(n));
        }
        for(String s: operators){
            Log.d("operators b4",s);
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
