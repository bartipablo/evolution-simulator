package com.example.evolutiongenerator.maps;

import com.example.evolutiongenerator.Animal;
import com.example.evolutiongenerator.Plant;
import com.example.evolutiongenerator.Vector2D;
import com.example.evolutiongenerator.interfaces.IMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractMap implements IMap {

    private final Map<Vector2D, List<Animal>> animalsOnMap = new HashMap<>();
    private final Map<Vector2D, Plant>  plantsOnMap = new HashMap<>();


    public List<Animal> getAnimalsAtPosition(Vector2D position) {
        return animalsOnMap.get(position);
    }

    public void putAnimalAtMap(Animal animal) {
        animalsOnMap.get(animal.getPosition()).add(animal);
    }

    public void removeAnimalFromMap(Animal animal) {
        animalsOnMap.get(animal.getPosition()).remove(animal);
    }

    public void changeAnimalPositionAtMap(Animal animal, Vector2D newPosition) {
        removeAnimalFromMap(animal);
        animalsOnMap.get(newPosition).add(animal);
    }


}
