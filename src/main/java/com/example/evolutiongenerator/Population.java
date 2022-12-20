package com.example.evolutiongenerator;

import com.example.evolutiongenerator.animals.AnimalBehaviourA;
import com.example.evolutiongenerator.animals.AnimalBehaviourB;
import com.example.evolutiongenerator.interfaces.*;
import com.example.evolutiongenerator.direction.MapDirection;
import com.example.evolutiongenerator.direction.Vector2D;
import com.example.evolutiongenerator.variants.BehaviourVariant;

import java.util.*;

public class Population {

    private final IMap map;
    private final List<IPopulationChangeObserver> populationObservers = new ArrayList<>();
    private final List<IStatisticsObserver> statisticsObservers = new ArrayList<>();
    private final List<IAnimal> aliveAnimals = new ArrayList<>();
    private final List<IAnimal> extinctAnimals = new ArrayList<>();
    private final IReproduction reproductionVariant;
    private final ITerrain terrain;
    private final int minimumEnergyToReproduction;
    private final int energyUsedToReproduction;
    private final int genomeLength;
    private final int minimumNumberOfMutations;
    private final int maximumNumberOfMutations;
    private final int dailyEnergyConsumption;
    private final int initialEnergy;
    private final BehaviourVariant behaviourVariant;


    //constructors-------------------------------------------------------------------------
    public Population(int populationSize, int minimumEnergyToReproduction, int genomeLength, int energyUsedToReproduction,
               int minimumNumberOfMutations, int maximumNumberOfMutations, IMap map, IReproduction reproductionVariant,
               ITerrain terrain, BehaviourVariant behaviourVariant, int dailyEnergyConsumption, int initialEnergy) {
        this.reproductionVariant = reproductionVariant;
        this.minimumEnergyToReproduction = minimumEnergyToReproduction;
        this.map = map;
        this.genomeLength = genomeLength;
        this.minimumNumberOfMutations = minimumNumberOfMutations;
        this.maximumNumberOfMutations = maximumNumberOfMutations;
        this.energyUsedToReproduction = energyUsedToReproduction;
        this.terrain = terrain;
        this.behaviourVariant = behaviourVariant;
        this.dailyEnergyConsumption = dailyEnergyConsumption;
        this.initialEnergy = initialEnergy;
        generateNewPopulation(populationSize);
    }

    private void generateNewPopulation(int populationSize) {
        Random random = new Random();
        for (int i = 0; i < populationSize; i++) {
            Vector2D initialPosition = Vector2D.generateRandomVector2D(0, map.getMapWidth(), 0, map.getMapHeight());
            MapDirection initialDirection = MapDirection.generateRandomDirection();
            IAnimal animal;
            if (behaviourVariant == BehaviourVariant.FULL_PREDESTINATION) {
                animal = new AnimalBehaviourA(initialPosition, initialDirection, map, genomeLength, initialEnergy);
            } else {
                animal = new AnimalBehaviourB(initialPosition, initialDirection, map, genomeLength, initialEnergy);
            }
            addAnimal(animal);
            informObserversAboutNewAnimal(animal);
        }
    }

    //-------------------------------------------------------------------------------------------

    private void addAnimal(IAnimal animal) {
        aliveAnimals.add(animal);
    }

    private void removeAnimal(IAnimal animal) {
        aliveAnimals.remove(animal);
    }

    public List<int[]> getAnimalGenomes() {
        List<int[]> animalGenomes = new ArrayList<>();
        for (IAnimal animal : aliveAnimals) {
            animalGenomes.add(animal.getGene().getGenome());
        }
        return animalGenomes;
    }

    public List<IAnimal> getAliveAnimals() {
        return aliveAnimals;
    }

    public List<IAnimal> getExtinctAnimals() {
        return extinctAnimals;
    }

    public void completeStatistics() {
        for (IStatisticsObserver observer : statisticsObservers) {
            observer.setAverageEnergy(aliveAnimals);
            observer.setPopulationSize(aliveAnimals.size());
            observer.setFreeFieldQuantity(map);
            observer.setTheMostPopularGenotype(getAnimalGenomes());
            observer.setAverageLifeLength(extinctAnimals);
        }
    }
    //daily energy consumption---------------------------------------------------------------------
    public void dailyEnergyConsumption() {
        for (IAnimal animal : aliveAnimals) {
            animal.increaseEnergy(dailyEnergyConsumption);
        }
    }
    //--------------------------------------------------------------------------------------------

