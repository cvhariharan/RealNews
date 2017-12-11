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
import com.arko.javaproject.*;
import javafx.event.ActionEvent;
import javafx.scene.control.ToggleGroup;
public class MainPageController implements Initializable{
    
    public static News newsArticle;
    ToggleGroup radioGroup = new ToggleGroup();
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
        
        worldCategoryBtn.setToggleGroup(radioGroup);
        sciCategoryBtn.setToggleGroup(radioGroup);
        sportsCategoryBtn.setToggleGroup(radioGroup);
        worldCategoryBtn.setSelected(true);
        newsMap = new HashMap<>();
        try {
            generateCards();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
       
    }
    public void generateCards() throws Exception
    {
        RadioButton selectedRadioButton = (RadioButton) radioGroup.getSelectedToggle();
        String toggleGroupValue = selectedRadioButton.getText();
        System.out.println(toggleGroupValue);
        String category = "";
        if(toggleGroupValue.equalsIgnoreCase("World"))
        category = "World";
        if(toggleGroupValue.equalsIgnoreCase("Science & Tech"))
        category = "SciTech";
        if(toggleGroupValue.equalsIgnoreCase("Sports"))
        category = "Sports";
        //String username = "test";
        String tableName = "newsarticles";
        AddArticles a = new AddArticles("root","","jdbc:mysql://localhost/database");
        /*Recommend r=new Recommend(a.getnewsObject(a)); 
        SQLiteJDBC sq = new SQLiteJDBC();
        int idArticle=sq.getlikedArticle(username);//the username of the user whoose data is to be found 
        ResultSet rs=a.select(tableName,"id = '"+idArticle+"'");
       
        byte[] data=rs.getBytes("NewsArticle");
      
        com.arko.javaproject.NewsArticle newsObj=ObjectString.getObject(data);
        ArrayList<String> newsId=r.recommend(newsObj);
        
       for(String i:newsId){
           ResultSet result=a.select(tableName,"id = '"+i+"'");
           
            int id = result.getInt("id");
            String title = result.getString("Title");
            String author = result.getString("Author");
            String content = result.getString("Content");
            String url = result.getString("URL");
            String timestamp = result.getString("Timestamp");
            News article = new News(timestamp,title,author,content,"",url,id);
            newsMap.put(title, article);
            values.add(title);
       }*/
        ResultSet results = a.selectall(tableName);
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
      @FXML
    void refresh(ActionEvent event) throws Exception {
        values.clear();
        newsMap.clear();
        generateCards();
    }
}
