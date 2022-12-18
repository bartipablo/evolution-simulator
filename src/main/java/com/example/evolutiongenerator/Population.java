package com.example.evolutiongenerator;

import com.example.evolutiongenerator.animals.AnimalBehaviourA;
import com.example.evolutiongenerator.animals.AnimalBehaviourB;
import com.example.evolutiongenerator.interfaces.IAnimal;
import com.example.evolutiongenerator.direction.MapDirection;
import com.example.evolutiongenerator.direction.Vector2D;
import com.example.evolutiongenerator.interfaces.IMap;
import com.example.evolutiongenerator.interfaces.IPopulationChangeObserver;
import com.example.evolutiongenerator.interfaces.IReproduction;
import com.example.evolutiongenerator.interfaces.ITerrain;
import com.example.evolutiongenerator.variants.BehaviourVariant;

import java.util.*;

public class Population {

    private int quantityPopulation;
    private final IMap map;
    private final List<IPopulationChangeObserver> observers = new ArrayList<>();
    private final List<IAnimal> animals = new ArrayList<>();
    private final IReproduction reproductionVariant;
    private final ITerrain terrain;
    private final int minimumEnergyToReproduction;
    private final int energyUsedToReproduction;
    private final int genomeLength;
    private final int quantityMutations;
    private final BehaviourVariant behaviourVariant;

    //constructors-------------------------------------------------------------------------
    Population(int quantityPopulation, int minimumEnergyToReproduction, int genomeLength, int energyUsedToReproduction,
               int quantityMutations, IMap map, IReproduction reproductionVariant, ITerrain terrain, BehaviourVariant behaviourVariant) {
        this.reproductionVariant = reproductionVariant;
        this.minimumEnergyToReproduction = minimumEnergyToReproduction;
        this.map = map;
        this.quantityPopulation = quantityPopulation;
        this.genomeLength = genomeLength;
        this.quantityMutations = quantityMutations;
        this.energyUsedToReproduction = energyUsedToReproduction;
        this.terrain = terrain;
        this.behaviourVariant = behaviourVariant;
        generateNewPopulation();
    }

    private void generateNewPopulation() {
        Random random = new Random();
        for (int i = 0; i < quantityPopulation; i++) {
            Vector2D initialPosition = Vector2D.generateRandomVector2D(0, map.getMapWidth(), 0, map.getMapHeight());
            MapDirection initialDirection = MapDirection.generateRandomDirection();
            IAnimal animal;
            if (behaviourVariant == BehaviourVariant.FULL_PREDESTINATION) {
                animal = new AnimalBehaviourA(initialPosition, initialDirection, map, genomeLength);
            } else {
                animal = new AnimalBehaviourB(initialPosition, initialDirection, map, genomeLength);
            }
            addAnimal(animal);
            informObserversAboutNewAnimal(animal);
        }
    }
    //-------------------------------------------------------------------------------------------

    private void addAnimal(IAnimal animal) {
        animals.add(animal);
    }

    private void removeAnimal(IAnimal animal) {
        animals.remove(animal);
    }

    //reproduction--------------------------------------------------------------------------------
    public void reproduction() {
        Vector2D[] positions = map.getAnimalsPositions();
        for (Vector2D animalPosition : positions) {
            List<IAnimal> animalsAtPosition = map.getAnimalsAtPosition(animalPosition);
            if (animalsAtPosition.size() > 1) {
                List<IAnimal> animalsToReproduction = chooseAnimalsToReproduction(animalsAtPosition);
                if (animalsToReproduction.get(0).getEnergy() > minimumEnergyToReproduction && animalsToReproduction.get(0).getEnergy() > minimumEnergyToReproduction) {
                    createNewAnimal(animalsToReproduction.get(0), animalsToReproduction.get(0));
                }
            }
        }
    }

    private List<IAnimal> chooseAnimalsToReproduction(List<IAnimal> animalsAtPosition) {
        List<IAnimal> drawsList;
        if (animalsAtPosition.size() == 2) {
            return animalsAtPosition;
        }

        sortTheAnimalsByEnergy(animalsAtPosition);
        if (animalsAtPosition.get(1).getEnergy() > animalsAtPosition.get(2).getEnergy()) {
            return animalsAtPosition.subList(0, 1);
        }

        drawsList = getEnergyTiesList(animalsAtPosition);
        sortTheAnimalsByAge(drawsList);
        if (drawsList.get(1).getAge() > drawsList.get(2).getAge()) {
            return drawsList.subList(0, 1);
        }

        drawsList = getAgeTiesList(drawsList);
        sortTheAnimalsByNumberOfChildren(drawsList);
        if (drawsList.get(1).getNumberOfChildren() > drawsList.get(2).getNumberOfChildren()) {
            return drawsList.subList(0, 1);
        }

        drawsList = getAgeTiesList(drawsList);
        Collections.shuffle(drawsList);
        return drawsList.subList(0, 1);
    }

