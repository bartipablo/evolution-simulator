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
    private final int minimumNumberOfMutations;
    private final int maximumNumberOfMutations;
    private final int genomeLength;
    private final int numberOfPlantGrowingDaily;
    private final int plantsEnergy;
    private final MapVariant mapVariant;
    private final BehaviourVariant behaviourVariant;
    private final MutationVariant mutationVariant;
    private final TerrainVariant terrainVariant;

    Configuration(int mapHeight, int mapWidth, int energyOfOneMove, int initialPlantsNumber, int initialAnimalsNumber, int initialAnimalsEnergy,
                  int energyRequiredForReproduction, int energyUsedForReproduction, int minimumNumberOfMutations, int maximumNumberOfMutations,
                  int genomeLength, int numberOfPlantGrowingDaily, int plantEnergy, MapVariant mapVariant, BehaviourVariant behaviourVariant,
                  MutationVariant mutationVariant, TerrainVariant terrainVariant) {
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        this.dailyEnergyConsumption = energyOfOneMove;
        this.initialPlantsNumber = initialPlantsNumber;
        this.initialAnimalsNumber = initialAnimalsNumber;
        this.initialAnimalsEnergy = initialAnimalsEnergy;
        this.energyRequiredForReproduction = energyRequiredForReproduction;
        this.energyUsedForReproduction = energyUsedForReproduction;
        this.minimumNumberOfMutations = minimumNumberOfMutations;
        this.maximumNumberOfMutations = maximumNumberOfMutations;
        this.genomeLength = genomeLength;
        this.numberOfPlantGrowingDaily = numberOfPlantGrowingDaily;
        this.plantsEnergy = plantEnergy;
        this.mapVariant = mapVariant;
        this.behaviourVariant = behaviourVariant;
        this.mutationVariant = mutationVariant;
        this.terrainVariant = terrainVariant;
    }

    public int getMapHeight() {
        return mapHeight;
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

    public int getMinimumNumberOfMutations() {
        return minimumNumberOfMutations;
    }

    public int getMaximumNumberOfMutations() {
        return maximumNumberOfMutations;
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

    public TerrainVariant terrainVariant() {
        return terrainVariant;
    }

    public int getPlantsEnergy() {
        return plantsEnergy;
    }
}
