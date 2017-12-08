package com.brocode.mainproject;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

public class MainPageController extends Application{
    
    
    @FXML
    private Button updatePasswordBtn;

    @FXML
    private RadioButton worldCategoryBtn;

    @FXML
    private RadioButton sciCategoryBtn;

    @FXML
    private RadioButton sportsCategoryBtn;

    @FXML
    private Button logoutBtn;
    private ListView<String> news = new ListView<String>();
    
    
    private void generateCards()
    {
        
    }

    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        FXMLLoader loader = new FXMLLoader();
        String fxmlDocPath = "C:\\Users\\thero\\Documents\\NetBeansProjects\\MainProject\\src\\main\\java\\com\\brocode\\mainproject\\MainPage.fxml";
        news.getItems().add("Item 1");
        news.getItems().add("Item 2");
        news.getItems().add("Item 3");
    }

}
