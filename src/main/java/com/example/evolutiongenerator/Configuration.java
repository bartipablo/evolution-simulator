package com.example.evolutiongenerator;

import com.example.evolutiongenerator.variants.BehaviourVariant;

public class Configuration {
    private final int mapHeight;
    private final int mapWidth;
    private final int energyOfOneMove;
    private final int initialPlantsNumber;


    Configuration(int mapHeight, int mapWidth, int energyOfOneMove, int initialPlantsNumber) {
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        this.energyOfOneMove = energyOfOneMove;
        this.initialPlantsNumber = initialPlantsNumber;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getEnergyOfOneMove() {
        return mapHeight;
    }

    public int getInitialPlantsNumber() {
        return initialPlantsNumber;
    }
}
