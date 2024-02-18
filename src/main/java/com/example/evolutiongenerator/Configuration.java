package com.example.evolutiongenerator;

import com.example.evolutiongenerator.variants.BehaviourVariant;
import com.example.evolutiongenerator.variants.MapVariant;
import com.example.evolutiongenerator.variants.MutationVariant;
import com.example.evolutiongenerator.variants.TerrainVariant;

public class Configuration {
    private final int mapHeight;
    private final int mapWidth;
    private final int dailyEnergyConsumption;
    private final int initialPlantsNumber;
    private final int initialAnimalsNumber;
    private final int initialAnimalsEnergy;
    private final int energyRequiredForReproduction;
    private final int energyUsedForReproduction;
    private final int genomeLength;
    private final int numberOfPlantGrowingDaily;
    private final int plantsEnergy;
    private final MapVariant mapVariant;
    private final BehaviourVariant behaviourVariant;
    private final MutationVariant mutationVariant;
    private final TerrainVariant terrainVariant;
    private final boolean saveToCSV;
    private final boolean removeExcessAnimals;
    private final String simulationName;

    private final int refreshTime;

    public Configuration(int mapHeight, int mapWidth, int dailyEnergyConsumption, int initialPlantsNumber, int initialAnimalsNumber, int initialAnimalsEnergy,
                         int energyRequiredForReproduction, int energyUsedForReproduction, int genomeLength, int numberOfPlantGrowingDaily, int plantEnergy,
                         MapVariant mapVariant, BehaviourVariant behaviourVariant, MutationVariant mutationVariant, TerrainVariant terrainVariant,
                         boolean saveToCSV, boolean removeExcessAnimals, String simulationName, int refreshTime) {
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        this.dailyEnergyConsumption = dailyEnergyConsumption;
        this.initialPlantsNumber = initialPlantsNumber;
        this.initialAnimalsNumber = initialAnimalsNumber;
        this.initialAnimalsEnergy = initialAnimalsEnergy;
        this.energyRequiredForReproduction = energyRequiredForReproduction;
        this.energyUsedForReproduction = energyUsedForReproduction;
        this.genomeLength = genomeLength;
        this.numberOfPlantGrowingDaily = numberOfPlantGrowingDaily;
        this.plantsEnergy = plantEnergy;
        this.mapVariant = mapVariant;
        this.behaviourVariant = behaviourVariant;
        this.mutationVariant = mutationVariant;
        this.terrainVariant = terrainVariant;
        this.saveToCSV = saveToCSV;
        this.removeExcessAnimals = removeExcessAnimals;
        this.simulationName = simulationName;
        this.refreshTime = refreshTime;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getRefreshTime() {
        return refreshTime;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getDailyEnergyConsumption() {
        return dailyEnergyConsumption;
    }

    public int getInitialPlantsNumber() {
        return initialPlantsNumber;
    }

    public int getInitialAnimalsNumber() {
        return initialAnimalsNumber;
    }

    public int getInitialAnimalsEnergy() {
        return initialAnimalsEnergy;
    }

    public int getEnergyRequiredForReproduction() {
        return energyRequiredForReproduction;
    }

    public int getEnergyUsedForReproduction() {
        return energyUsedForReproduction;
    }

    public int getGenomeLength() {
        return genomeLength;
    }

    public int getNumberOfPlantGrowingDaily() {
        return numberOfPlantGrowingDaily;
    }

    public MapVariant getMapVariant() {
        return mapVariant;
    }

    public BehaviourVariant getBehaviourVariant() {
        return behaviourVariant;
    }

    public MutationVariant getMutationVariant() {
        return mutationVariant;
    }

    public TerrainVariant getTerrainVariant() {
        return terrainVariant;
    }

    public int getPlantsEnergy() {
        return plantsEnergy;
    }

    public boolean isSaveToCSV() {
        return saveToCSV;
    }

    public boolean isRemoveExcessAnimals() {
        return removeExcessAnimals;
    }

    public String getSimulationName() {
        return simulationName;
    }
}
