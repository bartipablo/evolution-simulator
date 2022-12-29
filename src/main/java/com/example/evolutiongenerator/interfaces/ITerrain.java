package com.example.evolutiongenerator.interfaces;

import com.example.evolutiongenerator.Plant;

import java.util.List;

public interface ITerrain {
    void dailyPlantGrowth();
    void removePlant(Plant plant);
    List<Plant> getPlants();

    void addTerrainObserver(IMapElementsObserver mapElementsObserver);

    void addStatisticsObserver(IStatisticsObserver observer);
}
