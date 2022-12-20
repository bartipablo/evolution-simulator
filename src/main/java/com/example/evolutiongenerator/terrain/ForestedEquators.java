package com.example.evolutiongenerator.terrain;

import com.example.evolutiongenerator.Plant;
import com.example.evolutiongenerator.Static;
import com.example.evolutiongenerator.direction.Vector2D;
import com.example.evolutiongenerator.interfaces.IMap;

public class ForestedEquators extends AbstractTerrain {

    private int minEquatorIndex;
    private int maxEquatorIndex;

    public ForestedEquators(IMap map, int numberOfPlantsGrowingDaily, int plantEnergy, int initialPlantsNumber) {
        super(map, numberOfPlantsGrowingDaily, plantEnergy, initialPlantsNumber);
        generateTerrain(initialPlantsNumber);
    }

    @Override
    public void dailyPlantGrowth() {
        generateTerrain(numberOfPlantsGrowingDaily);
    }

    private void generateTerrain(int quantityOfPlants) {
        int quantityOfPlantsOnEquator = (int) Math.ceil(0.8 * quantityOfPlants);
        int quantityOfPlantsOutsideEquator = quantityOfPlants - quantityOfPlantsOnEquator;
        setAtEquator(quantityOfPlantsOnEquator);
        setOutsideEquator(quantityOfPlantsOutsideEquator);
    }

    private void setAtEquator(int quantity) {
        Vector2D[] positions = Static.generateVector2DArray(0, map.getMapHeight(), minEquatorIndex, maxEquatorIndex);
        setAtPositions(positions, quantity);

    }

    private void setOutsideEquator(int quantity) {
        Vector2D[] positions1 = Static.generateVector2DArray(0, map.getMapHeight(), 0, minEquatorIndex - 1);
        Vector2D[] positions2 = Static.generateVector2DArray(0, map.getMapHeight(), maxEquatorIndex + 1, map.getMapHeight());
        Vector2D[] positions = Static.concatTwoVector2DArrays(positions1, positions2);
        setAtPositions(positions, quantity);

    }

    private void setAtPositions(Vector2D[] positions, int quantity) {
        for (Vector2D position: positions) {
            Plant plant = new Plant(position, plantEnergy);
            if (map.getPlantAtPosition(position) == null) {
                plants.add(plant);
                quantity -= 1;
                //inform observer
            }
            if (quantity == 0) {
                break;
            }
        }
    }

    private void calculateEquatorIndexes() {

    }

}
