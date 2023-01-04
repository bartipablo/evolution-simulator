package com.example.evolutiongenerator.gui;

import com.example.evolutiongenerator.Main;
import com.example.evolutiongenerator.SimulationEngine;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App extends Application {

    private final static List<SimulationEngine> worlds = new ArrayList<>();
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-scene.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1530, 790);
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.setTitle("Evolution Generator");
        stage.getIcons().add(new Image(new FileInputStream("src/main/resources/com/example/evolutiongenerator/icon.png")));
        stage.setScene(scene);
        stage.show();
    }
}