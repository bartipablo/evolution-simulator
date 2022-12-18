package com.example.evolutiongenerator;

import com.example.evolutiongenerator.interfaces.IAnimal;
import com.example.evolutiongenerator.interfaces.IMap;
import com.example.evolutiongenerator.interfaces.ITerrain;

import java.util.Arrays;
import java.util.List;

public class GeneralStatistics {

    ITerrain terrain;
    Population population;
    IMap map;
    private int populationSize;
    private int numberOfPlants;
    private double averageLifeLength;
    private double averageEnergyAmount;
    private int quantityOfFreeField;
    private int numberOfTheMostPopularGenotype;
    private int[] theMostPopularGenotype;

    GeneralStatistics(int populationSize, int numberOfPlants, IMap map, Population population, ITerrain terrain) {
        this.populationSize = populationSize;
        this.numberOfPlants = numberOfPlants;
        this.averageLifeLength = 0;
        this.averageEnergyAmount = 0;
        this.quantityOfFreeField = 0;
        this.theMostPopularGenotype = new int[]{};
        this.map = map;
        this.population = population;
        this.terrain = terrain;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public void completePopulationSize() {
        this.populationSize = population.getAliveAnimals().size();
    }

    public int getNumberOfPlants() {
        return numberOfPlants;
    }

    public void completeNumberOfPlants() {
        this.numberOfPlants = terrain.getPlants().size();
    }

    public double getAverageLifeLength() {
        return averageLifeLength;
    }

    public void completeAverageLifeLength() {
        List<IAnimal> extinctAnimals = population.getExtinctAnimals();
        if (extinctAnimals.size() > 0) {
            int totalLifeLength = 0;
            for (int i = 0; i < extinctAnimals.size(); i++) {
                totalLifeLength += extinctAnimals.get(0).getAge();
            }
            averageLifeLength = (double) (totalLifeLength / extinctAnimals.size());
        } else {
            averageLifeLength = 0;
        }
    }

    public double getAverageEnergyAmount() {
        return averageEnergyAmount;
    }

    public void completeAverageEnergyAmount() {
        List<IAnimal> aliveAnimals = population.getAliveAnimals();
        if (aliveAnimals.size() > 0) {
            int totalEnergy = 0;
            for (int i = 0; i < aliveAnimals.size(); i++) {
                totalEnergy += aliveAnimals.get(0).getEnergy();
            }
            averageEnergyAmount = (double) (totalEnergy / aliveAnimals.size());
        } else {
            averageEnergyAmount = 0;
        }
    }

    public int getQuantityOfFreeField() {
        return quantityOfFreeField;
    }

    public void completeQuantityOfFreeField() {
        quantityOfFreeField = map.getAnimalsPositions().length;
    }

    public int[] getTheMostPopularGenotype() {
        return theMostPopularGenotype;
    }

    public void completeTheMostPopularGenotype() {
        List<int[]> animalGenotypes = population.getAnimalGenomes();
        int numberOfTheMostPopularGenotypes = 0;
        int[] theMostPopularGenotype = {};
        for (int[] animalGenotypeA : animalGenotypes) {
            int quantity = 0;
            for (int[] animalGenotypeB : animalGenotypes) {
                if (Arrays.equals(animalGenotypeA, animalGenotypeB)) quantity += 1;
            }
            if (quantity > numberOfTheMostPopularGenotypes) {
                numberOfTheMostPopularGenotypes = quantity;
                theMostPopularGenotype = animalGenotypeA;
            }
        }
        this.theMostPopularGenotype = theMostPopularGenotype;
        this.numberOfTheMostPopularGenotype = numberOfTheMostPopularGenotypes;
    }

}
