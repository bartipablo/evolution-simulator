package com.example.evolutiongenerator.gui;

import com.example.evolutiongenerator.Configuration;
import com.example.evolutiongenerator.Main;
import com.example.evolutiongenerator.World;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App extends Application {

    private MainSceneController mainSceneController;
    private final static List<World> worlds = new ArrayList<>();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-scene.fxml"));
        mainSceneController = fxmlLoader.getController();
        Scene scene = new Scene(fxmlLoader.load(), 1530, 790);
        stage.setResizable(false);
        stage.setTitle("Evolution Generator");
        stage.getIcons().add(new Image(new FileInputStream("src/main/resources/com/example/evolutiongenerator/icon.png")));
        stage.setScene(scene);
        stage.show();
    }

    public void createNewSimulation(Configuration configuration) throws IOException {
        World world = new World(configuration);
        worlds.add(world);
        mainSceneController.setNewPane();
    }

    private Scene prepareSimulationScene(int mapHeight, int mapWidth) {
        GridPane gridPane = new GridPane();
        Scene scene = new Scene(gridPane, mapHeight, mapWidth);
        return scene;
    }

}