    private void createNewAnimal(IAnimal animalA, IAnimal animalB) {
        IAnimal newAnimal = reproductionVariant.newAnimal(animalA, animalB, genomeLength, quantityMutations, energyUsedToReproduction, map);
        addAnimal(newAnimal);
        informObserversAboutNewAnimal(newAnimal);
        quantityPopulation += 1;
    }

    //------------------------------------------------------------------------------------------------

    //vanishing---------------------------------------------------------------------------------------
    public void vanishing() {
        for (IAnimal animal : animals) {
            if (animal.getEnergy() <= 0) {
                removeAnimal(animal);
                informObserversAboutRemoveAnimal(animal);
                quantityPopulation -= 1;
            }
        }
    }
    //-------------------------------------------------------------------------------------------------

    //consumption--------------------------------------------------------------------------------------
    public void consumptions() {
        Vector2D[] positions = map.getAnimalsPositions();
        for (Vector2D position : positions) {
            Plant plant = map.getPlantAtPosition(position);
            if (plant != null) {
                IAnimal animal = chooseAnimalToConsumption(map.getAnimalsAtPosition(position));
                animal.increaseEnergy(plant.getEnergy());
                terrain.removePlant(plant);
                //inform plant observer
            }
        }
    }

    private IAnimal chooseAnimalToConsumption(List<IAnimal> animalsAtPosition) {
        Random random = new Random();
        List<IAnimal> drawList;
        if (animalsAtPosition.size() == 1) {
            return animalsAtPosition.get(0);
        }

        sortTheAnimalsByEnergy(animalsAtPosition);
        drawList = getEnergyTiesList(animalsAtPosition);
        if (drawList.size() == 1) {
            return drawList.get(0);
        }

        sortTheAnimalsByAge(drawList);
        drawList = getAgeTiesList(drawList);
        if (drawList.size() == 1) {
            return drawList.get(0);
        }

        sortTheAnimalsByNumberOfChildren(drawList);
        drawList = getNumberOfChildrenTiesList(drawList);
        if (drawList.size() == 1) {
            return drawList.get(0);
        }
        return drawList.get(random.nextInt(0, drawList.size()));
    }

    //----------------------------------------------------------------------------------------------


    //sorting and comparing-------------------------------------------------------------------------
    private void sortTheAnimalsByEnergy(List<IAnimal> animalList) {
        animalList.sort(new Comparator<IAnimal>() {
            @Override
            public int compare(IAnimal o1, IAnimal o2) {
                return -(o1.getEnergy() - o2.getEnergy());
            }
        });
    }

    private void sortTheAnimalsByAge(List<IAnimal> animalList) {
        animalList.sort(new Comparator<IAnimal>() {
            @Override
            public int compare(IAnimal o1, IAnimal o2) {
                return -(o1.getAge() - o2.getAge());
            }
        });
    }

    private void sortTheAnimalsByNumberOfChildren(List<IAnimal> animalList) {
        animalList.sort(new Comparator<IAnimal>() {
            @Override
            public int compare(IAnimal o1, IAnimal o2) {
                return -(o1.getNumberOfChildren() - o2.getNumberOfChildren());
            }
        });
    }

    private List<IAnimal> getEnergyTiesList(List<IAnimal> animalList) {
        int numberOfTies = 1;
        while(numberOfTies < animalList.size() && animalList.get(numberOfTies).getEnergy() == animalList.get(numberOfTies - 1).getEnergy()) {
            numberOfTies++;
        }
        return animalList.subList(0, numberOfTies - 1);
    }

    private List<IAnimal> getAgeTiesList(List<IAnimal> animalList) {
        int numberOfTies = 1;
        while(numberOfTies < animalList.size() && animalList.get(numberOfTies).getAge() == animalList.get(numberOfTies - 1).getAge()) {
            numberOfTies++;
        }
        return animalList.subList(0, numberOfTies - 1);
    }

    private List<IAnimal> getNumberOfChildrenTiesList(List<IAnimal> animalList) {
        int numberOfTies = 1;
        while(numberOfTies < animalList.size() && animalList.get(numberOfTies).getNumberOfChildren() == animalList.get(numberOfTies - 1).getNumberOfChildren()) {
            numberOfTies++;
        }
        return animalList.subList(0, numberOfTies - 1);
    }
    //-----------------------------------------------------------------------------------------------


    //observers--------------------------------------------------------------------------------------
    public void addNewObserver(IPopulationChangeObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(IPopulationChangeObserver observer) {
        observers.remove(observer);
    }

    private void informObserversAboutNewAnimal(IAnimal animal) {
        for (IPopulationChangeObserver observer : observers) {
            observer.addedNewAnimal(animal);
        }
    }

    private void informObserversAboutRemoveAnimal(IAnimal animal) {
        for (IPopulationChangeObserver observer : observers) {
            observer.removedAnimal(animal);
        }
    }
    //-------------------------------------------------------------------------------------------------

}
