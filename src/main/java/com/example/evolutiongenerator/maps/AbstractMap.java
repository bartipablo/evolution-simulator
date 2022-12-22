package com.example.evolutiongenerator.maps;

import com.example.evolutiongenerator.interfaces.IAnimal;
import com.example.evolutiongenerator.Plant;
import com.example.evolutiongenerator.direction.Vector2D;
import com.example.evolutiongenerator.interfaces.IMap;
import com.example.evolutiongenerator.interfaces.IMapElementsObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractMap implements IMap, IMapElementsObserver {

    protected final int mapHeight;
    protected final int mapWidth;
    private final Map<Vector2D, List<IAnimal>> livesAnimalsOnMap = new HashMap<>();
    private final Map<Vector2D, Integer> deathsQuantityInPositions = new HashMap<>();
    private final Map<Vector2D, Plant> plantsOnMap = new HashMap<>();

    AbstractMap(int mapHeight, int mapWidth) {
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        createHashMaps();
    }

    private void createHashMaps() {
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                Vector2D vector2D = new Vector2D(x, y);
                //livesAnimalsOnMap.put(vector2D, new ArrayList<IAnimal>());
                deathsQuantityInPositions.put(vector2D, 0);
            }
        }
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
    public void positionChanged(IAnimal animal, Vector2D oldPosition, Vector2D newPosition) {
        livesAnimalsOnMap.get(oldPosition).remove(animal);
        livesAnimalsOnMap.get(newPosition).add(animal);
    }

    @Override
    public void addedNewAnimal(IAnimal animal) {
        if (livesAnimalsOnMap.get(animal.getPosition()) != null) {
            livesAnimalsOnMap.get(animal.getPosition()).add(animal);
        } else {
            livesAnimalsOnMap.put(animal.getPosition(), new ArrayList<IAnimal>());
            livesAnimalsOnMap.get(animal.getPosition()).add(animal);
        }

    }

    @Override
    public void removedAnimal(IAnimal animal) {
        livesAnimalsOnMap.get(animal.getPosition()).remove(animal);
        if (livesAnimalsOnMap.get(animal.getPosition()).size() == 0) {
            livesAnimalsOnMap.remove(animal.getPosition());
        }
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
