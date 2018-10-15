package com.example.deunique.mycalculator;

import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Solver {

    List<Integer> numbers = new ArrayList<>();
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
                    numbers.add(Integer.parseInt(num.toString()));
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



        //check for start of string ->
        //      if (-) ignore
        //      if num add +
        //      if / or * null
        //split string into array 1 (num) and array 2 (operators)
        //do math

        return reducedExpression;
    }

    private double solveReducedExpression(String reduced){

        Iterator<Integer> numbersIterator = numbers.iterator();
        Iterator<String> operatorIterator = operators.iterator();

        int i =0;
        while(operatorIterator.hasNext()){
            String op = operatorIterator.next();
            if(operators.indexOf("*")!=0 || operators.indexOf("/")!=0){
                Log.i("solvedReduced:i(before)", String.valueOf(i));
                if(op.equals("*") || op.equals("/")){
                    simplifyNumbersList(i,op);
                    operators.remove(op);
                    i--;
                }
                i++;
                Log.i("solvedReduced:i(after)", String.valueOf(i));
//
//                for (int j:numbers) {
//                    Log.i("solvedReduced:numbers", String.valueOf(j));
//                }
            }

        }

        for(double n: numbers){
            Log.d("numbers mid",String.valueOf(n));
        }
        for(String s: operators){
            Log.d("operators mid",s);
        }

        numbersIterator = numbers.iterator();
        operatorIterator = operators.iterator();

        i=0;
        while(operatorIterator.hasNext() && operators.size()>1){
            String op = operatorIterator.next();
            double n = numbersIterator.next();
            if(operators.indexOf("+")!=0 || operators.indexOf("-")!=0){
                Log.i("solvedReduced:i(before)", String.valueOf(i));
                if((op.equals("+") || op.equals("-")) && i>0){
                    simplifyNumbersList(i,op);
                    operators.remove(op);
                    i--;
                }
                i++;
                Log.i("solvedReduced:i(after)", String.valueOf(i));
//
//                for (int j:numbers) {
//                    Log.i("solvedReduced:numbers", String.valueOf(j));
//                }
            }

        }

        for(double n: numbers){
            Log.d("numbers aft",String.valueOf(n));
        }
        for(String s: operators){
            Log.d("operators aft",s);
        }




        for (int j:numbers) {
            Log.i("solvedReduced:numbers", String.valueOf(j));
        }

        return numbers.get(0);
    }

    private void simplifyNumbersList(int i,String operator) {
        int num = 0;
        Log.i("simplifyNum:i(inside)", String.valueOf(i));
        switch(operator){
            case "*": num = numbers.get(i) * numbers.get(i-1);break;
            case "/": num = numbers.get(i) / numbers.get(i-1);break;
            case "+": num = numbers.get(i) + numbers.get(i-1);break;
            case "-": num = numbers.get(i) - numbers.get(i-1);break;
            default: break;

        }
        numbers.set(i-1,num);
        numbers.remove(i);
        for(double n: numbers){
            Log.d("numbers",String.valueOf(n));
        }
    }


}
