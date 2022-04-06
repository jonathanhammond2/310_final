package integralTest;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Equation {

    public Equation(String input){
        //need to take string input such as "e^2" and turn it into a usable
        //equation java can calculate, such as e**2
        
        //first step is to take input and find things such as ^ and replace with **
        //then need to somehow take away the string part

        //parse through the string one charachter at a time

        //Split up the string by paretheses and evaluate seperately
        //Ex. (6x^2 + 8)/4 will split (6x^2 + 8) and /4 
        //but first we need a simple arithmetic calculator
        double result = 0;
        char prevChar = ' ';
        char nextChar = ' ';
        char[] c = input.toCharArray();
        result = c[0];
        for (int i=0;i<input.length(); i++){
            if (i==0)
                prevChar = ' ';
            else prevChar = c[i-1];
            if (i==(input.length()-1))
                nextChar = ' ';
            else nextChar = c[i+1];
            System.out.print("0" + c[i]);
            
            if (c[i]=='+')
                result = result + parse(nextChar);
            if (c[i] == '*')
                result = result*parse(nextChar);
        }
        System.out.println("result: " + result);
        int i = 0;
        while (nextChar !='\''){
            
            System.out.print(" " + c[i]);
            
            if (c[i]=='+')
                result = result + parse(nextChar);
            if (c[i] == '*')
            result = result*parse(nextChar);
            
            if (i==(input.length()-1))
                nextChar = '\'';
            else nextChar = c[i+1];
            i++;
        }

        System.out.println("result: " + result);
        

    }

    double parse(char c){
        return Double.parseDouble(c + "");
    }



}
