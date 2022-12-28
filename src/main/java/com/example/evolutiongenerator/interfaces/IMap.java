package com.example.evolutiongenerator.interfaces;

import com.example.evolutiongenerator.Plant;
import com.example.evolutiongenerator.direction.Vector2D;
import com.example.evolutiongenerator.direction.MapDirection;

import java.util.List;

public interface IMap {

    int getMapHeight();

    int getMapWidth();

    public List<IAnimal> getAnimalsAtPosition(Vector2D position);

    public Vector2D[] getAnimalsPositions();

    List<Vector2D> getPositionsSortedByNumbersOfDeaths();

    Plant getPlantAtPosition(Vector2D position);

    Vector2D calculatePositionAfterMovement(Vector2D position);

    MapDirection calculateDirectionAfterMovement(Vector2D position, MapDirection direction);

}
