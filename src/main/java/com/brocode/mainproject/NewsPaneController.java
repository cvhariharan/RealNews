package com.brocode.mainproject;

import com.gluonhq.charm.glisten.control.ToggleButtonGroup;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class NewsPaneController {

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
    private Pane summarizeBtn;

}
