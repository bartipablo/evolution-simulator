package com.example.evolutiongenerator.reproduction;

import com.example.evolutiongenerator.Animal;
import com.example.evolutiongenerator.Gene;
import com.example.evolutiongenerator.direction.MapDirection;
import com.example.evolutiongenerator.direction.Vector2D;
import com.example.evolutiongenerator.interfaces.IMap;

import java.util.Random;


//variant implementation "nieco szaleństwa" :)
public class ReproductionB extends AbstractReproduction {

    private void mutation() {
        Random random = new Random();
        int[] mutationValue = {-1, 1};
        int[] indexToMutation = new int[genomeLength];
        for (int i = 0; i < genomeLength; i++) {
            indexToMutation[i] = i;
        }
        shuffleArray(indexToMutation);
        for (int i = 0; i < quantityMutations; i++) {
            genome[indexToMutation[i]] += mutationValue[random.nextInt(0, 2)];
            if (genome[indexToMutation[i]] == 8) {
                genome[indexToMutation[i]] = 0;
            } else if (genome[indexToMutation[i]] == -1) {
                 genome[indexToMutation[i]] = 7;
            }
        }
    }

    @Override
    public Animal newAnimal(Animal parentA, Animal parentB, int genomeLength, int quantityMutations, IMap map) {
        initialVariable(parentA, parentB, genomeLength, quantityMutations);
        createNewGenome();
        calculateEnergy();
        mutation();
        Gene gene = new Gene(genome);
        Vector2D initialPosition = new Vector2D(parentA.getPosition().x, parentA.getPosition().y);
        return new Animal(initialPosition, MapDirection.generateRandomDirection(), map, gene);
    }
}
