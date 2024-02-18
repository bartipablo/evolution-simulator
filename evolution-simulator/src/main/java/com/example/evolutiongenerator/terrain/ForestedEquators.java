package com.example.evolutiongenerator.terrain;

import com.example.evolutiongenerator.Plant;
import com.example.evolutiongenerator.Static;
import com.example.evolutiongenerator.direction.Vector2D;
import com.example.evolutiongenerator.interfaces.IMap;

import java.util.Arrays;
import java.util.List;

public class ForestedEquators extends AbstractTerrain {

    private int minEquatorIndex;
    private int maxEquatorIndex;

    public ForestedEquators(IMap map, int numberOfPlantsGrowingDaily, int plantEnergy, int initialPlantsNumber) {
        super(map, numberOfPlantsGrowingDaily, plantEnergy, initialPlantsNumber);
        calculateEquatorIndexes();
    }

    @Override
    protected void setAtPreferPosition(int quantity) {
        Vector2D[] positions = Static.generateVector2DArray(0, map.getMapWidth()-1, minEquatorIndex, maxEquatorIndex);
        Static.shuffleVector2DArray(positions);
        List<Vector2D> positionList = Arrays.asList(positions);
        setAtPositions(positionList, quantity);
    }

    @Override
    protected void setOutsidePreferPosition(int quantity) {
        Vector2D[] positions1 = Static.generateVector2DArray(0, map.getMapHeight()-1, 0, minEquatorIndex - 1);
        Vector2D[] positions2 = Static.generateVector2DArray(0, map.getMapHeight()-1, maxEquatorIndex + 1, map.getMapHeight()-1);
        Vector2D[] positions = Static.concatTwoVector2DArrays(positions1, positions2);
        Static.shuffleVector2DArray(positions);
        List<Vector2D> positionList = Arrays.asList(positions);
        setAtPositions(positionList, quantity);
    }



    private void calculateEquatorIndexes() {
        minEquatorIndex = (int) Math.floor(0.35 * map.getMapHeight());
        maxEquatorIndex = (int) Math.floor(0.65 * map.getMapHeight());
    }

}
