package com.example.evolutiongenerator.gui;

import com.example.evolutiongenerator.World;
import com.example.evolutiongenerator.interfaces.IMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;

import java.net.URL;
import java.nio.Buffer;
import java.util.ResourceBundle;

public class SimulationSceneController {

    //FXML---------------------------------------------------
    private MainSceneController mainSceneController;
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;
    @FXML
    private Button deleteSimulationButton;
    @FXML
    private Pane simulationPane;
    @FXML
    private ChoiceBox liveAnimalsChoiceBox;
    @FXML
    private ChoiceBox deadAnimalsChoiceBox;
    @FXML
    private GridPane mapGridPane;
    //------------------------------------------------------

    //simulation--------------------------------------------
    private World world;
    private MapVisualizer mapVisualizer;

    //------------------------------------------------------

    public void onDeleteSimulationButtonClicked() {
        mainSceneController.removeTab(this);
    }

    public void setMainSceneController(MainSceneController mainSceneController) {
        this.mainSceneController = mainSceneController;
    }

    public void onStartButtonClicked() {
        deleteSimulationButton.setDisable(true);
        startButton.setDisable(true);
        stopButton.setDisable(false);
        liveAnimalsChoiceBox.setDisable(true);
        deadAnimalsChoiceBox.setDisable(true);
    }

    public void onStopButtonClicked() {
        deleteSimulationButton.setDisable(false);
        startButton.setDisable(false);
        stopButton.setDisable(true);
        liveAnimalsChoiceBox.setDisable(false);
        deadAnimalsChoiceBox.setDisable(false);
    }

    public void setWorld(World world) {
        this.world = world;
        this.mapVisualizer = new MapVisualizer(world.getMap());
    }

    private void prepareSimulationGridPane() {
        IMap map = world.getMap();
        mapGridPane.getRowConstraints().add(new RowConstraints(map.getMapHeight()));
        mapGridPane.getColumnConstraints().add(new ColumnConstraints(map.getMapWidth()));
    }




}
