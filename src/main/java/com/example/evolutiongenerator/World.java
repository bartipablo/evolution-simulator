package com.example.evolutiongenerator;

import com.example.evolutiongenerator.interfaces.IReproduction;
import com.example.evolutiongenerator.interfaces.ITerrain;

public class World {

    private final Population population;
    private final ITerrain terrain;
    private final IReproduction reproduction;
    Statistics statistics;
    public World(Population population, ITerrain terrain, IReproduction reproduction, Statistics statistics) {
        this.population = population;
        this.terrain = terrain;
        this.reproduction = reproduction;
    }

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