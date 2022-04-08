package com.example;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.StaticVariableSet;
import java.math.BigInteger;
// import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {   
        String expression = "e^x";
        System.out.println("simp's approx: " + simpsons(expression, 0, 4, 100000));
        binom_dist b = new binom_dist(20,.5);
        System.out.println("probability of 2 successes in 20 trials: " + binom_dist.npickk(20,2));
        System.out.println("binom pmf k = 10: " + b.pmf(10));
        System.out.println("binom cdf k <= 10: " + b.cdf(10));
        System.out.println("norm pdf mu = 0, sigma = 1, x = 10: " + normpdf(10, 0, 1));
        System.out.println("normcdf(0,0,1): " + normcdf(0,0,1));
      
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
        System.out.println();
    }
    
    static void print(int[] arr){
        for (int i:arr){
            System.out.print(" " + i);
        }
        System.out.println();
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
    
    static double simpsons(String expr, double lower, double upper, int n) throws InvalidNException{
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
    
    static double[] linspace(double a,double b,int n){
        double[] arr = new double[n];
        arr[0] = a;
        arr[n-1] = b;
        for(int i = 1; i<n-1;i++)
            arr[i] = arr[i-1]+(b-a)/(n-1);
            
        return arr;
    }
    
    static BigInteger fac(BigInteger n){
        return Factorial.bang(n);
    }
    
    static long fac(long n){
        return Factorial.bang(n);
    }
    
    private static class InvalidNException extends RuntimeException {
        InvalidNException(String message) {
        super(message);
        }
    }
    
    static double normpdf(double x, double mu, double sigma){
        return (1/((sigma*Math.sqrt(2*Math.PI)))*Math.exp(-Math.pow(x-mu, 2)/(2*Math.pow(sigma,2))));
    }

    static double normcdf(double x, double mu, double sigma){
        double result = 0;
        for (int k = 0; k<21;k++){
            result += Math.pow(-1,k)*Math.pow(x,2*k+1)/(Math.pow(2,k)*fac(k)*(2*k+1));
        }


        return (2/(Math.pow(2*Math.PI, 1/2)) * simpsons("e^((-(x-" + mu + ")^2)/(2 * " + sigma + "^2))",
                                                                                    -100,x, 100));
        // return .5+1/Math.pow(2*Math.PI, .5) + result;
    }
    
//    private static class norm_dist{
//        private double mu;
//        private double var;
//        
//    }
    
    private static class binom_dist{
        private int n;
        private double p;
        binom_dist(int n, double p) throws IllegalArgumentException{
            checkBounds(n,1,p);
            this.p = p;
            this.n = n;
        }
        
        static void checkBounds(int n, int k, double p){
            if (n<=0)
                throw new IllegalArgumentException("n must be greater than 0");
            if (p<0||p>1)
                throw new IllegalArgumentException("p must be between 1 and 0");
            if (k<0){
                throw new IllegalArgumentException("k must be greater than 0");
            }
            if (k>n){
//                throw new IllegalArgumentException("k must be less than or equal to n");
            }
        }
        
        static double pmf(int n, int k, double p){
            checkBounds(n,k,p);
            return npickk(n,k)*Math.pow(p, k)*Math.pow((1-p),(n-k));
        }
        double pmf(int k){
            checkBounds(n,k,p);
            return npickk(n,k)*Math.pow(p, k)*Math.pow((1-p),(n-k));
        }
        
        static double cdf(int k, int n, double p){
            checkBounds(k,n,p);
            double result = 0;
            for(int i=0;i<=k;i++){
                result+=npickk(n,i)*Math.pow(p,i)*Math.pow(1-p,n-i);
            }
            return result;
        }
        double cdf(int k){
            checkBounds(n,k,p);
            double result = 0;
            for(int i=0;i<=k;i++){
                result+= npickk(n,i)*Math.pow(p,i)*Math.pow(1-p,n-i);
            }
            return result;
        }
        
        static long npickk(long n, long k){
            if (k==0)
                return 1;
            if (n==0)
                return 0;
            BigInteger N = BigInteger.valueOf(n);
            BigInteger K = BigInteger.valueOf(k);
//            BigInteger denom = fac(K).multiply(fac(N.subtract(K)));
            return fac(N).divide(fac(K).multiply(fac(N.subtract(K)))).longValueExact();
//            return fac(N).divide(denom).doubleValue();
//            return fac(n)/(fac(k)*fac(n-k));
        }
        double Exp(){
            return n*p;
        }
        double Var(){
            return n*p*(1-p);
        }
    }
    
    
    
    
//        if (n<=0)
//            throw new IllegalArgumentException("n must be greater than 0");
//        if (p<0||p>1)
//            throw new IllegalArgumentException("p must be between 1 and 0");
        
//        return n*p;
}
