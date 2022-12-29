package com.example.evolutiongenerator.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class NewSimulationController implements Initializable {

    @FXML
    private ChoiceBox<String> mapVariantChoiceBox;
    @FXML
    private ChoiceBox<String> mutationVariantChoiceBox;
    @FXML
    private ChoiceBox<String> behaviourVariantChoiceBox;
    @FXML
    private ChoiceBox<String> growingVariantChoiceBox;

    @FXML
    private Label errorMessage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mapVariantChoiceBox.getItems().addAll("Globe", "HellishPortal");
        mutationVariantChoiceBox.getItems().addAll("Full randomness", "Slight correction");
        behaviourVariantChoiceBox.getItems().addAll("Full predestination", "Bit of craziness");
        growingVariantChoiceBox.getItems().addAll("Forested equators", "Toxic corpses");
    }




    @FXML
    protected void onStartButtonClick() {
        errorMessage.setVisible(true);
    }

    private void validateUserArguments() {

    }


}
