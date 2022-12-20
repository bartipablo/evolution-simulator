package com.example.evolutiongenerator.maps;

import com.example.evolutiongenerator.interfaces.IAnimal;
import com.example.evolutiongenerator.Plant;
import com.example.evolutiongenerator.direction.Vector2D;
import com.example.evolutiongenerator.interfaces.IMap;
import com.example.evolutiongenerator.interfaces.IMapElementsObserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractMap implements IMap, IMapElementsObserver {

    protected final int mapHeight;
    protected final int mapWidth;
    private final Map<Vector2D, List<IAnimal>> animalsOnMap = new HashMap<>();
    private final Map<Vector2D, Plant> plantsOnMap = new HashMap<>();

    AbstractMap(int mapHeight, int mapWidth) {
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
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
    public List<IAnimal> getAnimalsAtPosition(Vector2D position) {
        return animalsOnMap.get(position);
    }

    @Override
    public Plant getPlantAtPosition(Vector2D position) {
        return plantsOnMap.get(position);
    }

    @Override
    public Vector2D[] getAnimalsPositions() {
        return animalsOnMap.keySet().toArray(new Vector2D[0]);
    }

    @Override
    public void positionChanged(IAnimal animal, Vector2D oldPosition, Vector2D newPosition) {
        animalsOnMap.get(oldPosition).remove(animal);
        animalsOnMap.get(newPosition).add(animal);
    }

    @Override
    public void addedNewAnimal(IAnimal animal) {
        animalsOnMap.get(animal.getPosition()).add(animal);
    }

    @Override
    public void removedAnimal(IAnimal animal) {
        animalsOnMap.get(animal.getPosition()).remove(animal);
    }

    @Override
    public void addedNewPlant(Plant plant) {
        plantsOnMap.put(plant.getPosition(), plant);
    }

    @Override
    public void removedPlant(Plant plant) {
        plantsOnMap.remove(plant.getPosition());
    }

}
