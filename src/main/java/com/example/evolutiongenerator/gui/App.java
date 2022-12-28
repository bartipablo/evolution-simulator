package com.example.evolutiongenerator.gui;

import com.example.evolutiongenerator.Configuration;
import com.example.evolutiongenerator.Main;
import com.example.evolutiongenerator.variants.BehaviourVariant;
import com.example.evolutiongenerator.variants.MapVariant;
import com.example.evolutiongenerator.variants.MutationVariant;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-scene.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1530, 790);
        stage.setResizable(false);
        stage.setTitle("Evolution Generator");
        stage.getIcons().add(new Image(new FileInputStream("src/main/resources/com/example/evolutiongenerator/icon.png")));
        stage.setScene(scene);
        stage.show();
    }

    public static void runSimulation(Configuration configuration) {

    }

    private Scene prepareSimulationScene(int mapHeight, int mapWidth) {
        GridPane gridPane = new GridPane();
        Scene scene = new Scene(gridPane, mapHeight, mapWidth);
        return scene;
    }

}
