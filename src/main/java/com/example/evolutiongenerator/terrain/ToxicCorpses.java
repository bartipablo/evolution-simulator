package com.example.evolutiongenerator.terrain;

import com.example.evolutiongenerator.Plant;
import com.example.evolutiongenerator.direction.Vector2D;
import com.example.evolutiongenerator.interfaces.IMap;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class ToxicCorpses extends AbstractTerrain {

    public ToxicCorpses(IMap map, int numberOfPlantsGrowingDaily, int plantEnergy, int initialPlantsNumber) {
        super(map, numberOfPlantsGrowingDaily, plantEnergy, initialPlantsNumber);
        generateTerrain(initialPlantsNumber);
    }


    @Override
    public void dailyPlantGrowth() {
        generateTerrain(plantsNumberGrowingDaily);
    }

    @Override
    protected void setAtPreferPosition(int quantity) {
        List<Vector2D> preferPositions = map.getPositionsSortedByNumbersOfDeaths();
        setAtPositions(preferPositions, quantity);
    }

    @Override
    protected void setOutsidePreferPosition(int quantity) {
        List<Vector2D> preferPositions = map.getPositionsSortedByNumbersOfDeaths();
        Collections.reverse(preferPositions);
        setAtPositions(preferPositions, quantity);
    }

    private void setAtPositions(List<Vector2D> positions, int quantity) {
        for (Vector2D position : positions) {
            Plant plant = new Plant(position, initialPlantsEnergy);
            if (map.getPlantAtPosition(position) == null) {
                plants.add(plant);
                quantity -= 1;
                informObserverAboutAddedNewPlats(plant);
            }
            if (quantity == 0) {
                break;
            }
        }
    }


}
