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

    private final Map<Vector2D, Integer> quantityOfDeathsAtPosition = new HashMap<>();

    private final TreeMap<Integer, List<Vector2D>> positionWithNumberOfDeath = new TreeMap<>();

    private final Map<Vector2D, Plant> plantsOnMap = new HashMap<>();

    //constructors-------------------------------------------------
    public AbstractMap(int mapHeight, int mapWidth) {
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        initialHashMaps();
    }

    private void initialHashMaps() {

        //Lives animals on map initialization
        for (int i = 0; i < mapWidth; i++) {
            for (int j = 0; j < mapHeight; j++) {
                livesAnimalsOnMap.put(new Vector2D(i, j), new ArrayList<>());
            }
        }

        positionWithNumberOfDeath.put(0, new ArrayList<>());
        for (int i = 0; i < mapWidth; i++) {
            for (int j = 0; j < mapHeight; j++) {
                quantityOfDeathsAtPosition.put(new Vector2D(i, j), 0);
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
    public List<Vector2D> getPositionsSortedByNumbersOfDeaths() {
        List<Vector2D> resultList = new ArrayList<>();
        for (List<Vector2D> vector2DList : positionWithNumberOfDeath.values()) {
            resultList.addAll(vector2DList);
        }
        return resultList;
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
        return livesAnimalsOnMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue().size() >= 1)
                .map(Map.Entry::getKey)
                .toArray(Vector2D[]::new);
    }

    @Override
    public void changePositionOnMap(IAnimal animal, Vector2D oldPosition, Vector2D newPosition) {
        removeLiveAnimalFromHashMap(animal);
        if (livesAnimalsOnMap.get(newPosition) != null) {
            livesAnimalsOnMap.get(newPosition).add(animal);
        } else {
            livesAnimalsOnMap.put(newPosition, new ArrayList<>());
            livesAnimalsOnMap.get(newPosition).add(animal);
        }
    }

    @Override
    public void addAnimalToMap(IAnimal animal) {
        if (animal.getPosition().y >= mapHeight || animal.getPosition().y < 0 || animal.getPosition().x >= mapWidth || animal.getPosition().x < 0) {
            throw new IllegalArgumentException("Animal's position is invalid");
        } else {
            livesAnimalsOnMap.get(animal.getPosition()).add(animal);
        }
    }


    @Override
    public void removeAnimalFromMap(IAnimal animal) {
        removeLiveAnimalFromHashMap(animal);
        updatePositionWithNumberOfDeath(animal);
        updateNumbersOfDeathsAtPosition(animal);
    }

    private void removeLiveAnimalFromHashMap(IAnimal animal) {
        livesAnimalsOnMap.get(animal.getPosition()).remove(animal);
    }

    private void updatePositionWithNumberOfDeath(IAnimal animal) {
        int numberOfDeath = quantityOfDeathsAtPosition.get(animal.getPosition());
        positionWithNumberOfDeath.get(numberOfDeath).remove(animal.getPosition());
        if (positionWithNumberOfDeath.get(numberOfDeath).size() == 0) {
            positionWithNumberOfDeath.remove(numberOfDeath);
        }
        positionWithNumberOfDeath.computeIfAbsent(numberOfDeath + 1, k -> new ArrayList<Vector2D>());
        positionWithNumberOfDeath.get(numberOfDeath + 1).add(animal.getPosition());
    }

    private void updateNumbersOfDeathsAtPosition(IAnimal animal) {
        int numberOfDeath = quantityOfDeathsAtPosition.get(animal.getPosition());
        quantityOfDeathsAtPosition.put(animal.getPosition(), numberOfDeath + 1);
    }

    @Override
    public void addPlantToMap(Plant plant) {
        if (plant.getPosition().y >= mapHeight || plant.getPosition().y < 0 || plant.getPosition().x >= mapWidth || plant.getPosition().x < 0){
            throw new IllegalArgumentException("Plant's position is invalid");
        } else if (getPlantAtPosition(plant.getPosition()) != null) {
            throw new IllegalArgumentException("Plant's position is invalid");
        } else {
            plantsOnMap.put(plant.getPosition(), plant);
        }
    }

    @Override
    public void removePlantFromMap(Plant plant) {
        plantsOnMap.remove(plant.getPosition());
    }


}
