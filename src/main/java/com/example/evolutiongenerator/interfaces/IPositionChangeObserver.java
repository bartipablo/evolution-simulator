package com.example.evolutiongenerator.interfaces;

import com.example.evolutiongenerator.animals.AbstractAnimal;
import com.example.evolutiongenerator.direction.Vector2D;

public interface IPositionChangeObserver {

    void positionChanged(IAnimal animal, Vector2D oldPosition, Vector2D newPosition);

}
