package com.example.evolutiongenerator.gui;

import com.example.evolutiongenerator.Configuration;
import com.example.evolutiongenerator.Static;
import com.example.evolutiongenerator.variants.BehaviourVariant;
import com.example.evolutiongenerator.variants.MapVariant;
import com.example.evolutiongenerator.variants.MutationVariant;
import com.example.evolutiongenerator.variants.TerrainVariant;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.evolutiongenerator.Constant.*;

public class NewSimulationController implements Initializable {

    //controllers---------------------------------------------
    private MainSceneController mainSceneController;

    //--------------------------------------------------------
    private Configuration configuration;
    private Stage stage;

    //choice boxes----------------------------------------------
    @FXML
    private ChoiceBox<String> mapVariantChoiceBox;
    @FXML
    private ChoiceBox<String> mutationVariantChoiceBox;
    @FXML
    private ChoiceBox<String> behaviourVariantChoiceBox;
    @FXML
    private ChoiceBox<String> growingVariantChoiceBox;
    //-----------------------------------------------------------


    //labels-----------------------------------------------------
    @FXML
    private Label mapVariantLabel;
    @FXML
    private Label mapHeightLabel;
    @FXML
    private Label mapWidthLabel;
    @FXML
    private Label plantsEnergyProfitLabel;
    @FXML
    private Label minEnergyToCopulationLabel;
    @FXML
    private Label animalStartEnergyLabel;
    @FXML
    private Label dailyEnergyCostLabel;
    @FXML
    private Label energyUsedToCopulationLabel;
    @FXML
    private Label mutationVariantLabel;
    @FXML
    private Label behaviourVariantLabel;
    @FXML
    private Label genomeLengthLabel;
    @FXML
    private Label animalsStartSpawningLabel;
    @FXML
    private Label plantsStartSpawningLabel;
    @FXML
    private Label plantsEachDaySpawningLabel;
    @FXML
    private Label growingVariantLabel;
    @FXML
    private Label refreshTimeLabel;
    @FXML
    private Label errorMessagesLabel;
    @FXML
    private Label simulationNameLabel;
    //-----------------------------------------------------------

    //Text fields------------------------------------------------
    @FXML
    private TextField mapHeightTextField;

    @FXML
    private TextField mapWidthTextField;

    @FXML
    private TextField plantsEnergyProfitTextField;
    @FXML
    private TextField minEnergyToCopulationTextField;
    @FXML
    private TextField animalStartEnergyTextField;
    @FXML
    private TextField dailyEnergyCostTextField;
    @FXML
    private TextField energyUsedToCopulationTextField;
    @FXML
    private TextField genomeLengthTextField;
    @FXML
    private TextField animalStartSpawningTextField;
    @FXML
    private TextField plantStartSpawningTextField;
    @FXML
    private TextField plantEachDaySpawningTextField;
    @FXML
    private TextField refreshTimeTextField;
    @FXML
    private TextField simulationNameTextField;
    //-----------------------------------------------------------

    //check boxes------------------------------------------------
    @FXML
    private CheckBox saveCSVCheckBox;
    @FXML
    private CheckBox removeExcessAnimalsCheckBox;

    //-----------------------------------------------------------

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mapVariantChoiceBox.getItems().addAll("Globe", "HellishPortal");
        mapVariantChoiceBox.setValue("Globe");
        mutationVariantChoiceBox.getItems().addAll("Full randomness", "Slight correction");
        mutationVariantChoiceBox.setValue("Full randomness");
        behaviourVariantChoiceBox.getItems().addAll("Full predestination", "Bit of craziness");
        behaviourVariantChoiceBox.setValue("Full predestination");
        growingVariantChoiceBox.getItems().addAll("Forested equators", "Toxic corpses");
        growingVariantChoiceBox.setValue("Forested equators");
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setMainSceneController(MainSceneController mainSceneController) {
        this.mainSceneController = mainSceneController;
    }

    @FXML
    protected void onCancelButtonClicked() {
        stage.close();
    }

    @FXML
    protected void onCreateNewSimulationButtonClicked() throws IOException {
        if (validateAllUserArguments()) {
            getConfigurationFromUserArguments();
            stage.close();
            mainSceneController.createNewSimulation(configuration);
        }
    }

    private boolean validateAllUserArguments() {
        return validateArgument(mapHeightLabel, mapHeightTextField, MIN_MAP_HEIGHT, MAX_MAP_HEIGHT)
                && validateArgument(mapWidthLabel, mapWidthTextField, MIN_MAP_WIDTH, MAX_MAP_WIDTH)
                && validateArgument(plantsEnergyProfitLabel, plantsEnergyProfitTextField, MIN_PLANTS_ENERGY_PROFIT ,MAX_PLANTS_ENERGY_PROFIT)
                && validateArgument(minEnergyToCopulationLabel, minEnergyToCopulationTextField, MIN_MINIMUM_ENERGY_TO_COPULATION, MAX_MINIMUM_ENERGY_TO_COPULATION)
                && validateArgument(animalStartEnergyLabel, animalStartEnergyTextField, MIN_ANIMAL_START_ENERGY, MAX_ANIMAL_START_ENERGY)
                && validateArgument(dailyEnergyCostLabel, dailyEnergyCostTextField, MIN_DAILY_ENERGY_COST, MAX_DAILY_ENERGY_COST)
                && validateArgument(minEnergyToCopulationLabel, minEnergyToCopulationTextField, MIN_ENERGY_USED_TO_COPULATION, MAX_ENERGY_USED_TO_COPULATION)
                && validateArgument(genomeLengthLabel, genomeLengthTextField, MIN_GENOME_LENGTH, MAX_GENOME_LENGTH)
                && validateArgument(animalsStartSpawningLabel, animalStartSpawningTextField, MIN_ANIMALS_SPAWNING_AT_START, MAX_ANIMALS_SPAWNING_AT_START)
                && validateArgument(plantsStartSpawningLabel, plantStartSpawningTextField, MIN_PLANTS_SPAWNING_AT_THE_START, MAX_PLANTS_SPAWNING_AT_THE_START)
                && validateArgument(plantsEachDaySpawningLabel, plantEachDaySpawningTextField, MIN_PLANTS_SPAWNED_AT_EACH_DAY, MAX_PLANTS_SPAWNED_AT_EACH_DAY)
                && validateArgument(refreshTimeLabel, refreshTimeTextField, MIN_REFRESH_TIME, MAX_REFRESH_TIME)
                && validateSimulationName(simulationNameLabel, simulationNameTextField)
                && validateTwoArguments(minEnergyToCopulationLabel, minEnergyToCopulationTextField, energyUsedToCopulationLabel, energyUsedToCopulationTextField);
    }

