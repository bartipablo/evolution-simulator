package com.example.evolutiongenerator.reproduction;

import com.example.evolutiongenerator.Animal;
import com.example.evolutiongenerator.Gene;
import com.example.evolutiongenerator.direction.MapDirection;
import com.example.evolutiongenerator.direction.Vector2D;
import com.example.evolutiongenerator.interfaces.IMap;

import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


//variant implementation "full randomness"
public class ReproductionA extends AbstractReproduction {

    ReproductionA(Animal parentA, Animal parentB, int genomeLength, int quantityMutations, IMap map) {
        super(parentA, parentB, genomeLength, quantityMutations, map);
    }

    private void mutation() {
        Random random = new Random();
        int[] indexToMutation = new int[genomeLength];
        for (int i = 0; i < genomeLength; i++) {
            indexToMutation[i] = i;
        }
        shuffleArray(indexToMutation);
        for (int i = 0; i < quantityMutations; i++) {
            genome[indexToMutation[i]] = random.nextInt(0, 8);
        }
    }


    @Override
    public Animal newAnimal() {
        createNewGenome();
        calculateEnergy();
        mutation();
        Gene gene = new Gene(genome);
        Vector2D initialPosition = new Vector2D(parentA.getPosition().x, parentA.getPosition().y);
        return new Animal(initialPosition, MapDirection.generateRandomDirection(), map, gene);
    }
}
