package com.mycompany.integralcalculatorgui;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.StaticVariableSet;

import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;

public class graphPane extends Pane{
    int maxN = 100000; //100000
    int winX = 10;
    final NumberAxis X = new NumberAxis();
    final NumberAxis Y = new NumberAxis();
    XYChart.Series funcSeries = new XYChart.Series();
    AreaChart<Number,Number> areaChart = new AreaChart<Number,Number>(X,Y);    
    double intResult;
    graphPane(String e, double lower, double upper, int n){
        if (n%2!=0)
            n = 10000;
        // areaChart.setTitle("Graph");
        areaChart.setCreateSymbols(false);
        // this.intResult = simpsonsData(e, lower, upper, n);
        this.intResult = BetaData(.5, .5);
        areaChart.getData().add(funcSeries);
        getChildren().add(areaChart);
    }

    double updateIntegral(String e, double lower, double upper, int n){
        areaChart.getData().removeAll(funcSeries);
        funcSeries = new XYChart.Series();
        intResult = simpsonsData(e, lower, upper, n);
        areaChart.getData().addAll(funcSeries);
        return intResult;
    }

    double updateBeta(double a, double b){
        areaChart.getData().removeAll(funcSeries);
        funcSeries = new XYChart.Series();

        a = a-1.;
        b = b-1.;
        String expr = "x^("+a+")*(1-x)^("+b+")";
        System.out.println(expr);
        intResult =  simpsonsData(expr, 0, 1, 100);

        // intResult = BetaData(lower, upper);
        areaChart.getData().addAll(funcSeries);
        return intResult;
    }

    // double updateBeta(double a, double b){
    //     areaChart.getData().removeAll(funcSeries);
    //     funcSeries = new XYChart.Series();
    //     intResult = BetaData(a, b);
    //     areaChart.getData().addAll(funcSeries);
    //     return intResult;
    // }

    AreaChart makeGraph(String expr){
        final NumberAxis X = new NumberAxis();
        final NumberAxis Y = new NumberAxis();
        X.setLabel("X");
        AreaChart<Number,Number> lineChart = 
                new AreaChart<Number,Number>(X,Y);
       
        lineChart.setTitle("Graph");
        lineChart.setCreateSymbols(false);
        lineChart.getData().add(funcSeries);
        return lineChart;
    }

    double BetaData(double a, double b){
        a = a-1.;
        b = b-1.;
        String expr = "x^("+a+")*(1-x)^("+b+")";
        System.out.println(expr);
        return simpsonsData(expr, 0, 1, 100);
        // return simpsons("(x^(" + a + "))*((1-x)^(" + b + "))", 0., 1., 100);
        // return ((gamma(a)*gamma(b))/gamma(a+b));
    }
    
    double simpsonsData(String expr, double lower, double upper, int n) throws InvalidNException{
        // XYChart.Series series = new XYChart.Series<>();
        try {
            int[] coeff = math_functions.makeCoeff(n);
            double[] xVals = math_functions.linspace(lower,upper,n+1);
            
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
                // if (Math.abs(f_x*dx)<winX){
                //     intSeries.getData().add(new XYChart.Data(xVals[i],f_x*dx));
                // }
                if (Math.abs(f_x)<winX){
                    funcSeries.getData().add(new XYChart.Data(xVals[i],f_x));
                    // System.out.println(f_x);
                }
                   
                sum+= f_x*coeff[i]*dx/3.;;
            }
            return sum;
        } catch (InvalidNException e) {
            System.err.println(e.getMessage());
            throw e;
        }
    }

    private static class InvalidNException extends RuntimeException {
        InvalidNException(String message) {
        super(message);
        }
    }
}
