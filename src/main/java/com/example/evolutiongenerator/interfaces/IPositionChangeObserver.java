package com.example.evolutiongenerator.interfaces;

import com.example.evolutiongenerator.Animal;
import com.example.evolutiongenerator.direction.Vector2D;

public interface IPositionChangeObserver {

    void positionChanged(Animal animal, Vector2D oldPosition, Vector2D newPosition);

}
