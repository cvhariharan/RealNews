/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brocode.mainproject;

import java.awt.*;
import javafx.scene.text.Font;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author JAIVRAT SAROHA
 */
public class MainApp extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Button btnSignUp1 = new Button();
        btnSignUp1.setText("Sign Up?");
        btnSignUp1.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Go to Sign Up page");
            }
        });
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 300, 275);
        
        HBox hbButtons = new HBox();
        hbButtons.setSpacing(10.0);

        Button btnSignUp = new Button("Sign Up");
        Button btnGo = new Button("Go");
        Button btnExit = new Button("Exit");
        //btnSignUp.setStyle("-fx-font-size: 15pt;");
        
        Text scenetitle = new Text("News");
        Text scenetitle1 = new Text("Reader");
        scenetitle.setFont(Font.font("Big John", FontWeight.NORMAL, 36));
        scenetitle1.setFont(Font.font("Slim Joe", FontWeight.NORMAL, 36));
        grid.add(scenetitle, 0, 0, 1, 1);
        grid.add(scenetitle1, 1, 0, 1, 1);

        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);
        
        GridPane innergrid = new GridPane();
        innergrid.setAlignment(Pos.CENTER);
        innergrid.add(hbButtons, 0, 0);
        grid.add(innergrid, 0, 2, 2, 1);
        hbButtons.setAlignment(Pos.BOTTOM_CENTER);
        hbButtons.getChildren().addAll(btnSignUp, btnGo, btnExit);
        grid.add(hbButtons, 1, 3, 2, 1);
            
        primaryStage.show();
      
        primaryStage.setScene(scene);

        
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
