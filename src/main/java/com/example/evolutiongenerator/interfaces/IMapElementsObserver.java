package com.example.evolutiongenerator.interfaces;

import com.example.evolutiongenerator.Plant;
import com.example.evolutiongenerator.direction.Vector2D;

public interface IMapElementsObserver {
    //plants population observer:
    void addedNewPlant(Plant plant);
    void removedPlant(Plant plant);

    //animals population observer:
    void addedNewAnimal(IAnimal animal);
    void removedAnimal(IAnimal animal);

    //animal position observer:
    void positionChanged(IAnimal animal, Vector2D oldPosition, Vector2D newPosition);

}
