package com.example.evolutiongenerator.terrain;

import com.example.evolutiongenerator.Plant;
import com.example.evolutiongenerator.interfaces.IMap;
import com.example.evolutiongenerator.interfaces.IStatisticsObserver;
import com.example.evolutiongenerator.interfaces.ITerrain;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTerrain implements ITerrain {

    private final IMap map;
    private final List<Plant> plants = new ArrayList<>();
    private final List<IStatisticsObserver> statisticsObservers = new ArrayList<>();

    //constructors------------------------------------------------------------------
    public AbstractTerrain(IMap map) {
        this.map = map;
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
