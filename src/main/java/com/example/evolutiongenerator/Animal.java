package com.example.evolutiongenerator;

import com.example.evolutiongenerator.direction.MapDirection;
import com.example.evolutiongenerator.interfaces.IMap;

public class Animal {
    private Vector2D position;
    private MapDirection direction;
    private final IMap map;

    //constuctor------------------------------------------
    Animal(Vector2D initialPosition, MapDirection initialDirection, IMap map) {
        this.position = initialPosition;
        this.direction = initialDirection;
        this.map = map;
    }
    //---------------------------------------------------

    public Vector2D getPosition() {
        return position;
    }

    public MapDirection getDirection() {
        return direction;
    }

    public void setPosition(Vector2D newPosition) {
        position = newPosition;
    }

    public void setDirection(MapDirection newMapDirection) {
        direction = newMapDirection;
    }

    public void move() {

    }

}
