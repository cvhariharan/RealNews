/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brocode.mainproject;

/**
 *
 * @author JAIVRAT SAROHA
 */
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
public class SummaryController{
    
    
    @FXML
    private Label labelSummary;

    @FXML
    private Text summaryText;
    
    public void setSummaryText(String text) {
        // set text from another class
        summaryText.setTextAlignment(TextAlignment.LEFT);
        summaryText.setText(text);
    } 

    /*@Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        news = content;
        setSummaryText(news);
    }
*/
}

