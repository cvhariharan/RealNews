package com.brocode.mainproject;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

public class MainPageController implements Initializable{
    
    
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
    
  

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        List<String> values = Arrays.asList("one", "two", "three");

        news.setItems(FXCollections.observableList(values));
    }

    

}
