package com.brocode.mainproject;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

public class MainPageController{
    
    public MainPageController()
    {
        generateCards();
    }
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
    @FXML
    private ListView<String> news = new ListView<>();
    
    
    private void generateCards()
    {
        ObservableList<String> items =FXCollections.observableArrayList (
            "A", "B", "C", "D");
        news.setItems(items);
    }

    

}
