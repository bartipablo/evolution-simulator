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
    private final List<IMapElementsObserver> populationObservers = new ArrayList<>();
    private final List<IStatisticsObserver> statisticsObservers = new ArrayList<>();
    private final List<IAnimal> liveAnimals = new ArrayList<>();
    private final List<IAnimal> deadAnimals = new ArrayList<>();
    private final IReproduction reproductionVariant;
    private final ITerrain terrain;
    private final int minimumEnergyToReproduction;
    private final int energyUsedToReproduction;
    private final int genomeLength;
    private final int minimumNumberOfMutations;
    private final int maximumNumberOfMutations;
    private final int dailyEnergyConsumption;
    private final int initialEnergy;
    private int totalLifeExpectancy;
    private final BehaviourVariant behaviourVariant;
    private final int initialPopulationSize;
    private final SimulationEngine world;
    private final boolean isDeletedExcessAnimals;

    //constructors-------------------------------------------------------------------------
    public Population(int populationSize, int minimumEnergyToReproduction, int genomeLength, int energyUsedToReproduction,
               int minimumNumberOfMutations, int maximumNumberOfMutations, IMap map, IReproduction reproductionVariant,
               ITerrain terrain, BehaviourVariant behaviourVariant, int dailyEnergyConsumption, int initialEnergy, SimulationEngine world,
               Boolean isDeletedExcessAnimals) {
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
        this.totalLifeExpectancy = 0;
        this.initialPopulationSize = populationSize;
        this.world = world;
        this.isDeletedExcessAnimals = isDeletedExcessAnimals;
    }

    public void generateNewPopulation() {
        Random random = new Random();
        for (int i = 0; i < initialPopulationSize; i++) {
            Vector2D initialPosition = Vector2D.generateRandomVector2D(0, map.getMapWidth(), 0, map.getMapHeight());
            MapDirection initialDirection = MapDirection.generateRandomDirection();
            IAnimal animal;
            if (behaviourVariant == BehaviourVariant.FULL_PREDESTINATION) {
                animal = new AnimalBehaviourA(initialPosition, initialDirection, map, genomeLength, initialEnergy);
            } else {
                animal = new AnimalBehaviourB(initialPosition, initialDirection, map, genomeLength, initialEnergy);
            }

            animal.addPositionObserver((IMapElementsObserver) map);
            addNewAnimal(animal);
            informObserversAboutNewAnimal(animal);
        }
    }

    //-------------------------------------------------------------------------------------------

    private void addNewAnimal(IAnimal animal) {
        liveAnimals.add(animal);
    }

    private void removeAnimal(IAnimal animal) {
        liveAnimals.remove(animal);
        deadAnimals.add(animal);
    }

    public List<int[]> getAnimalGenomes() {
        List<int[]> animalGenomes = new ArrayList<>();
        for (IAnimal animal : liveAnimals) {
            animalGenomes.add(animal.getGene().getGenomes());
        }
        return animalGenomes;
    }

    public List<IAnimal> getLiveAnimals() {
        return liveAnimals;
    }

    public List<IAnimal> getDeadAnimals() {
        return deadAnimals;
    }

    public void completeStatistics() {
        for (IStatisticsObserver observer : statisticsObservers) {
            observer.setAverageEnergy(liveAnimals);
            observer.setPopulationSize(liveAnimals.size());
            observer.setFreeFieldQuantity(map);
            observer.setTheMostPopularGenotype(getAnimalGenomes());
            observer.setAverageLifeLength(deadAnimals);
        }
    }
    //daily energy consumption---------------------------------------------------------------------
    public void dailyEnergyConsumption() {
        for (IAnimal animal : liveAnimals) {
            animal.changeEnergy(-dailyEnergyConsumption);
        }
    }
    //--------------------------------------------------------------------------------------------

    //moving--------------------------------------------------------------------------------------
    public void dailyMoving() {
        for (IAnimal animal : liveAnimals) {
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
        if (animalsAtPosition.get(0).getEnergy() > animalsAtPosition.get(1).getEnergy()) {
            return animalsAtPosition.subList(0, 1);
        }

        drawsList = getEnergyDrawsList(animalsAtPosition);
        sortTheAnimalsByAge(drawsList);
        if (drawsList.get(0).getAge() > drawsList.get(1).getAge()) {
            return drawsList.subList(0, 1);
        }

        drawsList = getAgeDrawsList(drawsList);
        sortTheAnimalsByNumberOfChildren(drawsList);
        if (drawsList.get(0).getQuantityOfChildren() > drawsList.get(1).getQuantityOfChildren()) {
            return drawsList.subList(0, 1);
        }

        drawsList = getAgeDrawsList(drawsList);
        Collections.shuffle(drawsList);
        return drawsList.subList(0, 1);
    }

    private void createNewAnimal(IAnimal animalA, IAnimal animalB) {
        IAnimal newAnimal = reproductionVariant.newAnimal(animalA, animalB, genomeLength, minimumNumberOfMutations, maximumNumberOfMutations, energyUsedToReproduction, map);
        newAnimal.addPositionObserver((IMapElementsObserver) map);
        addNewAnimal(newAnimal);
        informObserversAboutNewAnimal(newAnimal);
    }

    //------------------------------------------------------------------------------------------------

    //vanishing---------------------------------------------------------------------------------------
    public void vanishing() {
        // Make a copy of the liveAnimals list
        List<IAnimal> liveAnimalsCopy = new ArrayList<>(liveAnimals);

        // Iterate over the copy of the list
        for (IAnimal animal : liveAnimalsCopy) {
            if (animal.getEnergy() <= 0) {
                totalLifeExpectancy += animal.getAge();
                removeAnimal(animal);
                animal.setDeathDay(world.getSimulationDay());
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
                animal.changeEnergy(plant.getEnergy());
                animal.changeEatenPlantsNumber(1);
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
    public void ageIncrease() {
        for (IAnimal animal : liveAnimals) {
            animal.changeAge(1);
        }
    }

    //remove excess animals-------------------------------------------------------------------------
    public void removeExcessAnimals() {
        int maxAnimalsNumber = (map.getMapHeight() * map.getMapWidth()) * 4;
        if (liveAnimals.size() > maxAnimalsNumber && isDeletedExcessAnimals) removeExcessLiveAnimals(liveAnimals.size() - maxAnimalsNumber);
        if (deadAnimals.size() > maxAnimalsNumber && isDeletedExcessAnimals) removeExcessDeadAnimals(liveAnimals.size() - maxAnimalsNumber);

    }

    private void removeExcessLiveAnimals(int quantity) {
        Random random = new Random();
        for (int i = 0; i < quantity; i++) {
            int index = random.nextInt(0, liveAnimals.size());
            IAnimal animal = liveAnimals.get(index);
            removeAnimal(animal);
        }
    }

    private void removeExcessDeadAnimals(int quantity) {
        Random random = new Random();
        for (int i = 0; i < quantity; i++) {
            int index = random.nextInt(0, liveAnimals.size());
            IAnimal animal = deadAnimals.get(index);
            deadAnimals.remove(animal);
        }
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
                return -(o1.getQuantityOfChildren() - o2.getQuantityOfChildren());
            }
        });
    }

    private List<IAnimal> getEnergyDrawsList(List<IAnimal> animalList) {
        int numberOfDraws = 1;
        while(numberOfDraws < animalList.size() && animalList.get(numberOfDraws).getEnergy() == animalList.get(numberOfDraws - 1).getEnergy()) {
            numberOfDraws++;
        }
        return animalList.subList(0, numberOfDraws);
    }

    private List<IAnimal> getAgeDrawsList(List<IAnimal> animalList) {
        int numberOfDraws = 1;
        while(numberOfDraws < animalList.size() && animalList.get(numberOfDraws).getAge() == animalList.get(numberOfDraws - 1).getAge()) {
            numberOfDraws++;
        }
        return animalList.subList(0, numberOfDraws);
    }

    private List<IAnimal> getNumberOfChildrenDrawsList(List<IAnimal> animalList) {
        int numberOfDraws = 1;
        while(numberOfDraws < animalList.size() && animalList.get(numberOfDraws).getQuantityOfChildren() == animalList.get(numberOfDraws - 1).getQuantityOfChildren()) {
            numberOfDraws++;
        }
        return animalList.subList(0, numberOfDraws);
    }
    //-----------------------------------------------------------------------------------------------


    //observers--------------------------------------------------------------------------------------
    public void addNewPopulationObserver(IMapElementsObserver observer) {
        populationObservers.add(observer);
    }

    public void addNewStatisticsObserver(IStatisticsObserver observer) {
        statisticsObservers.add(observer);
    }


    private void informObserversAboutNewAnimal(IAnimal animal) {
        for (IMapElementsObserver observer : populationObservers) {
            observer.addAnimalToMap(animal);
        }
    }

    private void informObserversAboutDeathAnimal(IAnimal animal) {
        for (IMapElementsObserver observer : populationObservers) {
            observer.removeAnimalFromMap(animal);
        }
    }
    //-------------------------------------------------------------------------------------------------

}
