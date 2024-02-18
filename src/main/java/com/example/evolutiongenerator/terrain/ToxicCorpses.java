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

}
