package com.example.evolutiongenerator;

import com.example.evolutiongenerator.interfaces.IAnimal;
import com.example.evolutiongenerator.interfaces.IMap;
import com.example.evolutiongenerator.interfaces.IStatisticsObserver;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Statistics implements IStatisticsObserver {

    private int simulationDay;
    private int populationSize;
    private int plantsQuantity;
    private double averageLifeLength;
    private double averageEnergy;
    private int freeFieldQuantity;
    private int mostPopularGenotypeQuantity;
    private int[] theMostPopularGenotype;

    Statistics(int populationSize, int numberOfPlants) {
        this.populationSize = populationSize;
        this.plantsQuantity = numberOfPlants;
        this.averageLifeLength = 0;
        this.averageEnergy = 0;
        this.freeFieldQuantity = 0;
        this.mostPopularGenotypeQuantity = 0;
        this.theMostPopularGenotype = new int[]{};
        this.simulationDay = 0;
    }


    public int getPopulationSize() {
        return populationSize;
    }

    @Override
    public void setPopulationSize(int size) {
        this.populationSize = size;
    }

    public int getPlantsQuantity() {
        return plantsQuantity;
    }

    @Override
    public void setPlantsQuantity(int quantity) {
        this.plantsQuantity = quantity;
    }

    public double getAverageLifeLength() {
        return averageLifeLength;
    }

    @Override
    public void setAverageLifeLength(List<IAnimal> deadAnimals) {
        if (deadAnimals.size() > 0) {
            int totalLifeLength = 0;
            for (IAnimal extinctAnimal : deadAnimals) {
                totalLifeLength += extinctAnimal.getAge();
            }
            averageLifeLength = (double) (totalLifeLength / deadAnimals.size());
        } else {
            averageLifeLength = 0;
        }
    }

    public double getAverageEnergy() {
        return averageEnergy;
    }

    @Override
    public void setAverageEnergy(List<IAnimal> aliveAnimals) {
        if (aliveAnimals.size() > 0) {
            int totalEnergy = 0;
            for (int i = 0; i < aliveAnimals.size(); i++) {
                totalEnergy += aliveAnimals.get(i).getEnergy();
            }
            averageEnergy = (double) (totalEnergy / aliveAnimals.size());
        } else {
            averageEnergy = 0;
        }
    }

    public int getFreeFieldQuantity() {
        return freeFieldQuantity;
    }

    @Override
    public void setFreeFieldQuantity(IMap map) {
        freeFieldQuantity = (map.getMapHeight() * map.getMapWidth()) - map.getAnimalsPositions().length;
    }

    public int[] getTheMostPopularGenotype() {
        return theMostPopularGenotype;
    }

    @Override
    public void setTheMostPopularGenotype(List<int[]> animalGenotypes) {
        int quantityOfTheMostPopularGenotypes = 0;
        int[] theMostPopularGenotype = {};
        for (int[] animalGenotypeA : animalGenotypes) {
            int quantity = 0;
            for (int[] animalGenotypeB : animalGenotypes) {
                if (Arrays.equals(animalGenotypeA, animalGenotypeB)) quantity += 1;
            }
            if (quantity > quantityOfTheMostPopularGenotypes) {
                quantityOfTheMostPopularGenotypes = quantity;
                theMostPopularGenotype = animalGenotypeA;
            }
        }
        this.theMostPopularGenotype = theMostPopularGenotype;
        this.mostPopularGenotypeQuantity = quantityOfTheMostPopularGenotypes;
    }

    @Override
    public void setSimulationDay(int day) {
        this.simulationDay = day;
    }

    public int getSimulationDay() {
        return simulationDay;
    }


}
