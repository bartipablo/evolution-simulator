package com.example.evolutiongenerator;

import com.example.evolutiongenerator.interfaces.*;
import com.example.evolutiongenerator.maps.Globe;
import com.example.evolutiongenerator.maps.HellishPortal;
import com.example.evolutiongenerator.reproduction.ReproductionA;
import com.example.evolutiongenerator.reproduction.ReproductionB;
import com.example.evolutiongenerator.terrain.ForestedEquators;
import com.example.evolutiongenerator.terrain.ToxicCorpses;
import com.example.evolutiongenerator.variants.BehaviourVariant;

import java.util.ArrayList;
import java.util.List;

public class World extends Thread {
    private final Population population;
    private IReproduction reproduction;
    private ITerrain terrain;
    private IMap map;
    private final BehaviourVariant behaviourVariant;
    private final Statistics statistics;
    private final List<IGuiObserver> guiObservers = new ArrayList<>();
    private int simulationDay = 0;


    //constructors------------------------------------------------------------------------------------------------------

    public World(Configuration configuration) {
        initializeVariants(configuration);
        behaviourVariant = configuration.getBehaviourVariant();
        statistics = new Statistics(configuration.getInitialAnimalsNumber(), configuration.getInitialPlantsNumber());
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
        setObservators();
        population.generateNewPopulation();
        terrain.generateInitialTerrain();
    }

    private void setObservators() {
        population.addNewStatisticsObserver(statistics);
        population.addNewPopulationObserver((IMapElementsObserver) map);
        terrain.addStatisticsObserver(statistics);
        terrain.addTerrainObserver((IMapElementsObserver) map);
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
        while (population.getLiveAnimals().size() > 0) {
            simulate();
        }
    }

    private void simulate() {
        refreshGuiCharts();
        population.ageIncrease();
        terrain.dailyPlantGrowth();
        population.dailyMoving();
        refreshGui();
        population.dailyEnergyConsumption();
        refreshGui();
        population.consumptions();
        refreshGui();
        population.vanishing();
        refreshGui();
        population.reproduction();
        population.completeStatistics();
        simulationDay++;
        refreshGui();
    }

    public IMap getMap() {
        return map;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public Population getPopulation() {
        return population;
    }

    public int getSimulationDay() {
        return simulationDay;
    }

    //observers---------------------------------------------------------------------------------------------------------
    public void addObserver(IGuiObserver guiObserver) {
        guiObservers.add(guiObserver);
    }

    private void refreshGui() {
        for (IGuiObserver guiObserver : guiObservers) {
            guiObserver.updateGuiViews();
        }
    }

    private void refreshGuiCharts() {
        for (IGuiObserver guiObserver : guiObservers) {
            guiObserver.updateGuiCharts();
        }
    }

}