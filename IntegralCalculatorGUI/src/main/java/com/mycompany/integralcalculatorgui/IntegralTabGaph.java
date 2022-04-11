/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.integralcalculatorgui;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 *
 * @author abw04
 */
public class IntegralTabGaph extends Tab{
    private BorderPane tabContent;
    //top
    private Label titleLabel;
    //bottom
    private HBox bottomBox;
    private Button calcButton;
    private Label answerLabel;
    //center
    private GridPane centerPane;
    private graphPane graphPane;
    // private Pane graphPane;
    private Label expressionLabel;
    private TextField expressionField;
    private Label lowerLabel;
    private TextField lowerField;
    private Label upperLabel;
    private TextField upperField;
    private Label nLabel;
    private TextField nField;
    
    //Error Handling
    private Label nErrorLabel;
    private Label illegalBoundLabel;
    private Label illegalVarLabel;
    
    public IntegralTabGaph(String title) {
        super(title);
        
        //error handling
        nErrorLabel = new Label();
        illegalBoundLabel = new Label();
        illegalVarLabel = new Label();
        graphPane = new graphPane("x^2",-10,10,100);
        
        //the tab will consist of a BorderPane
        //with other layouts layered over top
        tabContent = new BorderPane();
        
        //title of the tab in the top
        titleLabel = new Label("Calculate an Integral");
        tabContent.setTop(titleLabel);
        
        
        //center will consist of a GridPane
        //containing the user input fields
        centerPane = new GridPane();
        expressionLabel = new Label("f(x) = ");
        
        expressionField = new TextField("x^2");
        
        centerPane.add(expressionLabel, 0, 0);
        centerPane.add(expressionField, 1, 0);
        centerPane.add(illegalVarLabel, 2, 0);
        
        lowerLabel = new Label("Lower Limit of Integration: ");
        lowerField = new TextField("-10");
        centerPane.add(lowerLabel, 0, 1);
        centerPane.add(lowerField, 1, 1);
        
        upperLabel = new Label("Upper Limit of Integration: ");
        upperField = new TextField("10");
        centerPane.add(upperLabel, 0, 2);
        centerPane.add(upperField, 1, 2);
        centerPane.add(illegalBoundLabel, 2, 2);
        
        nLabel = new Label("Enter an even 'n' Value (Higher n = More Accuracy): ");
        nField = new TextField("1000");
        centerPane.add(nLabel, 0, 3);
        centerPane.add(nField, 1, 3);
        centerPane.add(nErrorLabel, 2, 3);
        centerPane.add(graphPane,0,4);
        
        
        tabContent.setCenter(centerPane);
        
        //The bottom of the borderpane will consist of the button
        //to calculate and the Label where the answer will be displayed
        calcButton = new Button("Approximate Integral");
        calcButton.setOnAction(this::processCalcButton);
        answerLabel = new Label();
        bottomBox = new HBox();
        bottomBox.getChildren().add(calcButton);
        bottomBox.getChildren().add(answerLabel);
        
        tabContent.setBottom(bottomBox);
        
        
        setContent(tabContent);
    }
    
    private void processCalcButton(ActionEvent e) {
        
        try {
            String expr = expressionField.getText();
            // double lower = Double.parseDouble(lowerField.getText());
            // double upper = Double.parseDouble(upperField.getText());
            double lower = parseIntLimit(lowerField.getText());
            double upper = parseIntLimit(upperField.getText());
            int n = Integer.parseInt(nField.getText());
            double answer = graphPane.updateIntegral(expr, lower, upper,n);
            answerLabel.setText("" + answer);
            bottomBox.getChildren().add(answerLabel);
        } catch (Simpsons.InvalidNException exc) {
            nErrorLabel.setText(exc.getMessage());
        } catch (NumberFormatException exc) {
            illegalBoundLabel.setText("Limits of Integration Must Be Numbers");
        } catch (IllegalArgumentException exc) {
            illegalVarLabel.setText("Only Use 'x' as a Variable");
        }
    }

    private double parseIntLimit(String s) throws NumberFormatException{
        if (s.equals("pi"))
            return Math.PI;
        else if (s.equals("e"))
            return Math.E;
        else return Double.parseDouble(s);
    }
}
