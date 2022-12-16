package com.example.evolutiongenerator;

import com.example.evolutiongenerator.direction.MapDirection;
import com.example.evolutiongenerator.interfaces.IMap;
import com.example.evolutiongenerator.interfaces.IMapElement;
import com.example.evolutiongenerator.interfaces.IPositionChangeObserver;

import java.util.ArrayList;
import java.util.List;

public class Animal implements IMapElement {

    int energy;
    private final List<IPositionChangeObserver> observers = new ArrayList<>();
    private Vector2D position;
    private MapDirection direction;
    private final IMap map;

    //constructors------------------------------------------
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

    public int getEnergy() {
        return energy;
    }


    public void move() {

    }

    public void addObserver(IPositionChangeObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer) {
        observers.remove(observer);
    }

    private void informObserversAboutChanges(Vector2D oldPosition, Vector2D newPosition) {
        for (IPositionChangeObserver observer : observers) {
            observer.positionChanged(this, oldPosition, newPosition);
        }
    }

}
