package com.example.evolutiongenerator;

import com.example.evolutiongenerator.interfaces.*;
import com.example.evolutiongenerator.maps.Globe;
import com.example.evolutiongenerator.maps.HellishPortal;
import com.example.evolutiongenerator.reproduction.ReproductionA;
import com.example.evolutiongenerator.reproduction.ReproductionB;
import com.example.evolutiongenerator.terrain.ForestedEquators;
import com.example.evolutiongenerator.terrain.ToxicCorpses;
import com.example.evolutiongenerator.variants.BehaviourVariant;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class SimulationEngine extends Thread {
    private final Population population;
    private IReproduction reproduction;
    private ITerrain terrain;
    private IMap map;
    private final BehaviourVariant behaviourVariant;
    private final Statistics statistics;
    private final List<IGuiObserver> guiObservers = new ArrayList<>();
    private int simulationDay = 0;
    private final WriterCSV writerCSV;

    private boolean isPaused = false;

    private boolean isFirstSimulationStep = true;

    private final int refreshTime;

    //constructors------------------------------------------------------------------------------------------------------

    public SimulationEngine(Configuration configuration) throws FileNotFoundException {
        initializeVariants(configuration);
        behaviourVariant = configuration.getBehaviourVariant();
        statistics = new Statistics(configuration.getInitialAnimalsNumber(), configuration.getInitialPlantsNumber());
        this.writerCSV = new WriterCSV(statistics, configuration);
        population = new Population(
                configuration.getInitialAnimalsNumber(),
                configuration.getEnergyRequiredForReproduction(),
                configuration.getGenomeLength(),
                configuration.getEnergyUsedForReproduction(),
                Properties.MIN_MUTATION_NUMBER, Properties.MAX_MUTATION_NUMBER,
                map, reproduction, terrain, behaviourVariant,
                configuration.getDailyEnergyConsumption(),
                configuration.getInitialAnimalsEnergy(),
                this, configuration.isRemoveExcessAnimals()
        );
        refreshTime = configuration.getRefreshTime();
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
        do {
            synchronized (this) {
                while (isPaused) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            try {
                simulate();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } while (population.getLiveAnimals().size() > 0);
    }

    public void pauseSimulation() {
        synchronized (this) {
            isPaused = true;
        }
    }

    public void resumeSimulation() {
        synchronized (this) {
            isPaused = false;
            this.notifyAll();
        }
    }

    private void simulate() throws FileNotFoundException {
        if (isFirstSimulationStep) {
            refreshGuiCharts();
            isFirstSimulationStep = false;
        }
        simulationDay++;
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
        statistics.setSimulationDay(simulationDay);
        refreshGui();
        writerCSV.saveStatisticsToCSV();
        population.removeExcessAnimals();
        refreshGuiCharts();
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

    public int getRefreshTime() {
        return refreshTime;
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
    //------------------------------------------------------------------------------------------------------------------

}