package com.example.evolutiongenerator.interfaces;

import com.example.evolutiongenerator.Gene;
import com.example.evolutiongenerator.direction.MapDirection;
import com.example.evolutiongenerator.direction.Vector2D;

public interface IAnimal {
    Vector2D getPosition();
    MapDirection getDirection();
    int getEnergy();
    int getAge();
    int getChildrenNumber();
    int getActualGenome();
    int[] getGenomes();
    int getEatenPlantsNumber();
    int getDeathDay();
    void setDeathDay(int day);
    void changeEatenPlantsNumber(int number);
    Gene getGene();
    void changeEnergy(int amount);
    void move();
    void changeChildrenNumber(int quantity);
    void changeAge(int amount);
    void addPositionObserver(IMapElementsObserver observer);
    void removePositionObserver(IMapElementsObserver observer);

}
