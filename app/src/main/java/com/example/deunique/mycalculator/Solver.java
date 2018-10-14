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

            if(operators.indexOf("*")!=0 || operators.indexOf("/")!=0){
                Log.i("solvedReduced:i(before)", String.valueOf(i));
                String op = operatorIterator.next();
                if(op.equals("*")){
                    Log.i("solvedReduced:i(in *)", String.valueOf(i));
                    int num = numbers.get(i) * numbers.get(i-1);
                    numbers.set(i-1,num);
                    numbers.remove(i);
                    i--;
                }
                else if(op.equals("/")){
                    Log.i("solvedReduced:i(in /)", String.valueOf(i));
                    int num = numbers.get(i-1) / numbers.get(i);
                    numbers.set(i-1,num);
                    numbers.remove(i);
                    i--;
                }
                i++;
                Log.i("solvedReduced:i(after)", String.valueOf(i));

                for (int j:numbers) {
                    Log.i("solvedReduced:numbers", String.valueOf(j));
                }

                boolean test = operators.indexOf("*")!=0 || operators.indexOf("/")!=0;
                Log.i("solvedReduced:numbers", String.valueOf(test));
            }



        }


        for (int j:numbers) {
            Log.i("solvedReduced:numbers", String.valueOf(j));
        }

        return numbers.get(0);
    }

}
