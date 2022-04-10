package com.mycompany.linetest2;

import java.math.BigInteger;
import java.net.URL;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.StaticVariableSet;

import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {
    static final int maxN = 100000;
    static final int winX = 10;
    XYChart.Series intSeries = new XYChart.Series();
    XYChart.Series funcSeries = new XYChart.Series();


    @Override public void start(Stage stage) {
        stage.setTitle("Line Chart Sample");
        final NumberAxis X = new NumberAxis();
        final NumberAxis Y = new NumberAxis();
        X.setLabel("X");
        AreaChart<Number,Number> lineChart = 
                new AreaChart<Number,Number>(X,Y);
       
        lineChart.setTitle("Graph");
        lineChart.setCreateSymbols(false);
        
        String expr = "x^2";
        
        // series1.setName("f(x)");
        // XYChart.Series series = generateData();
        funcSeries.setName("f(x) = " + expr);
        intSeries.setName("int(" + expr+")");

        System.out.println("int of " + expr + " from 0 to 10 is: " + simpsonsData(expr, -10, 10, 1000));

        // graph g = new graph(graph, range)
        math_functions.simpsons("e^x", -100, 100, maxN);
        
        Scene scene  = new Scene(lineChart,800,600);       
        lineChart.getData().addAll(funcSeries, intSeries);

        URL url = this.getClass().getResource("chart.css");
        if (url == null) {
            System.out.println("Resource not found. Aborting.");
            System.exit(-1);
        }
        String css = url.toExternalForm();
        System.out.println(css); 
        scene.getStylesheets().add(css);
       
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }    

    // public XYChart.Series generateData(){
    //     XYChart.Series series = new XYChart.Series<>();
    //     // series.getData().add(new XYChart.Data(1,2));
    //     return simpsonsData("e^x", 0, 10, 1000);
    //     }

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
                    System.out.println(f_x);
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