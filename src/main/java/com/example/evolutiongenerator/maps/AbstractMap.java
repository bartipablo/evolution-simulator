package com.example.evolutiongenerator.maps;

import com.example.evolutiongenerator.interfaces.IAnimal;
import com.example.evolutiongenerator.Plant;
import com.example.evolutiongenerator.direction.Vector2D;
import com.example.evolutiongenerator.interfaces.IGuiObserver;
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
    private final List<IGuiObserver> guiObservers = new ArrayList<>();


    //constructors-------------------------------------------------
    public AbstractMap(int mapHeight, int mapWidth) {
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        initialHashMaps();
    }

    private void initialHashMaps() {
        positionWithNumberOfDeath.put(0, new ArrayList<>());
        for (int i = 0; i < mapWidth; i++) {
            for (int j = 0; j < mapHeight; j++) {
                numbersOfDeathsAtPosition.put(new Vector2D(i, j), 0);
                positionWithNumberOfDeath.get(0).add(new Vector2D(i, j));
            }
        }
    }
    //-----------------------------------------------------------------

    @Override
    public Object objectAt(Vector2D position) {
        if (livesAnimalsOnMap.get(position) != null && livesAnimalsOnMap.get(position).size() > 0) {
            return livesAnimalsOnMap.get(position).get(0);
        }
        return plantsOnMap.get(position);
    }

    @Override
    public boolean isOccupied(Vector2D position) {
        if (livesAnimalsOnMap.get(position) != null && livesAnimalsOnMap.get(position).size() > 0) {
            return true;
        }
        return plantsOnMap.get(position) != null;
    }

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
        return livesAnimalsOnMap.keySet().toArray(new Vector2D[0]);
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
        informObserversAboutChanges();
    }

    @Override
    public void addAnimalToMap(IAnimal animal) {
        if (animal.getPosition().y >= mapHeight || animal.getPosition().y < 0 || animal.getPosition().x >= mapWidth || animal.getPosition().x < 0) {
            throw new InvalidParameterException("Animal's position is invalid");
        } else if (livesAnimalsOnMap.get(animal.getPosition()) != null) {
            livesAnimalsOnMap.get(animal.getPosition()).add(animal);
        } else {
            livesAnimalsOnMap.put(animal.getPosition(), new ArrayList<>());
            livesAnimalsOnMap.get(animal.getPosition()).add(animal);
        }
        informObserversAboutChanges();
    }

    @Override
    public void removeAnimalFromMap(IAnimal animal) {
        removeLiveAnimalFromHashMap(animal);
        updatePositionWithNumberOfDeath(animal);
        updateNumbersOfDeathsAtPosition(animal);
        informObserversAboutChanges();
    }

    private void removeLiveAnimalFromHashMap(IAnimal animal) {
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
        positionWithNumberOfDeath.computeIfAbsent(numberOfDeath + 1, k -> new ArrayList<Vector2D>());
        positionWithNumberOfDeath.get(numberOfDeath + 1).add(animal.getPosition());
    }

    private void updateNumbersOfDeathsAtPosition(IAnimal animal) {
        int numberOfDeath = numbersOfDeathsAtPosition.get(animal.getPosition());
        numbersOfDeathsAtPosition.put(animal.getPosition(), numberOfDeath + 1);
    }

    @Override
    public void addPlantToMap(Plant plant) {
        if (plant.getPosition().y >= mapHeight || plant.getPosition().y < 0 || plant.getPosition().x >= mapWidth || plant.getPosition().x < 0){
            throw new IllegalArgumentException("Plant's position is invalid");
        } else if (getPlantAtPosition(plant.getPosition()) != null) {
            throw new IllegalArgumentException("Plant's position is invalid");
        } else {
            plantsOnMap.put(plant.getPosition(), plant);
            informObserversAboutChanges();
        }
    }

    @Override
    public void removePlantFromMap(Plant plant) {
        plantsOnMap.remove(plant.getPosition());
    }

    @Override
    public void addGuiObserver(IGuiObserver observer) {
        guiObservers.add(observer);
    }

    private void informObserversAboutChanges() {
        for (IGuiObserver guiObserver : guiObservers) {
            guiObserver.changed();
        }
    }
}
