package com.example.evolutiongenerator;

import com.example.evolutiongenerator.direction.MapDirection;
import com.example.evolutiongenerator.interfaces.IMap;

public class Animal {
    private Vector2D position;
    private MapDirection mapDirection;
    private final IMap map;

    //constuctor------------------------------------------
    Animal(Vector2D initialPosition, IMap map) {
        this.position = initialPosition;
        this.map = map;
    }
    //---------------------------------------------------

    public Vector2D getPosition() {
        return position;
    }

    public MapDirection getMapDirection() {
        return mapDirection;
    }

    public void setPosition(Vector2D newPosition) {
        position = newPosition;
    }

    public void setMapDirection(MapDirection newMapDirection) {
        mapDirection = newMapDirection;
    }

    public void move() {

    }

}
