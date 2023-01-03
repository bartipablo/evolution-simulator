package com.example.evolutiongenerator.gui;

import com.example.evolutiongenerator.World;
import com.example.evolutiongenerator.interfaces.IGuiObserver;
import com.example.evolutiongenerator.interfaces.IMap;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;

import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.Buffer;
import java.util.ResourceBundle;

public class SimulationSceneController implements IGuiObserver {

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
    private Thread thread;

    //------------------------------------------------------
    private boolean firstStart = true;


    public void onDeleteSimulationButtonClicked() {
        mainSceneController.removeTab(this);
    }

    public void setMainSceneController(MainSceneController mainSceneController) {
        this.mainSceneController = mainSceneController;
    }

    public void onStartButtonClicked() {
        startSimulation();
        deleteSimulationButton.setDisable(true);
        startButton.setDisable(true);
        stopButton.setDisable(false);
        liveAnimalsChoiceBox.setDisable(true);
        deadAnimalsChoiceBox.setDisable(true);
    }

    public void onStopButtonClicked() throws InterruptedException {
        deleteSimulationButton.setDisable(false);
        startButton.setDisable(false);
        stopButton.setDisable(true);
        liveAnimalsChoiceBox.setDisable(false);
        deadAnimalsChoiceBox.setDisable(false);
        pauseSimulation();
    }

    private void startSimulation() {
        if (firstStart) {
            world.getMap().addGuiObserver(this);
            thread.start();
            firstStart = false;
        } else {
            thread.resume();
        }
    }

    private void pauseSimulation() throws InterruptedException {
        thread.suspend();
    }

    public void setWorld(World world) {
        this.world = world;
        world.getMap().addGuiObserver(this);
        this.thread = new Thread(world);
        this.mapVisualizer = new MapVisualizer(world.getMap());
        this.mapGridPane = new GridPane();
    }

    @Override
    public void changed() {
        try {
            Platform.runLater(this::updateScene);
            Thread.sleep(300);
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void updateScene() {
        mapGridPane = mapVisualizer.visualizeMap();
    }


    @Override
    public void generalStatisticsChanged() {

    }


}
