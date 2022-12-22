package com.example.evolutiongenerator.maps;

import com.example.evolutiongenerator.interfaces.IAnimal;
import com.example.evolutiongenerator.Plant;
import com.example.evolutiongenerator.direction.Vector2D;
import com.example.evolutiongenerator.interfaces.IMap;
import com.example.evolutiongenerator.interfaces.IMapElementsObserver;

import java.util.*;

public abstract class AbstractMap implements IMap, IMapElementsObserver {

    protected final int mapHeight;
    protected final int mapWidth;
    private final Map<Vector2D, List<IAnimal>> livesAnimalsOnMap = new HashMap<>();
    private final Map<Vector2D, Integer> numbersOfDeathsAtPosition = new HashMap<>();
    private final TreeMap<Integer, List<Vector2D>> positionWithNumberOfDeath = new TreeMap<>();
    private final Map<Vector2D, Plant> plantsOnMap = new HashMap<>();


    //constructors-------------------------------------------------
    AbstractMap(int mapHeight, int mapWidth) {
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        initialHashMaps();
    }

    private void initialHashMaps() {
        positionWithNumberOfDeath.put(0, new ArrayList<Vector2D>());
        for (int i = 0; i < mapWidth; i++) {
            for (int j = 0; j < mapHeight; j++) {
                numbersOfDeathsAtPosition.put(new Vector2D(i, j), 0);
                positionWithNumberOfDeath.get(0).add(new Vector2D(i, j));
            }
        }
    }
    //-----------------------------------------------------------------

    @Override
    public int getMapHeight() {
        return mapHeight;
    }

    @Override
    public int getMapWidth() {
        return mapWidth;
    }

    @Override
    public List<IAnimal> getAnimalsAtPosition(Vector2D position) {
        return livesAnimalsOnMap.get(position);
    }

    @Override
    public Plant getPlantAtPosition(Vector2D position) {
        return plantsOnMap.get(position);
    }

    @Override
    public Vector2D[] getAnimalsPositions() {
        return livesAnimalsOnMap.keySet().toArray(new Vector2D[0]);
    }

    @Override
    public void changePositionOnMap(IAnimal animal, Vector2D oldPosition, Vector2D newPosition) {
        livesAnimalsOnMap.get(oldPosition).remove(animal);
        livesAnimalsOnMap.get(newPosition).add(animal);
    }

    @Override
    public void addAnimalToMap(IAnimal animal) {
        if (livesAnimalsOnMap.get(animal.getPosition()) != null) {
            livesAnimalsOnMap.get(animal.getPosition()).add(animal);
        } else {
            livesAnimalsOnMap.put(animal.getPosition(), new ArrayList<IAnimal>());
            livesAnimalsOnMap.get(animal.getPosition()).add(animal);
        }
    }

    @Override
    public void removeAnimalFromMap(IAnimal animal) {
        removeAliveAnimalFromHashMap(animal);
        updatePositionWithNumberOfDeath(animal);
        updateNumbersOfDeathsAtPosition(animal);
    }

    private void removeAliveAnimalFromHashMap(IAnimal animal) {
        livesAnimalsOnMap.get(animal.getPosition()).remove(animal);
        if (livesAnimalsOnMap.get(animal.getPosition()).size() == 0) {
            livesAnimalsOnMap.remove(animal.getPosition());
        }
    }

    private void updatePositionWithNumberOfDeath(IAnimal animal) {
        int numberOfDeath = numbersOfDeathsAtPosition.get(animal.getPosition());
        positionWithNumberOfDeath.get(numberOfDeath).remove(animal.getPosition());
        if (positionWithNumberOfDeath.get(numberOfDeath).size() == 0) {
            positionWithNumberOfDeath.remove(numberOfDeath);
        }
        if (positionWithNumberOfDeath.get(numberOfDeath + 1) == null) {
            positionWithNumberOfDeath.put(numberOfDeath + 1, new ArrayList<Vector2D>());
        }
        positionWithNumberOfDeath.get(numberOfDeath + 1).add(animal.getPosition());
    }

    private void updateNumbersOfDeathsAtPosition(IAnimal animal) {
        int numberOfDeath = numbersOfDeathsAtPosition.get(animal.getPosition());
        numbersOfDeathsAtPosition.put(animal.getPosition(), numberOfDeath + 1);
    }

    @Override
    public void addPlantToMap(Plant plant) {
        plantsOnMap.put(plant.getPosition(), plant);
    }

    @Override
    public void removePlantFromMap(Plant plant) {
        plantsOnMap.remove(plant.getPosition());
    }

}
