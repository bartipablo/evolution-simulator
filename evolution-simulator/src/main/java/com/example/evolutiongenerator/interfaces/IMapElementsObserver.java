package com.example.evolutiongenerator.interfaces;

import com.example.evolutiongenerator.Plant;
import com.example.evolutiongenerator.direction.Vector2D;

public interface IMapElementsObserver {
    //plants population observer:
    void addPlantToMap(Plant plant);
    void removePlantFromMap(Plant plant);

    //animals population observer:
    void addAnimalToMap(IAnimal animal);
    void removeAnimalFromMap(IAnimal animal);

    //animal position observer:
    void changePositionOnMap(IAnimal animal, Vector2D oldPosition, Vector2D newPosition);

}