    private boolean validateArgument(Label argumentLabel, TextField argumentTextField, int minValue, int maxValue) {
        String stringUserArgument = argumentTextField.getText();
        if (stringUserArgument.length() == 0) {
            argumentLabel.setTextFill(Color.RED);
            errorMessagesLabel.setText("ERROR: The " + argumentLabel.getText() + " value cannot be a empty!");
            return false;
        }
        if (!Static.isStringInt(stringUserArgument)) {
            argumentLabel.setTextFill(Color.RED);
            errorMessagesLabel.setText("ERROR: The " + argumentLabel.getText() + " value must be an integer!");
            return false;
        }

        int intUserArgument = Integer.parseInt(stringUserArgument);
        if (intUserArgument < minValue || intUserArgument > maxValue) {
            argumentLabel.setTextFill(Color.RED);
            errorMessagesLabel.setText("ERROR: The " + argumentLabel.getText() + " value exceeded!\n" +
                    "It must be a integer between " + minValue +  " and " + maxValue);
            return false;
        }
        argumentLabel.setTextFill(Color.BLACK);
        return true;
    }

    private boolean validateTwoArguments(Label argumentLabel1, TextField argumentTextField1, Label argumentLabel2, TextField argumentTextField2) {
        int intUserArgument1 = Integer.parseInt(argumentTextField1.getText());
        int intUserArgument2 = Integer.parseInt(argumentTextField2.getText());
        if (intUserArgument1 > intUserArgument2) {
            argumentLabel1.setTextFill(Color.RED);
            argumentLabel1.setTextFill(Color.RED);
            errorMessagesLabel.setText("ERROR: The argument " + argumentLabel1.getText() + " cannot be greater than the argument " + argumentLabel2.getText());
            return false;
        }
        return true;
    }

    private boolean validateSimulationName(Label label, TextField textField) {
        String simulationName = textField.getText();
        if (simulationName.length() == 0) {
            label.setTextFill(Color.RED);
            errorMessagesLabel.setText("ERROR: The simulation name field must not be empty!");
            return false;
        }
        return true;
    }

    private void getConfigurationFromUserArguments() {
        BehaviourVariant behaviourVariant = null;
        switch (behaviourVariantChoiceBox.getValue()) {
            case "Full predestination" -> behaviourVariant = BehaviourVariant.FULL_PREDESTINATION;
            case "Bit of craziness"    -> behaviourVariant = BehaviourVariant.BIT_OF_CRAZINESS;
        }

        MapVariant mapVariant = null;
        switch (mapVariantChoiceBox.getValue()) {
            case "Globe"         -> mapVariant = MapVariant.GLOBE;
            case "HellishPortal" -> mapVariant = MapVariant.HELLISH_PORTAL;
        }

        MutationVariant mutationVariant = null;
        switch (mutationVariantChoiceBox.getValue()) {
            case "Full randomness"   -> mutationVariant = MutationVariant.FULL_RANDOMNESS;
            case "Slight correction" -> mutationVariant = MutationVariant.SLIGHT_CORRECTION;
        }

        TerrainVariant terrainVariant = null;
        switch (growingVariantChoiceBox.getValue()) {
            case "Forested equators"  -> terrainVariant = TerrainVariant.FOREST_EQUATORS;
            case "Toxic corpses"      -> terrainVariant = TerrainVariant.TOXIC_CORPSES;
        }


        configuration = new Configuration(
                Integer.parseInt(mapHeightTextField.getText()),
                Integer.parseInt(mapWidthTextField.getText()),
                Integer.parseInt(dailyEnergyCostTextField.getText()),
                Integer.parseInt(plantStartSpawningTextField.getText()),
                Integer.parseInt(animalStartSpawningTextField.getText()),
                Integer.parseInt(animalStartEnergyTextField.getText()),
                Integer.parseInt(minEnergyToCopulationTextField.getText()),
                Integer.parseInt(energyUsedToCopulationTextField.getText()),
                Integer.parseInt(genomeLengthTextField.getText()),
                Integer.parseInt(plantEachDaySpawningTextField.getText()),
                Integer.parseInt(plantsEnergyProfitTextField.getText()),
                mapVariant, behaviourVariant, mutationVariant, terrainVariant,
                saveCSVCheckBox.isSelected(), removeExcessAnimalsCheckBox.isSelected(),
                simulationNameTextField.getText()
        );
    }
}
