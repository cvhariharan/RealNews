package com.brocode.mainproject;

import com.gluonhq.charm.glisten.control.ToggleButtonGroup;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class NewsPaneController implements Initializable{
        
    News news;
    
    @FXML
    private Text headlineTxt;

    @FXML
    private ProgressBar trustBar;

    @FXML
    private ToggleButtonGroup buttonGroup;

    @FXML
    private ToggleButton likeBtn;

    @FXML
    private Button summarizeBtn;
    
    @FXML
    private Button reportBtn;

    @FXML
    private Text newsTxt;

    @FXML
    void reportFake(MouseEvent event) {
        
        news.incrementFakes();
    }

    void likeNews(MouseEvent event) {
        
        news.incrementLikes();
    }

    void summarizeText(MouseEvent event) throws IOException {

        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/fxml/Summary.fxml"));
        Parent root = (Parent)fxml.load();
        SummaryController summaryController = new SummaryController();
        summaryController.setSummaryText(news.summarize(6));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
        //NewsPaneController summaryController = (NewsPaneController)fxml.getController();
        //summaryController.setSummaryText(news.summarize(6));   //pass summarized text string
        
    }

    public void start(Stage primaryStage) throws Exception {
        
        headlineTxt.setText(news.getTitle());       //sets headline text
        
        trustBar.setProgress(100);      //sets trust bar progress
        
        if(news.detect())             //changes trust bar's colour if the news is reported fake
        {
           trustBar.setStyle("-fx-accent: green"); 
        }
        else
        {
            trustBar.setStyle("-fx-accent: red");
        }  
        
        newsTxt.setText(news.content);  //sets news article text in news pane
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        news = MainPageController.newsArticle;
    }
}
