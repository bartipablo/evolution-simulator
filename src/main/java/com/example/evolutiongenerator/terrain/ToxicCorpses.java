package com.example.evolutiongenerator.terrain;

import com.example.evolutiongenerator.interfaces.IMap;

public class ToxicCorpses extends AbstractTerrain {

    public ToxicCorpses(IMap map, int numberOfPlantsGrowingDaily, int plantEnergy, int initialPlantsNumber) {
        super(map, numberOfPlantsGrowingDaily, plantEnergy, initialPlantsNumber);
        generateTerrain(initialPlantsNumber);
    }

    private void generateTerrain(int quantityOfPlants) {

    }

    @Override
    public void dailyPlantGrowth() {
        generateTerrain(plantsNumberGrowingDaily);
    }
}
