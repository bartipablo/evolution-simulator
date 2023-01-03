package com.example.evolutiongenerator.interfaces;

import com.example.evolutiongenerator.Plant;
import com.example.evolutiongenerator.direction.Vector2D;
import com.example.evolutiongenerator.direction.MapDirection;
import com.example.evolutiongenerator.gui.SimulationSceneController;

import java.util.List;

public interface IMap {

    Object objectAt(Vector2D position);
    boolean isOccupied(Vector2D position);

    int getMapHeight();

    int getMapWidth();

    public List<IAnimal> getAnimalsAtPosition(Vector2D position);

    public Vector2D[] getAnimalsPositions();

    List<Vector2D> getPositionsSortedByNumbersOfDeaths();

    Plant getPlantAtPosition(Vector2D position);

    Vector2D calculatePositionAfterMovement(Vector2D position);

    MapDirection calculateDirectionAfterMovement(Vector2D position, MapDirection direction);

    String getPathImageAtPosition(Vector2D position);


}
