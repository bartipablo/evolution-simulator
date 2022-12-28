package com.example.evolutiongenerator.gui;

import com.example.evolutiongenerator.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainSceneController implements Initializable {

    //controllers------------------
    @FXML
    private NewSimulationController newSimulationController;
    //----------------------------

    private int simulationNumber = 1;
    @FXML
    private TabPane tabPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void onNewSimulationButtonClicked() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("new-simulation.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 867, 481);
        Stage stage = new Stage();
        newSimulationController = fxmlLoader.getController();
        newSimulationController.setStage(stage);
        newSimulationController.setControllers(this);
        stage.setResizable(false);
        stage.setTitle("New simulation");
        stage.getIcons().add(new Image(new FileInputStream("src/main/resources/com/example/evolutiongenerator/icon.png")));
        stage.setScene(scene);
        stage.show();
    }

    public void setNewPane() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("simulation-scene.fxml"));
        Parent root = fxmlLoader.load();
        Tab tab = new Tab("Simulation " + simulationNumber);
        simulationNumber += 1;
        tab.setContent(root);
        tabPane.getTabs().add(tab);
    }


}
