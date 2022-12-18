package com.example.evolutiongenerator.animals;

import com.example.evolutiongenerator.Gene;
import com.example.evolutiongenerator.direction.MapDirection;
import com.example.evolutiongenerator.direction.Vector2D;
import com.example.evolutiongenerator.interfaces.IAnimal;
import com.example.evolutiongenerator.interfaces.IMap;
import com.example.evolutiongenerator.interfaces.IPositionChangeObserver;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAnimal implements IAnimal {

    protected int actualGenomeIndex;
    private int energy;
    private int age;
    private int numberOfChildren;
    private final List<IPositionChangeObserver> observers = new ArrayList<>();
    protected Vector2D position;
    protected MapDirection direction;
    protected final IMap map;
    protected Gene gene;

    //constructors------------------------------------------
    AbstractAnimal(Vector2D initialPosition, MapDirection initialDirection, IMap map, int genomeLength) {
        this.position = initialPosition;
        this.direction = initialDirection;
        this.map = map;
        this.gene = new Gene(genomeLength);
        this.actualGenomeIndex = 0;
    }

    public AbstractAnimal(Vector2D initialPosition, MapDirection initialDirection, IMap map, Gene gene) {
        this.position = initialPosition;
        this.direction = initialDirection;
        this.map = map;
        this.gene = gene;
    }
    //---------------------------------------------------

    @Override
    public Vector2D getPosition() {
        return position;
    }

    @Override
    public MapDirection getDirection() {
        return direction;
    }

    @Override
    public int getEnergy() {
        return energy;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    @Override
    public Gene getGene() {
        return gene;
    }

    @Override
    public void reduceEnergy(int amount) {
        energy -= amount;
    }

    @Override
    public void increaseEnergy(int amount) {
        energy += amount;
    }

    @Override
    public void increaseNumberOfChildren(int quantity) {
        numberOfChildren += quantity;
    }

    @Override
    public void increaseAge(int amount) {
        age += amount;
    }

    public void addObserver(IPositionChangeObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer) {
        observers.remove(observer);
    }

    protected void informObserversAboutChanges(Vector2D oldPosition, Vector2D newPosition) {
        for (IPositionChangeObserver observer : observers) {
            observer.positionChanged(this, oldPosition, newPosition);
        }
    }

}
