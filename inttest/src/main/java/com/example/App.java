package com.example;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.StaticVariableSet;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        DoubleEvaluator e = new DoubleEvaluator();
        String expression = "x^2";
        String simp;
        Scanner scan = new Scanner(System.in);
        StaticVariableSet<Double> variables = new StaticVariableSet<Double>();
        double a = 0;
        double b = 10;
        variables.set("x", a);
        double f_a = e.evaluate(expression, variables);
        
        variables.set("x", b);
        double f_b = e.evaluate(expression, variables);
        
        
        print(linspace(0,100,21));
        
        
        
    }
    
    static double sum(double[] arr){
        double sum = 0;
        for (double i:arr)
            sum +=i;
        return sum;
    }
    
    static void print(double[] arr){
        for (double i:arr){
            System.out.print(" " + i);
        }
    }
    
    static void print(int[] arr){
        for (int i:arr){
            System.out.print(" " + i);
        }
    }
    
    static int[] makeCoeff(int n) throws InvalidNException{
        if (n % 2 == 0) {
            int[] coeff = new int[n];
            coeff[0] = 1;
            coeff[n-1] = 1;

            for (int i=1; i<n-1; i++){
                if (i%2 == 0)
                    coeff[i] = 2;
                else coeff[i] = 4;
            }
            
            return coeff;
        } else  {
            throw new InvalidNException("N must be an even number");
        }
        //simpson's approximation
        
        
    }
    
    static void simpsons(String expr, int n, double lower, double upper) {
        try {
            int[] coeff = makeCoeff(n);
            double[] xVals = linspace(lower,upper,n+1);
            
            print(xVals);
            print(coeff);
            
            double dx = (upper - lower) / n;
            double sum = 0;
            for (int i = 0; i<n; i++){
                sum+=
            }
        } catch (InvalidNException e) {
            System.err.println(e.getMessage());
        }
    }
    
    static double[] linspace(double a,double b,int n){
        double[] arr = new double[n];
        arr[0] = a;
        arr[n-1] = b;
        for(int i = 1; i<n-1;i++)
            arr[i] = arr[i-1]+(b-a)/(n-1);
            
        return arr;
    }
    
    private static class InvalidNException extends RuntimeException {
        InvalidNException(String message) {
        super(message);
        }
    }
}
