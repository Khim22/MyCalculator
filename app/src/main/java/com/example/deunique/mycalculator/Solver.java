package com.example.deunique.mycalculator;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Solver {

    private List<Double> numbersForCalc;
    private List<String> operatorsForCalc;

    private List<String> operators =  Arrays.asList("+", "-", "×","÷");

    private ExpressionParser parser = new ExpressionParser();

    public double evaluate(String expression){
        expression = parser.removeEqualSignAtEnd(expression);
        while(parser.isExpressionContainingBrackets(expression)){
            //TODO: TO clean up

            if(parser.IsContainingNestedBrackets(expression)){

            }
            String withinBracket = setNumbersAndOpsList(
                    parser.getExpressionWithinBracket(expression)
            );
            //TODO: TO clean up
            Log.i("withinBrac",withinBracket);

            double answerWithinBrackets = solveReducedExpression(withinBracket);
            Log.i("ansWithinBrac",String.valueOf(answerWithinBrackets));
            //TODO: TO clean up
            expression = parser.setExpressionAfterEvaluatingExpressionInBracket(String.valueOf(answerWithinBrackets),expression);

        }
        String reducedExp = setNumbersAndOpsList(expression);
        Log.i("Solver:reducedExp", reducedExp);

        return solveReducedExpression(reducedExp);
    }

    private double solveReducedExpression(String reduced){
        solveForMultiplicationAndDivision();
        solveForAdditionAndSubtraction();

        for (double j:numbersForCalc) {
            Log.i("solvedReduced:numbers", String.valueOf(j));
        }
        return numbersForCalc.get(0);
    }

    private void solveForMultiplicationAndDivision(){
        solveForMathematicalOperations("*","/");

        for(double n: numbersForCalc){
            Log.d("num aft times divide",String.valueOf(n));
        }
        for(String s: operatorsForCalc){
            Log.d("ops aft times divide",s);
        }
    }

    private void solveForAdditionAndSubtraction(){
        solveForMathematicalOperations("+","-");

        for(double n: numbersForCalc){
            Log.d("num aft minus plus",String.valueOf(n));
        }
        for(String s: operatorsForCalc){
            Log.d("ops aft minus plus",s);
        }
    }

    private void solveForMathematicalOperations(String operator1, String operator2){
        for(int j=1; j<operatorsForCalc.size();j++){
            if(operatorsForCalc.get(j).equals(operator1)||operatorsForCalc.get(j).equals(operator2)){
                simplifyNumbersList(j,operatorsForCalc.get(j));
                operatorsForCalc.set(j,null);
            }
        }

        numbersForCalc.removeAll(Collections.singleton(null));
        operatorsForCalc.removeAll(Collections.singleton(null));
    }

    private String setNumbersAndOpsList(String expression){
        String reducedExpression = ExpressionParser.parseExpression(expression).concat("=");
        numbersForCalc = new ArrayList<Double>();
        operatorsForCalc = new ArrayList<String>();

        Log.i("reducedExp",reducedExpression);

        StringBuilder num = new StringBuilder();
        for(int i=0; i< reducedExpression.length();i++){
            String nextChar = reducedExpression.substring(i,i+1);
            Log.i("loop i",String.valueOf(i));
            Log.i("nextChar",nextChar);
            if(nextChar.matches(".*\\d+.*")||nextChar.matches("\\.")){
                num.append(nextChar);
                Log.i("numInMatch",num.toString());
            }
            else{
                Log.i("numNotMatch",num.toString());
                if(!num.toString().equals(""))
                    numbersForCalc.add(Double.parseDouble(num.toString()));
                num =  new StringBuilder();
                if(!nextChar.equals("="))
                    operatorsForCalc.add(nextChar);
            }
        }

        for(double n: numbersForCalc){
            Log.i("numbers b4",String.valueOf(n));
        }
        for(String s: operatorsForCalc){
            Log.i("operators b4",s);
        }

        return reducedExpression;
    }





    private void simplifyNumbersList(int i,String operator) {
        double num = 0;
        Log.i("simplifyNum:i(inside)", String.valueOf(i));
        Log.i("simplifyNum:op(inside)", operator);
        switch(operator){
            case "*": num = numbersForCalc.get(i-1) * numbersForCalc.get(i);break;
            case "/": num = numbersForCalc.get(i-1) / numbersForCalc.get(i);break;
            case "+": num = numbersForCalc.get(i-1) + numbersForCalc.get(i);break;
            case "-": num = numbersForCalc.get(i-1) - numbersForCalc.get(i);break;
            default: break;
        }
        Log.i("simplifyNum:num(inside)", String.valueOf(num));
        numbersForCalc.set(i,num);
        numbersForCalc.set(i-1,null);

    }

    //TODO; SCientific calculator buttons
    //sin, cos, tan - inverse
    //ln, log - e^x , 10^x
    // sto - rcl
    //x^2, x^3 - sqrt,cubert
    // x^y - yroot
    //x^-1 - x!
    //hyp - mode





}
