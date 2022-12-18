package com.example.evolutiongenerator;

import com.example.evolutiongenerator.direction.MapDirection;
import com.example.evolutiongenerator.direction.Vector2D;
import com.example.evolutiongenerator.interfaces.IMap;
import com.example.evolutiongenerator.interfaces.IPopulationChangeObserver;
import com.example.evolutiongenerator.interfaces.IReproduction;
import com.example.evolutiongenerator.interfaces.ITerrain;

import java.util.*;

public class Population {

    private int quantityPopulation;
    private final IMap map;
    private final List<IPopulationChangeObserver> observers = new ArrayList<>();
    private final List<Animal> animals = new ArrayList<>();
    private final IReproduction reproductionVariant;
    private final ITerrain terrain;
    private final int minimumEnergyToReproduction;
    private final int energyUsedToReproduction;
    private final int genomeLength;
    private final int quantityMutations;

    //constructors----------------------------
    Population(int quantityPopulation, int minimumEnergyToReproduction, int genomeLength, int energyUsedToReproduction,
               int quantityMutations, IMap map, IReproduction reproductionVariant, ITerrain terrain) {
        this.reproductionVariant = reproductionVariant;
        this.minimumEnergyToReproduction = minimumEnergyToReproduction;
        this.map = map;
        this.quantityPopulation = quantityPopulation;
        this.genomeLength = genomeLength;
        this.quantityMutations = quantityMutations;
        this.energyUsedToReproduction = energyUsedToReproduction;
        this.terrain = terrain;
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

    //reproduction-------------------------------------------- TO DO!!!!!!!!!!!!
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

    //vanishing-----------------------------------------------
    public void vanishing() {
        for (Animal animal : animals) {
            if (animal.getEnergy() <= 0) {
                removeAnimal(animal);
                informObserversAboutRemoveAnimal(animal);
                quantityPopulation -= 1;
            }
        }
    }
    //--------------------------------------------------------

    //consumption--------------------------------------------
    public void consumptions() {
        Vector2D[] positions = map.getAnimalsPositions();
        for (Vector2D position : positions) {
            Plant plant = map.getPlantAtPosition(position);
            if (plant != null) {
                Animal animal = chooseAnimalToConsumption(map.getAnimalsAtPosition(position));
                animal.increaseEnergy(plant.getEnergy());
                terrain.removePlant(plant);
                //inform plant observer
            }
        }
    }

    private Animal chooseAnimalToConsumption(List<Animal> animalsAtPosition) {
        Random random = new Random();
        if (animalsAtPosition.size() == 1) {
            return animalsAtPosition.get(0);
        }

        sortTheAnimalsByEnergy(animalsAtPosition);
        if (animalsAtPosition.get(0).getEnergy() > animalsAtPosition.get(1).getEnergy()) {
            return animalsAtPosition.get(0);
        }

        sortTheAnimalsByAge(animalsAtPosition);
        if (animalsAtPosition.get(0).getAge() > animalsAtPosition.get(1).getAge()) {
            return animalsAtPosition.get(0);
        }

        sortTheAnimalsByNumberOfChildren(animalsAtPosition);
        if (animalsAtPosition.get(0).getNumberOfChildren() > animalsAtPosition.get(1).getNumberOfChildren()) {
            return animalsAtPosition.get(0);
        }
        return animalsAtPosition.get(random.nextInt(0, animalsAtPosition.size()));
    }
    //-------------------------------------------------------------




    //sorting---------------------------------------------------
    private void sortTheAnimalsByEnergy(List<Animal> animalList) {
        animalList.sort(new Comparator<Animal>() {
            @Override
            public int compare(Animal o1, Animal o2) {
                return -(o1.getEnergy() - o2.getEnergy());
            }
        });
    }

    private void sortTheAnimalsByAge(List<Animal> animalList) {
        animalList.sort(new Comparator<Animal>() {
            @Override
            public int compare(Animal o1, Animal o2) {
                return -(o1.getAge() - o2.getAge());
            }
        });
    }

    private void sortTheAnimalsByNumberOfChildren(List<Animal> animalList) {
        animalList.sort(new Comparator<Animal>() {
            @Override
            public int compare(Animal o1, Animal o2) {
                return -(o1.getNumberOfChildren() - o2.getNumberOfChildren());
            }
        });
    }
    //-------------------------------------------------------------------




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
