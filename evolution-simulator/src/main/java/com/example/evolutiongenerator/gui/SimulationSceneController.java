package com.example.evolutiongenerator.gui;

import com.example.evolutiongenerator.Statistics;
import com.example.evolutiongenerator.SimulationEngine;
import com.example.evolutiongenerator.direction.Vector2D;
import com.example.evolutiongenerator.gui.MainSceneController;
import com.example.evolutiongenerator.gui.MapVisualizer;
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

    private static final Image selectedAnimalImage;

    static {
        try {
            selectedAnimalImage = new Image(new FileInputStream("src/main/resources/com/example/evolutiongenerator/images/animal.png"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


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
    private Label animalGenomeLabel;
    @FXML
    private Label activeGenomeLabel;
    @FXML
    private Label animalEnergyLabel;
    @FXML
    private Label numberOfPlantsEatenLabel;
    @FXML
    private Label numberOfChildrenLabel;
    @FXML
    private Label ageLabel;
    @FXML
    private Label dateOfDeathLabel;
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
    private SimulationEngine world;
    private MapVisualizer mapVisualizer;

    private IAnimal observedAnimal;

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
        if (liveAnimalsChoiceBox.getValue() != null) {
            selectObservedAnimal((String) liveAnimalsChoiceBox.getValue(), true);
        } else if (deadAnimalsChoiceBox.getValue() != null) {
            selectObservedAnimal((String) deadAnimalsChoiceBox.getValue(), false);
        }
    }

    private void selectObservedAnimal(String name, boolean isLive) throws FileNotFoundException {
        animalNameLabel.setText(name);
        Image image = selectedAnimalImage;
        animalPictureView.setImage(image);
        List<IAnimal> animalList;
        if (isLive) {
            animalList = world.getPopulation().getLiveAnimals();
        } else {
            animalList = world.getPopulation().getDeadAnimals();
        }
        for (IAnimal animal : animalList) {
            if (animal.getName().equals(name)) {
                observedAnimal = animal;
                updateAnimalStatistics();
                break;
            }
        }
        updateGuiViews();
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
            world.start();
            firstStart = false;
        } else {
            world.resumeSimulation();
        }
    }

    private void pauseSimulation() throws InterruptedException {
        world.pauseSimulation();
    }

    public void setWorld(SimulationEngine world) {
        this.world = world;
        world.addObserver(this);
//        this.thread = new Thread(world);
        this.mapVisualizer = new MapVisualizer(world.getMap(), mapGridPane);
        mapGridPane.setAlignment(Pos.CENTER);
        updateGuiViews();
        completeLiveAnimalsChoiceBox();
    }


    public void updateGuiViews() {
        try {
            Platform.runLater(this::updateMapRepresentation);
            Platform.runLater(this::updateGeneralStatistics);
            if (observedAnimal != null) Platform.runLater(this::updateAnimalStatistics);
           Thread.sleep(world.getRefreshTime());
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void updateGuiCharts() {
        Platform.runLater(this::updateCharts);
    }

    private void updateMapRepresentation() {
        if (observedAnimal != null && observedAnimal.getEnergy() > 0) {
            mapVisualizer.visualizeMap(observedAnimal.getPosition());
        } else {
            mapVisualizer.visualizeMap(new Vector2D(-1, -1));
        }
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
        populationChart.setAnimated(false);
        plantsChart.setAnimated(false);
        Statistics statistics = world.getStatistics();
        seriesA.setName("Day");
        seriesB.setName("Day");
        seriesA.getData().add(new XYChart.Data(Integer.toString(world.getSimulationDay()), statistics.getPopulationSize()));
        seriesB.getData().add(new XYChart.Data(Integer.toString(world.getSimulationDay()), statistics.getPlantsQuantity()));
        populationChart.getData().clear();
        plantsChart.getData().clear();

        populationChart.getData().add(seriesA);
        plantsChart.getData().add(seriesB);
    }

    private void updateAnimalStatistics() {
        animalGenomeLabel.setText("animal's genome: " + Arrays.toString(observedAnimal.getGenomes()));
        activeGenomeLabel.setText("active genome: " + observedAnimal.getActualGenome());
        animalEnergyLabel.setText("animal energy: " + observedAnimal.getEnergy());
        numberOfPlantsEatenLabel.setText("The number of plants eaten: " + observedAnimal.getEatenPlantsNumber());
        numberOfChildrenLabel.setText("Number of children: " + observedAnimal.getQuantityOfChildren());
        ageLabel.setText("Age: " + observedAnimal.getAge());
        dateOfDeathLabel.setText("Date of death: " + observedAnimal.getDeathDay());

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
