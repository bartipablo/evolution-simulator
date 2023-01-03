package com.example.evolutiongenerator.gui;

import com.example.evolutiongenerator.Statistics;
import com.example.evolutiongenerator.World;
import com.example.evolutiongenerator.interfaces.IGuiObserver;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.Arrays;

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
    @FXML
    private HBox MainHBox;
    //------------------------------------------------------

    //Labels------------------------------------------------
    @FXML
    private Label numberOfAllAnimalsLabel;
    @FXML
    private Label numberOfAllPlantsLabel;
    @FXML
    private Label numberOfFreeFieldLabel;
    @FXML
    private Label theMostPopularGenomeLabel;
    @FXML
    private Label averageEnergyLevelLabel;
    @FXML
    private Label lifeExpectancyLabel;
    @FXML
    private Label dayLabel;

    //-----------------------------------------------------

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
        world.addObserver(this);
        this.thread = new Thread(world);
        this.mapVisualizer = new MapVisualizer(world.getMap(), mapGridPane);
        mapGridPane.setAlignment(Pos.CENTER);
        updateGui();
    }


    public void updateGui() {
        try {
            Platform.runLater(this::updateMapRepresentation);
            Platform.runLater(this::updateGeneralStatistics);
           Thread.sleep(1000);
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void updateMapRepresentation() {
        mapVisualizer.visualizeMap();
    }


    private void updateGeneralStatistics() {
        Statistics statistics = world.getStatistics();
        numberOfAllAnimalsLabel.setText("Number of all animals: " + statistics.getPopulationSize());
        numberOfAllPlantsLabel.setText("Number of all plants: " + statistics.getPlantsQuantity());
        numberOfFreeFieldLabel.setText("Number of free field: " + statistics.getFreeFieldQuantity());
        theMostPopularGenomeLabel.setText("The most popular genome: " + Arrays.toString(statistics.getTheMostPopularGenotype()));
        averageEnergyLevelLabel.setText("Average energy level: " + statistics.getAverageEnergy());
        lifeExpectancyLabel.setText("Life expectancy: " + statistics.getAverageLifeLength());
        dayLabel.setText("Day: " + world.getSimulationDay());
    }


}
