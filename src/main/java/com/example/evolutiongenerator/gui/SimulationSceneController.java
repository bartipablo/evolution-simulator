package com.example.evolutiongenerator.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.nio.Buffer;

public class SimulationSceneController {

    private MainSceneController mainSceneController;
    @FXML
    private Button deleteSimulationButton;

    @FXML
    private Pane simulationPane;

    public void onDeleteSimulationButtonClicked() {
        mainSceneController.removeTab(this);
    }

    public void setMainSceneController(MainSceneController mainSceneController) {
        this.mainSceneController = mainSceneController;
    }


}
