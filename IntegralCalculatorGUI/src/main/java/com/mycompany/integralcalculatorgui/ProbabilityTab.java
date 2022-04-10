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
    
    public ProbabilityTab(String title) {
        super(title);
        
        tabContent = new BorderPane();
        titleLabel = new Label("Calculate a Probability Using Normal Distribution");
        tabContent.setTop(titleLabel);
        
        //center contents
        centerPane = new GridPane();
        tabContent.setCenter(centerPane);
        
        inputLabel = new Label("Input: ");
        inputField = new TextField("Input");
        centerPane.add(inputLabel, 0, 0);
        centerPane.add(inputField, 1, 0);
        
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
        String helpText = "";
        if (input.toLowerCase()=="help"){
            answerLabel.setText(helpText);
        }
        else{
            double result = stringParse.parseInput(inputField.getText());
            answerLabel.setText(result+"");
        }
        
    }
}
