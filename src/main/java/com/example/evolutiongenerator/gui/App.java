package com.example.evolutiongenerator.gui;

import com.example.evolutiongenerator.variants.BehaviourVariant;
import com.example.evolutiongenerator.variants.MapVariant;
import com.example.evolutiongenerator.variants.MutationVariant;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {

    }

    public static void runSimulation(int mapHeight, int mapWidth, int grassEnergyProfit, int minEnergyToCopulation, int animalStartEnergy,
                                     int dailyEnergyCost, int quantityAnimals, int grassSpawnedAtEachDay, int refreshTime,
                                     BehaviourVariant behaviourVariant, MapVariant mapVariant, MutationVariant mutationVariant) {

    }

    private Scene prepareSimulationScene(int mapHeight, int mapWidth) {
        GridPane gridPane = new GridPane();
        Scene scene = new Scene(gridPane, mapHeight, mapWidth);
        return scene;
    }

}
