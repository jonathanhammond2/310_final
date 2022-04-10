package com.mycompany.integralcalculatorgui;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.StaticVariableSet;

import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;

public class graphPane extends Pane{
    static final int maxN = 100; //100000
    static final int winX = 10;
    final NumberAxis X = new NumberAxis();
    final NumberAxis Y = new NumberAxis();
    XYChart.Series funcSeries = new XYChart.Series();
    AreaChart<Number,Number> areaChart = new AreaChart<Number,Number>(X,Y);    
    String expr;
    double lower, upper, intResult;

    graphPane(String e, double lower, double upper, int n){
        this.expr = e;
        this.lower = lower;
        this.upper = upper;
        areaChart.setTitle("Graph");
        areaChart.setCreateSymbols(false);
        intResult = simpsonsData(expr, lower, upper, maxN);
        areaChart.getData().add(funcSeries);
        getChildren().add(areaChart);
    }

    double updateGraph(String e, double lower, double upper){
        // this.expr = e;
        // this.lower = lower;
        // this.upper = upper;
        areaChart.getData().removeAll(funcSeries);
        funcSeries = new XYChart.Series();
        intResult = simpsonsData(e, lower, upper, maxN);
        areaChart.getData().addAll(funcSeries);
        return intResult;
    }

    AreaChart makeGraph(String expr){
        final NumberAxis X = new NumberAxis();
        final NumberAxis Y = new NumberAxis();
        X.setLabel("X");
        AreaChart<Number,Number> lineChart = 
                new AreaChart<Number,Number>(X,Y);
       
        lineChart.setTitle("Graph");
        lineChart.setCreateSymbols(false);
        lineChart.getData().add(funcSeries);
        // System.out.println("int of " + expr + " from 0 to 10 is: " + simpsonsData(expr, -10, 10, 1000));

        return lineChart;
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
