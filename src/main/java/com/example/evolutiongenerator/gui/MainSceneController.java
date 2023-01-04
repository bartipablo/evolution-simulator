package com.example.evolutiongenerator.gui;

import com.example.evolutiongenerator.Configuration;
import com.example.evolutiongenerator.Main;
import com.example.evolutiongenerator.World;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainSceneController implements Initializable {

    //controllers---------------------------------------
    @FXML
    private NewSimulationController newSimulationController;
    @FXML
    private List<SimulationSceneController> simulationSceneControllerList = new ArrayList<>();
    //---------------------------------------------------
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
        newSimulationController.setMainSceneController(this);
        stage.setResizable(false);
        stage.setTitle("New simulation");
        stage.getIcons().add(new Image(new FileInputStream("src/main/resources/com/example/evolutiongenerator/icon.png")));
        stage.setScene(scene);
        stage.show();
    }

    public void createNewSimulation(Configuration configuration) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("simulation-scene.fxml"));
        Parent root = fxmlLoader.load();
        SimulationSceneController simulationSceneController = fxmlLoader.getController();
        simulationSceneController.setMainSceneController(this);
        simulationSceneController.setWorld(new World(configuration));
        simulationSceneControllerList.add(simulationSceneController);
        Tab tab = new Tab(configuration.getSimulationName());
        tab.setContent(root);
        tabPane.getTabs().add(tab);
    }

    public void removeTab(SimulationSceneController simulationSceneController) {
        if (simulationSceneControllerList.size() == 0) return;
        int i = simulationSceneControllerList.indexOf(simulationSceneController);
        tabPane.getTabs().remove(i + 1, i + 2);
        simulationSceneControllerList.remove(i);
    }
}
