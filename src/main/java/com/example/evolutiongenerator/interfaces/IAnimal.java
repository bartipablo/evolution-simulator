package com.example.evolutiongenerator.interfaces;

import com.example.evolutiongenerator.Gene;
import com.example.evolutiongenerator.direction.MapDirection;
import com.example.evolutiongenerator.direction.Vector2D;

public interface IAnimal {
    Vector2D getPosition();

    MapDirection getDirection();

    int getEnergy();

    int getAge();

    int getNumberOfChildren();

    int getActualGenome();

    int[] getGenome();

    int getNumberOfEatenPlants();

    int getDeathDay();

    void setDeathDay(int day);

    void increaseNumberOfEatenPlants(int number);

    Gene getGene();

    void increaseEnergy(int amount);

    void move();

    void increaseNumberOfChildren(int quantity);

    void increaseAge(int amount);

}
