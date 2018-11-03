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
        //check for brackets

        while(expression.contains("(")|| expression.contains(")")){
            //TODO: TO clean up

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

    private String setNumbersAndOpsList(String expression){
        String reducedExpression = ExpressionParser.parseExpression(expression);
        numbersForCalc = new ArrayList<>();
        operatorsForCalc = new ArrayList<>();
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

    private boolean doesContainNestedBrackets(String expression){
        int countOfOpenBrackets = expression.length() - expression.replace("(", "").length();
        return countOfOpenBrackets>0;
    }

    private String reduceNestedBrackets(String expression){
        //count first instance of ")"
        //backward determine "(" --- to get the innermost () expression
        //cut out the string inside innermost () expression
        int indexOfFirstCloseBracket = expression.indexOf(")");
        int indexOfAssociatedOpenBracket = -1;
        String innerBracketExpression ="";
        for(int i= indexOfFirstCloseBracket; i>0;i--){
            if(expression.charAt(i)== '('){
                indexOfAssociatedOpenBracket = i;
                break;
            }
        }

        if(indexOfAssociatedOpenBracket>0)
            innerBracketExpression = expression.substring(indexOfAssociatedOpenBracket+1,indexOfFirstCloseBracket);


        return innerBracketExpression;
    }

    private double solveReducedExpression(String reduced){

        for(int j=1; j<operatorsForCalc.size();j++){
            if(operatorsForCalc.get(j).equals("*")||operatorsForCalc.get(j).equals("/")){
                simplifyNumbersList(j,operatorsForCalc.get(j));
                operatorsForCalc.set(j,null);
            }
        }

        numbersForCalc.removeAll(Collections.singleton(null));
        operatorsForCalc.removeAll(Collections.singleton(null));

        for(double n: numbersForCalc){
            Log.d("num aft times divide",String.valueOf(n));
        }
        for(String s: operatorsForCalc){
            Log.d("ops aft times divide",s);
        }

        for(int j=1; j<operatorsForCalc.size();j++){
            if(operatorsForCalc.get(j).equals("+")||operatorsForCalc.get(j).equals("-")){
                simplifyNumbersList(j,operatorsForCalc.get(j));
                operatorsForCalc.set(j,null);
            }
        }

        numbersForCalc.removeAll(Collections.singleton(null));
        operatorsForCalc.removeAll(Collections.singleton(null));

        for(double n: numbersForCalc){
            Log.d("num aft minus plus",String.valueOf(n));
        }
        for(String s: operatorsForCalc){
            Log.d("ops aft minus plus",s);
        }

        for (double j:numbersForCalc) {
            Log.i("solvedReduced:numbers", String.valueOf(j));
        }

        return numbersForCalc.get(0);
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