    //moving--------------------------------------------------------------------------------------
    public void dailyMoving() {
        for (IAnimal animal : aliveAnimals) {
            animal.move();
        }
    }
    //--------------------------------------------------------------------------------------------

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

        drawsList = getEnergyDrawsList(animalsAtPosition);
        sortTheAnimalsByAge(drawsList);
        if (drawsList.get(1).getAge() > drawsList.get(2).getAge()) {
            return drawsList.subList(0, 1);
        }

        drawsList = getAgeDrawsList(drawsList);
        sortTheAnimalsByNumberOfChildren(drawsList);
        if (drawsList.get(1).getNumberOfChildren() > drawsList.get(2).getNumberOfChildren()) {
            return drawsList.subList(0, 1);
        }

        drawsList = getAgeDrawsList(drawsList);
        Collections.shuffle(drawsList);
        return drawsList.subList(0, 1);
    }

    private void createNewAnimal(IAnimal animalA, IAnimal animalB) {
        IAnimal newAnimal = reproductionVariant.newAnimal(animalA, animalB, genomeLength, minimumNumberOfMutations, energyUsedToReproduction, map);
        addAnimal(newAnimal);
        informObserversAboutNewAnimal(newAnimal);
    }

    //------------------------------------------------------------------------------------------------

    //vanishing---------------------------------------------------------------------------------------
    public void vanishing() {
        for (IAnimal animal : aliveAnimals) {
            if (animal.getEnergy() <= 0) {
                extinctAnimals.add(animal);
                removeAnimal(animal);
                informObserversAboutDeathAnimal(animal);
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
                animal.increaseNumberOfEatenPlants(1);
                terrain.removePlant(plant);
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
        drawList = getEnergyDrawsList(animalsAtPosition);
        if (drawList.size() == 1) {
            return drawList.get(0);
        }

        sortTheAnimalsByAge(drawList);
        drawList = getAgeDrawsList(drawList);
        if (drawList.size() == 1) {
            return drawList.get(0);
        }

        sortTheAnimalsByNumberOfChildren(drawList);
        drawList = getNumberOfChildrenDrawsList(drawList);
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

    private List<IAnimal> getEnergyDrawsList(List<IAnimal> animalList) {
        int numberOfDraws = 1;
        while(numberOfDraws < animalList.size() && animalList.get(numberOfDraws).getEnergy() == animalList.get(numberOfDraws - 1).getEnergy()) {
            numberOfDraws++;
        }
        return animalList.subList(0, numberOfDraws - 1);
    }

    private List<IAnimal> getAgeDrawsList(List<IAnimal> animalList) {
        int numberOfDraws = 1;
        while(numberOfDraws < animalList.size() && animalList.get(numberOfDraws).getAge() == animalList.get(numberOfDraws - 1).getAge()) {
            numberOfDraws++;
        }
        return animalList.subList(0, numberOfDraws - 1);
    }

    private List<IAnimal> getNumberOfChildrenDrawsList(List<IAnimal> animalList) {
        int numberOfDraws = 1;
        while(numberOfDraws < animalList.size() && animalList.get(numberOfDraws).getNumberOfChildren() == animalList.get(numberOfDraws - 1).getNumberOfChildren()) {
            numberOfDraws++;
        }
        return animalList.subList(0, numberOfDraws - 1);
    }
    //-----------------------------------------------------------------------------------------------


    //observers--------------------------------------------------------------------------------------
    public void addNewPopulationObserver(IPopulationChangeObserver observer) {
        populationObservers.add(observer);
    }

    public void addNewStatisticsObserver(IStatisticsObserver observer) {
        statisticsObservers.add(observer);
    }


    private void informObserversAboutNewAnimal(IAnimal animal) {
        for (IPopulationChangeObserver observer : populationObservers) {
            observer.addedNewAnimal(animal);
        }
    }

    private void informObserversAboutDeathAnimal(IAnimal animal) {
        for (IPopulationChangeObserver observer : populationObservers) {
            observer.removedAnimal(animal);
        }
    }
    //-------------------------------------------------------------------------------------------------

}
