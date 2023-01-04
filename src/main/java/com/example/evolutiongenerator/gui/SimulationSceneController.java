package com.example.evolutiongenerator.gui;

import com.example.evolutiongenerator.Statistics;
import com.example.evolutiongenerator.World;
import com.example.evolutiongenerator.interfaces.IAnimal;
import com.example.evolutiongenerator.interfaces.IGuiObserver;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class SimulationSceneController implements IGuiObserver {

    //FXML---------------------------------------------------
    private MainSceneController mainSceneController;
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;
    @FXML
    private Button selectAnimalButton;
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
    @FXML
    private Label animalNameLabel;
    @FXML
    private ImageView animalPictureView;

    //-----------------------------------------------------

    //chart-------------------------------------------------
    @FXML
    private LineChart<?, ?> populationChart;
    @FXML
    private LineChart<?, ?> plantsChart;
    private XYChart.Series seriesA = new XYChart.Series();
    private XYChart.Series seriesB = new XYChart.Series();
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

    public void onSelectAnimalButtonClicked() throws FileNotFoundException {
        String animalName = null;
        if (liveAnimalsChoiceBox.getValue() != null) animalName = (String) liveAnimalsChoiceBox.getValue();
        if (deadAnimalsChoiceBox.getValue() != null) animalName = (String) deadAnimalsChoiceBox.getValue();
        if (animalName != null) {
            animalNameLabel.setText(animalName);
            Image image = new Image(new FileInputStream("src/main/resources/com/example/evolutiongenerator/animal.png"));
            animalPictureView.setImage(image);
        }

    }

    public void onChoiceLiveAnimalClicked() {
        deadAnimalsChoiceBox.setValue(null);
    }

    public void onChoiceDeadAnimalClicked() {
        liveAnimalsChoiceBox.setValue(null);
    }

    public void onStartButtonClicked() {
        startSimulation();
        deleteSimulationButton.setDisable(true);
        startButton.setDisable(true);
        stopButton.setDisable(false);
        selectAnimalButton.setDisable(true);
        liveAnimalsChoiceBox.setDisable(true);
        deadAnimalsChoiceBox.setDisable(true);
    }

    public void onStopButtonClicked() throws InterruptedException {
        deleteSimulationButton.setDisable(false);
        startButton.setDisable(false);
        stopButton.setDisable(true);
        selectAnimalButton.setDisable(false);
        liveAnimalsChoiceBox.setDisable(false);
        deadAnimalsChoiceBox.setDisable(false);
        pauseSimulation();
        completeLiveAnimalsChoiceBox();
        completeDeadAnimalsChoiceBox();
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
        updateGuiViews();
        completeLiveAnimalsChoiceBox();
    }


    public void updateGuiViews() {
        try {
            Platform.runLater(this::updateMapRepresentation);
            Platform.runLater(this::updateGeneralStatistics);
           Thread.sleep(1000);
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void updateGuiCharts() {
        Platform.runLater(this::updateCharts);
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

    private void updateCharts() {
        Statistics statistics = world.getStatistics();
        seriesA.setName("Day");
        seriesB.setName("Day");
        seriesA.getData().add(new XYChart.Data(Integer.toString(world.getSimulationDay()), statistics.getPopulationSize()));
        seriesB.getData().add(new XYChart.Data(Integer.toString(world.getSimulationDay()), statistics.getPlantsQuantity()));
        populationChart.getData().add(seriesA);
        plantsChart.getData().add(seriesB);
    }

    private void completeLiveAnimalsChoiceBox() {
        liveAnimalsChoiceBox.getItems().clear();
        List<IAnimal> liveAnimals = world.getPopulation().getLiveAnimals();
        for (IAnimal animal : liveAnimals) {
            liveAnimalsChoiceBox.getItems().add(animal.getName());
        }
    }

    private void completeDeadAnimalsChoiceBox() {
        deadAnimalsChoiceBox.getItems().clear();
        List<IAnimal> deadAnimals = world.getPopulation().getDeadAnimals();
        for (IAnimal animal : deadAnimals) {
            deadAnimalsChoiceBox.getItems().add(animal.getName());
        }
    }
}
