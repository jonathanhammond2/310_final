/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.integralcalculatorgui;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author abw04
 */
public class ProbabilityTab extends Tab{
    private BorderPane tabContent;
    private Label titleLabel;
    //center contents
    private GridPane centerPane;
    private Label inputLabel;
    private TextField inputField;
    

    // private Label sigmaLabel;
    // private TextField sigmaField;
    // private Label lowerLabel;
    // private TextField lowerField;
    // private Label upperLabel;
    // private TextField upperField;
    //bottom contents
    private HBox bottomBox;
    private Button calcButton;
    private Label answerLabel;
    private graphPane gPane;
    
    public ProbabilityTab(String title) {
        super(title);
        
        gPane = new graphPane();
        tabContent = new BorderPane();
        titleLabel = new Label("Probability");
        tabContent.setTop(titleLabel);
        
        //center contents
        centerPane = new GridPane();
        tabContent.setCenter(centerPane);
        
        inputLabel = new Label("Input: ");
        inputField = new TextField("Input");
        // centerPane.add(inputLabel, 0, 0);
        centerPane.add(inputField, 0, 0);
        centerPane.add(gPane, 0,1);
        
        // sigmaLabel = new Label("Standard Deviation: ");
        // sigmaField = new TextField("Input the Standard Deviation");
        // centerPane.add(sigmaLabel, 0, 1);
        // centerPane.add(sigmaField, 1, 1);
        
        // lowerLabel = new Label("Lower Bound: ");
        // lowerField = new TextField("Input the Lower Bound");
        // centerPane.add(lowerLabel, 0, 2);
        // centerPane.add(lowerField, 1, 2);
        
        // upperLabel = new Label("Upper Bound: ");
        // upperField = new TextField("Input the Upper Bound");
        // centerPane.add(upperLabel, 0, 3);
        // centerPane.add(upperField, 1, 3);
        
        //bottom contents
        bottomBox = new HBox();
        calcButton = new Button("Calculate Probability");
        calcButton.setOnAction(this::processCalcButton);
        answerLabel = new Label();
        bottomBox.getChildren().addAll(calcButton, answerLabel);
        tabContent.setBottom(bottomBox);
        setContent(tabContent);
    }
    
    private void processCalcButton(ActionEvent e) {
        String input = inputField.getText();
        String helpText = "rocks";
        if (input.toLowerCase()=="help"){
            answerLabel.setText(helpText);
        }
        else{
            double result = parseInput(inputField.getText());
            if (result != Double.POSITIVE_INFINITY && result != Double.NEGATIVE_INFINITY)
                result = round(stringParse.parseInput(inputField.getText()), 4);
            answerLabel.setText(result+"");
        }
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
    
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    double parseInput(String input){
        input = input.toLowerCase();
        double[]in = new double[10];

        int prevIndex = input.indexOf("(");

        int nextIndex = 0;
        int i = 0;

        while(nextIndex!=-1){
            nextIndex = input.indexOf(",", prevIndex+1);
            if (nextIndex>0){
                in[i] = Double.parseDouble(input.substring(prevIndex+1, nextIndex));
            }
            if (nextIndex<0){
                in[i] = Double.parseDouble(input.substring(prevIndex+1, input.indexOf(")",prevIndex+1)));
            }
            prevIndex = nextIndex;
                        i++;
        }

        if ((input.charAt(0) =='b') && (input.charAt(1)=='p')){
            return (math_functions.binom_dist.pmf((int) in[0], (int) in[1],in[2]));
        }
        else if ((input.charAt(0) =='b') && (input.charAt(1)=='c')){
            return (math_functions.binom_dist.cdf((int) in[0], (int) in[1],in[2]));
        }
        else if ((input.charAt(0) =='n') && (input.charAt(4)=='p')){
            return (math_functions.normpdf((int) in[0], (int) in[1],in[2]));
        }
        else if ((input.charAt(0) =='n') && (input.charAt(4)=='c')){
            return (math_functions.normcdf((int) in[0], (int) in[1],in[2]));
        }
        else if ((input.charAt(0) =='e') && (input.charAt(2)=='f')){
            // return math_functions.erf(in[0]);
            return gPane.updateErf(in[0]);
        }
        else if ((input.charAt(0) =='g') && (input.charAt(3)=='m')){
            return math_functions.gamma(in[0]);
        }
        else if ((input.charAt(0) =='b') && (input.charAt(1)=='e')){
            // return math_functions.Beta(in[0], in[1]);
            return gPane.updateBeta(in[0], in[1]);
        }
        else if ((input.charAt(0) =='t') && (input.charAt(1)=='p')){
            return math_functions.tpdf(in[0], (int) in[1]);
        }
        else if ((input.charAt(0) =='t') && (input.charAt(1)=='c')){
            return math_functions.tcdf(in[0], (int) in[1]);
        }
        else{
            return -1;
        }
        
        // return 0;
    }
}
