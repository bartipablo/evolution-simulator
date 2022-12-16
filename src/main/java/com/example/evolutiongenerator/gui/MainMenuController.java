package com.example.evolutiongenerator.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainMenuController {
    @FXML
    private Label errorMessage;


    @FXML
    protected void onStartButtonClick() {
        errorMessage.setVisible(true);
    }

    private void validateUserArguments() {

    }

}
