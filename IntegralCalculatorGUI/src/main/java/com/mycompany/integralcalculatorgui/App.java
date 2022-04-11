package com.mycompany.integralcalculatorgui;

import java.net.URL;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        CalculatorPane calcDisplay = new CalculatorPane();
        Scene scene = new Scene(calcDisplay, 640, 480);

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
        launch();
    }


}