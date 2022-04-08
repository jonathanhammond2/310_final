/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.integralcalculatorgui;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.StaticVariableSet;
/**
 *
 * @author abw04
 */
public class Simpsons {
    public static double sum(double[] arr){
        double sum = 0;
        for (double i:arr)
            sum +=i;
        return sum;
    }
    
    public static void print(double[] arr){
        for (double i:arr){
            System.out.print(" " + i);
        }
        System.out.println();
    }
    
    public static void print(int[] arr){
        for (int i:arr){
            System.out.print(" " + i);
        }
        System.out.println();
    }
    
    public static int[] makeCoeff(int n) throws InvalidNException{
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
    
    public static double simpsons(String expr, double lower, double upper, int n) throws InvalidNException{
        try {
            int[] coeff = makeCoeff(n);
            double[] xVals = linspace(lower,upper,n+1);
            
//            print(xVals);
//            print(coeff);
//            
            double dx = (upper - lower) / n;            
            
            
            DoubleEvaluator ev = new DoubleEvaluator();
            StaticVariableSet<Double> variables = new StaticVariableSet<Double>();
        
            double f_x;
            double sum = 0;
            for (int i = 0; i<n; i++){
                variables.set("x", xVals[i]); //set value of x to xVal array at index i
                f_x = ev.evaluate(expr, variables);
                sum+= f_x*coeff[i]*dx/3;
            }
            return sum;
        } catch (InvalidNException e) {
            System.err.println(e.getMessage());
            throw e;
        }
    }
    
    public static double[] linspace(double a,double b,int n){
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
    
    private static class binom_dist{
        private static int n;
        private static double p;
        binom_dist(int n, double p) throws IllegalArgumentException{
            if (n<=0)
                throw new IllegalArgumentException("n must be greater than 0");
            if (p<0||p>1)
                throw new IllegalArgumentException("p must be between 1 and 0");
        }
        static double Exp(){
            return n*p;
        }
        static double Var(){
            return n*p*(1-p);
        }
    }
}
