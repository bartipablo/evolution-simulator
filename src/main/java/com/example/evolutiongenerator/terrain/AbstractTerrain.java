package com.example.evolutiongenerator.terrain;

import com.example.evolutiongenerator.Plant;
import com.example.evolutiongenerator.interfaces.IMap;
import com.example.evolutiongenerator.interfaces.IMapElementsObserver;
import com.example.evolutiongenerator.interfaces.IStatisticsObserver;
import com.example.evolutiongenerator.interfaces.ITerrain;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class AbstractTerrain implements ITerrain {

    protected final Random random;

    protected final IMap map;

    protected final int plantsNumberGrowingDaily;

    protected final int initialPlantsEnergy;

    protected final int initialPlantsNumber;

    protected final List<Plant> plants = new ArrayList<>();

    private final List<IStatisticsObserver> statisticsObservers = new ArrayList<>();

    protected final List<IMapElementsObserver> terrainObserver = new ArrayList<>();


    //constructors------------------------------------------------------------------
    public AbstractTerrain(IMap map, int plantsNumberGrowingDaily, int initialPlantsEnergy, int initialPlantsNumber) {
        this.map = map;
        this.plantsNumberGrowingDaily = plantsNumberGrowingDaily;
        this.random = new Random();
        this.initialPlantsEnergy = initialPlantsEnergy;
        this.initialPlantsNumber = initialPlantsNumber;
    }
    //-----------------------------------------------------------------------------

    @Override
    public void removePlant(Plant plant) {
        plants.remove(plant);
        informObserverAboutRemovedPlats(plant);
        completeStatistics();
    }

    @Override
    public List<Plant> getPlants() {
        return plants;
    }

    //observers------------------------------------------------------------------------
    public void addTerrainObserver(IMapElementsObserver mapElementsObserver) {
        terrainObserver.add(mapElementsObserver);
    }

    public void addStatisticsObserver(IStatisticsObserver observer) {
        statisticsObservers.add(observer);
    }

    private void completeStatistics() {
        for (IStatisticsObserver observer : statisticsObservers) {
            observer.setPlantsQuantity(plants.size());
        }
    }

    protected void informObserverAboutAddedNewPlats(Plant plant) {
        for (IMapElementsObserver observer : terrainObserver) {
            observer.addPlantToMap(plant);
        }
    }

    protected void informObserverAboutRemovedPlats(Plant plant) {
        for (IMapElementsObserver observer : terrainObserver) {
            observer.removePlantFromMap(plant);
        }
    }

    //---------------------------------------------------------------------------------

    @Override
    public void dailyPlantGrowth() {
        generateTerrain(plantsNumberGrowingDaily);
    }

    @Override
    public void generateInitialTerrain() {
        generateTerrain(initialPlantsNumber);
    }

    protected void generateTerrain(int quantityOfPlants) {
        int quantityOfPlantsOnPreferPosition = (int) Math.ceil(0.8 * quantityOfPlants);
        int quantityOfPlantsOutsidePreferPosition = quantityOfPlants - quantityOfPlantsOnPreferPosition;
        setAtPreferPosition(quantityOfPlantsOnPreferPosition);
        setOutsidePreferPosition(quantityOfPlantsOutsidePreferPosition);
        completeStatistics();
    }

    protected abstract void setAtPreferPosition(int quantityOfPlants);

    protected abstract void setOutsidePreferPosition(int quantityOfPlants);
}
