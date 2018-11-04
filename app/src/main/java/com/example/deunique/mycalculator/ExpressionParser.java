package com.example.deunique.mycalculator;

import android.util.Log;

import java.util.Arrays;
import java.util.List;

public class ExpressionParser {
    private int positionOfCloseBracket;
    private int positionOfOpenBracket;
    static String beforeBracket;
    static String afterBracket;
    private List<String> operators =  Arrays.asList("+", "-", "*","/");


    private String getExpressionAfterBracket(String fullExpression){
        return fullExpression.substring(fullExpression.indexOf(")")+1,fullExpression.length());
    }

    private String getExpressionBeforeBracket(String fullExpression){
        return fullExpression.substring(0,fullExpression.indexOf("("));
    }

    private boolean isFirstCharOfAfterBracketExpressionOperator(String afterBracket){
        return afterBracket!=null && !afterBracket.equals("") && operators.contains(afterBracket.substring(0,1));
    }

    private boolean isLastCharOfBeforeBracketExpressionOperator(String beforeBracket){
        if(beforeBracket!=null && beforeBracket.length()>1)
            Log.i("b4BrackLastcharT/F",String.valueOf(operators.contains(beforeBracket.substring(beforeBracket.length()-1))));
        return beforeBracket!=null && !beforeBracket.equals("") && operators.contains(beforeBracket.substring(beforeBracket.length()-1));
    }

    private boolean isFirstCharOfBeforeBracketPositiveSign(String beforeBracket){
        return beforeBracket!=null && !beforeBracket.equals("") && beforeBracket.charAt(0)=='+';
    }

    private void ensureBeforeAndAfterBracketExpressionContainsOperator(){
        if(!isFirstCharOfAfterBracketExpressionOperator(afterBracket) && !afterBracket.equals("")){
            afterBracket = "*" + afterBracket;
        }
        if(!isLastCharOfBeforeBracketExpressionOperator(beforeBracket) && !beforeBracket.equals("")){
            beforeBracket+= "*";
        }
        if(isFirstCharOfBeforeBracketPositiveSign(beforeBracket))
            beforeBracket = beforeBracket.substring(1,beforeBracket.length());
    }

    //Public methods

    public String removeEqualSignAtEnd(String expression){
        return expression.charAt(expression.length()-1)=='='?
                    expression.substring(0,expression.length()-1):
                    expression;
    }

    public boolean isExpressionContainingBrackets(String expression){
        return expression.contains("(")|| expression.contains(")");
    }

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
        return newExpression;
    }

    public String setExpressionAfterEvaluatingExpressionInBracket(String answerWithinBrackets, String expression){

        String newExpression;
        Log.i("before ensure b4Brac",beforeBracket);
        Log.i("before ensure aftBrac",afterBracket);
        ensureBeforeAndAfterBracketExpressionContainsOperator();
        Log.i("aft ensure b4Brac",beforeBracket);
        Log.i("aft ensure aftBrac",afterBracket);

        newExpression =  "+" + beforeBracket + String.valueOf(answerWithinBrackets)+ afterBracket;
        Log.i("BrackAtMid",newExpression);

        Log.i("afterBracket",newExpression);
        return newExpression;
    }

    public boolean IsContainingNestedBrackets(String expression){
        boolean isContain = false;
        for(int i =0,countOfOpenBracket=0,countOfCloseBracket=0;i<expression.length();i++){
            if(expression.charAt(i)=='(')
                countOfOpenBracket++;
            if(expression.charAt(i)==')')
                countOfCloseBracket++;
            if(countOfOpenBracket>countOfCloseBracket+2){
                isContain = true;
                break;
            }
        }
        return isContain;
    }


    private String getExpressionInNestedBrackets(String expression){
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

        if(indexOfAssociatedOpenBracket>0){}
            innerBracketExpression = expression.substring(indexOfAssociatedOpenBracket+1,indexOfFirstCloseBracket);
        beforeBracket = expression.substring(0,indexOfAssociatedOpenBracket);
        afterBracket = expression.substring(indexOfFirstCloseBracket+1);

        return innerBracketExpression;
    }
}
