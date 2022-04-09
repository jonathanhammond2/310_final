/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.integralcalculatorgui;

import javafx.scene.control.TabPane;

/**
 *
 * @author abw04
 */
public class CalculatorPane extends TabPane{
    
    private IntegralTab intTab;
    private NormalTab normTab;
    private BinomialTab biTab;
    
    public CalculatorPane() {
        intTab = new IntegralTab("Integral");
        normTab = new NormalTab("Normal Distro");
        biTab = new BinomialTab("Binomial Distro");
        
        getTabs().addAll(intTab, normTab, biTab);
    }
}
