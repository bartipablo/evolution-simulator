package com.example.evolutiongenerator.interfaces;

import com.example.evolutiongenerator.Animal;
import com.example.evolutiongenerator.Vector2D;
import com.example.evolutiongenerator.direction.MapDirection;

public interface IMap {

    int getMapHeight();

    int getMapWidth();

    Vector2D calculatePositionAfterMovement(Vector2D position, MapDirection direction);

    MapDirection calculateDirectionAfterMovement(Vector2D position, MapDirection direction);

}
