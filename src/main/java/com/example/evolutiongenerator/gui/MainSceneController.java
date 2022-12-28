package com.example.evolutiongenerator.gui;

import com.example.evolutiongenerator.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainSceneController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void onNewSimulationButton() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("new-simulation.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 867, 481);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("New simulation");
        stage.getIcons().add(new Image(new FileInputStream("src/main/resources/com/example/evolutiongenerator/icon.png")));
        stage.setScene(scene);
        stage.show();
    }


}
