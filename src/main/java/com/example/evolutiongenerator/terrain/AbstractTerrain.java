package com.example.evolutiongenerator.terrain;

import com.example.evolutiongenerator.Plant;
import com.example.evolutiongenerator.interfaces.IMap;
import com.example.evolutiongenerator.interfaces.IStatisticsObserver;
import com.example.evolutiongenerator.interfaces.ITerrain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class AbstractTerrain implements ITerrain {

    protected final Random random;
    protected final IMap map;
    protected final int numberOfPlantsGrowingDaily;
    protected final int plantEnergy;
    protected final int initialPlantsNumber;
    protected final List<Plant> plants = new ArrayList<>();
    private final List<IStatisticsObserver> statisticsObservers = new ArrayList<>();


    //constructors------------------------------------------------------------------
    public AbstractTerrain(IMap map, int numberOfPlantsGrowingDaily, int plantEnergy, int initialPlantsNumber) {
        this.map = map;
        this.numberOfPlantsGrowingDaily = numberOfPlantsGrowingDaily;
        this.random = new Random();
        this.plantEnergy = plantEnergy;
        this.initialPlantsNumber = initialPlantsNumber;
    }
    //-----------------------------------------------------------------------------

    @Override
    public void removePlant(Plant plant) {
        plants.remove(plant);
    }

    @Override
    public List<Plant> getPlants() {
        return plants;
    }

    //observers------------------------------------------------------------------------
    public void addNewStatisticsObserver(IStatisticsObserver observer) {
        statisticsObservers.add(observer);
    }

    private void completeStatistics() {
        for (IStatisticsObserver observer : statisticsObservers) {
            observer.setPlantsQuantity(plants.size());
        }
    }
    //---------------------------------------------------------------------------------

}
