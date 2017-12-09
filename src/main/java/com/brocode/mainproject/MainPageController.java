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
import com.ayush.jdbc.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
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
        try {
            generateCards();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        List<String> values = Arrays.asList("one", "two", "three");
        news.setItems(FXCollections.observableList(values));
    }
    public void generateCards() throws Exception
    {
        String tableName = "Articles0";
        Jdbc conn = new Jdbc();
        ResultSet results = conn.get("SELECT * FROM "+tableName+" WHERE 1 = 1");
        while(results.next())
        {
            int id = results.getInt("id");
            String title = results.getString("Title");
            String content = results.getString("Content");
            String timestamp = results.getString("Date");
            String author = results.getString("Author");
            String url = results.getString("Link");
            News news = new News(timestamp,title,author,content,"",url,id);
            
        }
    }
    

}
