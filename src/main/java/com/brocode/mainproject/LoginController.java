/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brocode.mainproject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author thero
 */
public class LoginController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TextField userTxt;

    @FXML
    private PasswordField passTxt;

    @FXML
    private Button goBtn;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
     
    @FXML
    void goClicked(ActionEvent event) throws IOException 
    {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/fxml/MainPage.fxml"));
        Parent root = (Parent)fxml.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
}
