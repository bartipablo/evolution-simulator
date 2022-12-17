package com.example.evolutiongenerator;

import com.example.evolutiongenerator.direction.MapDirection;
import com.example.evolutiongenerator.direction.Vector2D;
import com.example.evolutiongenerator.interfaces.IMap;
import com.example.evolutiongenerator.interfaces.IPopulationChangeObserver;
import com.example.evolutiongenerator.interfaces.IReproduction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Population {

    private int quantityPopulation;
    private final IMap map;
    private final List<IPopulationChangeObserver> observers = new ArrayList<>();
    private final List<Animal> animals = new ArrayList<>();
    private final IReproduction reproductionVariant;
    private final int minimumEnergyToReproduction;
    private final int energyUsedToReproduction;
    private final int genomeLength;
    private final int quantityMutations;

    //constructors----------------------------
    Population(int quantityPopulation, int minimumEnergyToReproduction, int genomeLength, int energyUsedToReproduction, int qunatityMutantions, IMap map, IReproduction reproductionVariant) {
        this.reproductionVariant = reproductionVariant;
        this.minimumEnergyToReproduction = minimumEnergyToReproduction;
        this.map = map;
        this.quantityPopulation = quantityPopulation;
        this.genomeLength = genomeLength;
        this.quantityMutations = qunatityMutantions;
        this.energyUsedToReproduction = energyUsedToReproduction;
        createNewPopulation();
    }

    private void createNewPopulation() {
        Random random = new Random();
        for (int i = 0; i < quantityPopulation; i++) {
            Vector2D initialPosition = Vector2D.generateRandomVector2D(0, map.getMapWidth(), 0, map.getMapHeight());
            MapDirection initialDirection = MapDirection.generateRandomDirection();
            Animal animal = new Animal(initialPosition, initialDirection, map, 5);
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

    //reproduction--------------------------------------------
    public void reproduction() {
        Vector2D[] positions = map.getAnimalsPositions();
        for (Vector2D animalPosition : positions) {
            List<Animal> animalsAtPosition = map.getAnimalsAtPosition(animalPosition);
            if (animalsAtPosition.size() > 1) {
                Animal animalToReproductionA = getTwoAnimalsWithTheMostEnergy(animalsAtPosition)[0];
                Animal animalToReproductionB = getTwoAnimalsWithTheMostEnergy(animalsAtPosition)[1];
                if (animalToReproductionA.getEnergy() > minimumEnergyToReproduction && animalToReproductionB.getEnergy() > minimumEnergyToReproduction) {
                    createNewAnimal(animalToReproductionA, animalToReproductionB);
                }
            }
        }
    }

    private void createNewAnimal(Animal animalA, Animal animalB) {
        Animal newAnimal = reproductionVariant.newAnimal(animalA, animalB, genomeLength, quantityMutations, energyUsedToReproduction, map);
        addAnimal(newAnimal);
        informObserversAboutNewAnimal(newAnimal);
        quantityPopulation += 1;
    }

    private Animal[] getTwoAnimalsWithTheMostEnergy(List<Animal> animalsAtPosition) {
        Animal[] twoAnimals = new Animal[2];
        twoAnimals[0] = animalsAtPosition.get(0);
        twoAnimals[1] = animalsAtPosition.get(1);
        for (int i = 2; i < animalsAtPosition.size(); i++) {
            Animal animal = animalsAtPosition.get(i);
            if (twoAnimals[0].getEnergy() > twoAnimals[1].getEnergy() && animal.getEnergy() > twoAnimals[1].getEnergy()) {
                twoAnimals[1] = animal;
            } else if (twoAnimals[1].getEnergy() > twoAnimals[0].getEnergy() && animal.getEnergy() > twoAnimals[0].getEnergy()) {
                twoAnimals[0] = animal;
            } else if (twoAnimals[1].getEnergy() == twoAnimals[0].getEnergy() && animal.getEnergy() > twoAnimals[0].getEnergy()) {
                twoAnimals[0] = animal;
            }
        }
        return twoAnimals;
    }

    //--------------------------------------------------------

    //vanishing-----------------------------
    public void vanishing() {
        for (Animal animal : animals) {
            if (animal.getEnergy() <= 0) {
                removeAnimal(animal);
                informObserversAboutRemoveAnimal(animal);
                quantityPopulation -= 1;
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
