package com.mycompany.integralcalculatorgui;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.StaticVariableSet;

import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;

public class graphPane extends Pane{
    static int maxN = 100000; //100000
    int maxDN = 1000;

    int winX = 10;
    NumberAxis X = new NumberAxis();
    NumberAxis Y = new NumberAxis();
    XYChart.Series funcSeries = new XYChart.Series();
    XYChart.Series graphFunc = new XYChart.Series(); 
    AreaChart<Number,Number> areaChart = new AreaChart<Number,Number>(X,Y);    
    double intResult;
    graphPane(String e, double lower, double upper, int n){
        if (n%2!=0)
            n = 10000;
        areaChart.setCreateSymbols(false);
        this.intResult = simpsonsData(e, lower, upper, n);
        double sigma = 1;
        double mu = 0;
        // graphFunc("(1/(" + sigma * Math.sqrt(2*Math.PI) + "))*e^(-.5 * ((x-" + mu + ")/" + sigma + ")^2)", upper, lower, n);
        areaChart.getData().add(funcSeries);
        areaChart.getData().add(graphFunc);
        getChildren().add(areaChart);
    }
    graphPane(){
        areaChart.setCreateSymbols(false);
        // double sigma = 1;
        // double mu = 0;
        // graphFunc("(1/(" + sigma * Math.sqrt(2*Math.PI) + "))*e^(-.5 * ((x-" + mu + ")/" + sigma + ")^2)", -5, 5, 100);
        areaChart.getData().add(funcSeries);
        areaChart.getData().add(graphFunc);
        getChildren().add(areaChart);
    }

    double updateIntegral(String e, double lower, double upper, int n){
        // areaChart.getData().removeAll(funcSeries);
        // funcSeries = new XYChart.Series();
        X.setAutoRanging(true);
        funcSeries.getData().clear();
        intResult = simpsonsData(e, lower, upper, n);
        // areaChart.getData().addAll(funcSeries);
        return intResult;
    }

    double updateBeta(double a, double b){
        // areaChart.getData().removeAll(funcSeries);
        // funcSeries = new XYChart.Series();
        X.setAutoRanging(true);
        funcSeries.getData().clear();
        intResult = BetaData(a, b);
        // areaChart.getData().addAll(funcSeries);
        return intResult;
    }

    double updateErf(double x){
        // areaChart.getData().removeAll(funcSeries);
        // funcSeries = new XYChart.Series();
        X.setAutoRanging(true);
        funcSeries.getData().clear();

        intResult = erfData(x);

        areaChart.getData().addAll(funcSeries);
        return intResult;
    }

    double updateNormCDF(double x, double mu, double sigma){
        // areaChart.getData().removeAll(funcSeries);
        // funcSeries.
        // funcSeries = new XYChart.Series();
        X.setAutoRanging(false);
        funcSeries.getData().clear();

        normcdfData(x, mu, sigma);
        // areaChart.getData().addAll(funcSeries);

        graphNormCDF(x, mu, sigma);
        

        return math_functions.normcdf(x, mu, sigma);
    }
    
    void normcdfData(double x, double mu, double sigma){//e^(-.5 * ((x-mu)/sigma)^2
        String norm = "(1/(" + sigma * Math.sqrt(2*Math.PI) + "))*e^(-.5 * ((x-" + mu + ")/" + sigma + ")^2)";
        simpsonsData(norm, mu-5.5*sigma, x, maxDN);
    }

    void graphNormCDF(double x, double mu, double sigma){
        double upper = mu+5*sigma;
        double lower = mu-5.5*sigma;
        X.setAutoRanging(false);
        X.setUpperBound(mu+5*sigma);
        X.setLowerBound(mu-5*sigma);
        graphFunc("(1/(" + sigma * Math.sqrt(2*Math.PI) + "))*e^(-.5 * ((x-" + mu + ")/" + sigma + ")^2)", upper, lower, maxDN);
    }

    double erfData(double x){//2/Math.sqrt(Math.PI) * simpsons("e^(-x^2)
        String expr = "2/(pi^.5) * e^(-x^2)";
        return simpsonsData(expr, 0, x, maxDN);
    }


    double BetaData(double a, double b){
        a = a-1.;
        b = b-1.;
        String expr = "x^("+a+")*(1-x)^("+b+")";
        // System.out.println(expr);
        return simpsonsData(expr, 0, 1, maxDN);
        // return simpsons("(x^(" + a + "))*((1-x)^(" + b + "))", 0., 1., 100);
        // return ((gamma(a)*gamma(b))/gamma(a+b));
    }

    void graphFunc(String expr, double upper, double lower, int n){
        X.setAutoRanging(false);
        // areaChart.getData().removeAll(graphFunc);
        graphFunc.getData().clear();
        // graphFunc = new XYChart.Series();
        if (n%2!=0)
            n = n-1;

        DoubleEvaluator ev = new DoubleEvaluator();
        StaticVariableSet<Double> variables = new StaticVariableSet<Double>();
        double[] xVals = math_functions.linspace(lower, upper, n);
        double f_x;
        for (int i = 0; i<n; i++){
            variables.set("x", xVals[i]);
            f_x = ev.evaluate(expr, variables);
            graphFunc.getData().add(new XYChart.Data(xVals[i],f_x));
        }
        
        // areaChart.getData().addAll(graphFunc);
        // X.setLowerBound(-100);
        // X.setMaxHeight(1000);
        // X.setUpperBound(100);
        
    }

    
    double simpsonsData(String expr, double lower, double upper, int n) throws InvalidNException{
        // XYChart.Series series = new XYChart.Series<>();
        try {
            int[] coeff = math_functions.makeCoeff(n);
            double[] xVals = math_functions.linspace(lower,upper,n+1);
            
//            print(xVals);
//            print(coeff);

            double dx = (upper - lower) / n;            
            
            
            DoubleEvaluator ev = new DoubleEvaluator();
            StaticVariableSet<Double> variables = new StaticVariableSet<Double>();
        
            double f_x;
            double sum = 0;
            for (int i = 0; i<n; i++){
                variables.set("x", xVals[i]); //set value of x to xVal array at index i
                f_x = ev.evaluate(expr, variables);
                // if (Math.abs(f_x*dx)<winX){
                //     intSeries.getData().add(new XYChart.Data(xVals[i],f_x*dx));
                // }
                // if (Math.abs(f_x)<winX){
                    funcSeries.getData().add(new XYChart.Data(xVals[i],f_x));
                    // System.out.println(f_x);
                // }
                   
                sum+= f_x*coeff[i]*dx/3.;;
            }
            return sum;
        } catch (InvalidNException e) {
            System.err.println(e.getMessage());
            throw e;
        }
    }

    public void clearData(){
        // areaChart.getData().removeAll(funcSeries);
        // areaChart.getData().removeAll(graphFunc);
        // funcSeries.getData().clear();
        // graphFunc.getData().clear();
    }

    private static class InvalidNException extends RuntimeException {
        InvalidNException(String message) {
        super(message);
        }
    }
}
