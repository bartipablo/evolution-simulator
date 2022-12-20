package com.example.evolutiongenerator;

import com.example.evolutiongenerator.variants.BehaviourVariant;

public class Configuration {
    private final int mapHeight;
    private final int mapWidth;
    private final int energyOfOneMove;
    private final int initialPlantsNumber;
    private final int initialAnimalsNumber;

    Configuration(int mapHeight, int mapWidth, int energyOfOneMove, int initialPlantsNumber, int initialAnimalsNumber) {
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        this.energyOfOneMove = energyOfOneMove;
        this.initialPlantsNumber = initialPlantsNumber;
        this.initialAnimalsNumber = initialAnimalsNumber;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getMapWidth() {
        return energyOfOneMove;
    }

    public int getEnergyOfOneMove() {
        return energyOfOneMove;
    }

    public int getInitialPlantsNumber() {
        return initialPlantsNumber;
    }

    public int getInitialAnimalsNumber() {
        return initialAnimalsNumber;
    }
}
