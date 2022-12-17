package com.example.evolutiongenerator;

import com.example.evolutiongenerator.direction.MapDirection;
import com.example.evolutiongenerator.interfaces.IMap;
import com.example.evolutiongenerator.interfaces.IPopulationChangeObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Population {

    private int quantityPopulation;
    private final IMap map;
    private final List<IPopulationChangeObserver> observers = new ArrayList<>();
    private final List<Animal> animals = new ArrayList<>();

    //constructors----------------------------
    Population(IMap map, int quantityPopulation) {
        this.map = map;
        this.quantityPopulation = quantityPopulation;
        createNewPopulation();
    }

    private void createNewPopulation() {
        Random random = new Random();
        for (int i = 0; i < quantityPopulation; i++) {
            Vector2D initialPosition = Vector2D.generateRandomVector2D(0, map.getMapWidth(), 0, map.getMapHeight());
            MapDirection initialDirection = MapDirection.generateRandomDirection();
            Animal animal = new Animal(initialPosition, initialDirection, map);
            addAnimal(animal);
            informObserversAboutNewAnimal(animal);
        }
    }
    //----------------------------------------

    private void addAnimal(Animal animal) {
        animals.add(animal);
    }

    private void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    //reproduction---------------------------
    public void reproduction() {
        Vector2D[] positions = map.getAnimalsPositions();
        for (Vector2D animalPosition : positions) {

        }

    }

    //---------------------------------------

    //vanishing-----------------------------
    public void vanishing() {
        for (Animal animal : animals) {
            if (animal.getEnergy() <= 0) {
                removeAnimal(animal);
                informObserversAboutRemoveAnimal(animal);
            }
        }
    }
    //---------------------------------------


    //observers------------------------------
    public void addNewObserver(IPopulationChangeObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(IPopulationChangeObserver observer) {
        observers.remove(observer);
    }

    private void informObserversAboutNewAnimal(Animal animal) {
        for (IPopulationChangeObserver observer : observers) {
            observer.addedNewAnimal(animal);
        }
    }

    private void informObserversAboutRemoveAnimal(Animal animal) {
        for (IPopulationChangeObserver observer : observers) {
            observer.removedAnimal(animal);
        }
    }
    //---------------------------------------
}
