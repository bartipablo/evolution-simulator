package com.example.evolutiongenerator;

import com.example.evolutiongenerator.interfaces.IMap;
import com.example.evolutiongenerator.interfaces.IReproduction;
import com.example.evolutiongenerator.interfaces.ITerrain;
import com.example.evolutiongenerator.maps.Globe;
import com.example.evolutiongenerator.maps.HellishPortal;
import com.example.evolutiongenerator.reproduction.ReproductionA;
import com.example.evolutiongenerator.reproduction.ReproductionB;
import com.example.evolutiongenerator.terrain.ForestedEquators;
import com.example.evolutiongenerator.terrain.ToxicCorpses;
import com.example.evolutiongenerator.variants.BehaviourVariant;

public class World extends Thread {
    private final Population population;
    private IReproduction reproduction;
    private ITerrain terrain;
    private IMap map;
    private final BehaviourVariant behaviourVariant;


    //constructors------------------------------------------------------------------------------------------------------

    public World(Configuration configuration) {
        initializeVariants(configuration);
        behaviourVariant = configuration.getBehaviourVariant();
        population = new Population(
                configuration.getInitialAnimalsNumber(),
                configuration.getEnergyRequiredForReproduction(),
                configuration.getGenomeLength(),
                configuration.getEnergyUsedForReproduction(),
                Constant.MIN_MUTATION_NUMBER, Constant.MAX_MUTATION_NUMBER,
                map, reproduction, terrain, behaviourVariant,
                configuration.getDailyEnergyConsumption(),
                configuration.getInitialAnimalsEnergy()
        );
    }

    private void initializeVariants(Configuration configuration) {
        switch (configuration.getMapVariant()) {
            case GLOBE -> map = new Globe(configuration.getMapHeight(), configuration.getMapWidth());
            case HELLISH_PORTAL -> map = new HellishPortal(configuration.getMapHeight(), configuration.getMapWidth());
        }

        switch (configuration.getMutationVariant()) {
            case SLIGHT_CORRECTION -> reproduction = new ReproductionA();
            case FULL_RANDOMNESS -> reproduction = new ReproductionB();
        }

        switch (configuration.getTerrainVariant()) {
            case FOREST_EQUATORS -> terrain = new ForestedEquators(map, configuration.getNumberOfPlantGrowingDaily(),
                    configuration.getPlantsEnergy(), configuration.getInitialPlantsNumber());
            case TOXIC_CORPSES -> terrain = new ToxicCorpses(map, configuration.getNumberOfPlantGrowingDaily(),
                    configuration.getPlantsEnergy(), configuration.getInitialPlantsNumber());
        }
    }
    //------------------------------------------------------------------------------------------------------------------

    @Override
    public void run() {
        terrain.dailyPlantGrowth();
        population.dailyMoving();
        population.dailyEnergyConsumption();
        population.consumptions();
        population.vanishing();
        population.reproduction();
        population.completeStatistics();
    }

}