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
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
public class MainPageController implements Initializable{
    
    public static News newsArticle;
    List<String> values = new ArrayList<>();
    private HashMap<String, News>newsMap;
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
        newsMap = new HashMap<>();
        try {
            generateCards();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
       
    }
    public void generateCards() throws Exception
    {
        //String tableName = "Articles0";
        AddArticles articles = new AddArticles();
        ResultSet results = articles.selectall("Artq");
        while(results.next())
        {
            int id = results.getInt("id");
            String title = results.getString("Title");
            String author = results.getString("Author");
            String content = results.getString("Content");
            String url = results.getString("URL");
            String timestamp = results.getString("Timestamp");
            News article = new News(timestamp,title,author,content,"",url,id);
            newsMap.put(title, article);
        }
        
        for(Map.Entry e: newsMap.entrySet())
        {
            values.add((String)e.getKey());
        }
        news.setItems(FXCollections.observableList(values));
        listViewClick();
    }
    
    public void listViewClick()
    {
        news.setOnMouseClicked(new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
            System.out.println("clicked on " + news.getSelectionModel().getSelectedItem());
            String title = values.get(news.getSelectionModel().getSelectedIndex());
            News article = newsMap.get(title);
            newsArticle = article;
            try
            {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("/fxml/NewsPane.fxml"));
            Parent root = (Parent)fxml.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            }
            catch(IOException e)
            {
               e.printStackTrace();
            }
        }
    });
    }
}
