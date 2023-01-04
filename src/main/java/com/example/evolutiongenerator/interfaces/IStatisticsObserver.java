package com.example.evolutiongenerator.interfaces;

import com.example.evolutiongenerator.direction.Vector2D;

import java.util.List;

public interface IStatisticsObserver {
    void setPopulationSize(int quantity);
    void setPlantsQuantity(int quantity);
    void setAverageLifeLength(List<IAnimal> extinctAnimals);
    void setAverageEnergy(List<IAnimal> aliveAnimals);
    void setFreeFieldQuantity(IMap map);
    void setTheMostPopularGenotype(List<int[]> animalGenotypes);
    void setSimulationDay(int day);

    }
