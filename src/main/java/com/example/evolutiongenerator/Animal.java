package com.example.evolutiongenerator;

import com.example.evolutiongenerator.direction.MapDirection;
import com.example.evolutiongenerator.direction.Vector2D;
import com.example.evolutiongenerator.interfaces.IMap;
import com.example.evolutiongenerator.interfaces.IMapElement;
import com.example.evolutiongenerator.interfaces.IPositionChangeObserver;

import java.util.ArrayList;
import java.util.List;

public class Animal implements IMapElement {

    private int energy;
    private int age;
    private int numberOfChildren;
    private final List<IPositionChangeObserver> observers = new ArrayList<>();
    private Vector2D position;
    private MapDirection direction;
    private final IMap map;
    private Gene gene;

    //constructors------------------------------------------
    Animal(Vector2D initialPosition, MapDirection initialDirection, IMap map, int genomeLength) {
        this.position = initialPosition;
        this.direction = initialDirection;
        this.map = map;
        this.gene = new Gene(genomeLength);
    }

    public Animal(Vector2D initialPosition, MapDirection initialDirection, IMap map, Gene gene) {
        this.position = initialPosition;
        this.direction = initialDirection;
        this.map = map;
        this.gene = gene;
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

    public int getAge() {
        return age;
    }

    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    public Gene getGene() {
        return gene;
    }


    public void move() {

    }

    public void reduceEnergy(int amount) {
        energy -= amount;
    }

    public void increaseEnergy(int amount) {
        energy += amount;
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
