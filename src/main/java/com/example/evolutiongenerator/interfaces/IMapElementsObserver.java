package com.example.evolutiongenerator.interfaces;

import com.example.evolutiongenerator.Plant;
import com.example.evolutiongenerator.direction.Vector2D;

public interface IMapElementsObserver {
    void addedNewPlant(Plant plant);
    void removedPlant(Plant plant);
    void addedNewAnimal(IAnimal animal);

    void removedAnimal(IAnimal animal);
    void positionChanged(IAnimal animal, Vector2D oldPosition, Vector2D newPosition);

}
