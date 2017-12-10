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
    private ImageView newsImg;

    @FXML
    private Text headlineTxt;

    @FXML
    private ProgressBar trustBar;

    @FXML
    private ToggleButtonGroup buttonGroup;

    @FXML
    private ToggleButton saveBtn;

    @FXML
    private ToggleButton likeBtn;

    @FXML
    private ToggleButton unlikeBtn;

    @FXML
    private Button summarizeBtn;

    void likeNews(MouseEvent event) {
        
        news.incrementLikes();
    }

    void reportFake(MouseEvent event) {

        news.incrementFakes();
    }

    void saveNews(MouseEvent event) {

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

    void unlikeNews(MouseEvent event) {

        news.decrementLikes();
    }
    

    public void start(Stage primaryStage) throws Exception {
        
        headlineTxt.setText(news.getTitle());
        
        trustBar.setProgress(news.getFakes());
        if(trustBar.getProgress()>= 60)
        {
           trustBar.setStyle("-fx-accent: red"); 
        }
        else if(trustBar.getProgress()>=40)
        {
            trustBar.setStyle("-fx-accent: orange");
        }        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        news = MainPageController.newsArticle;
    }
}
