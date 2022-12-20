package com.example.evolutiongenerator.reproduction;

import com.example.evolutiongenerator.animals.AnimalBehaviourA;
import com.example.evolutiongenerator.animals.AnimalBehaviourB;
import com.example.evolutiongenerator.interfaces.IAnimal;
import com.example.evolutiongenerator.Gene;
import com.example.evolutiongenerator.direction.MapDirection;
import com.example.evolutiongenerator.direction.Vector2D;
import com.example.evolutiongenerator.interfaces.IMap;

import java.util.Random;


//variant implementation "full randomness"
public class ReproductionA extends AbstractReproduction {

    private void mutation() {
        Random random = new Random();
        int[] indexToMutation = new int[genomeLength];
        for (int i = 0; i < genomeLength; i++) {
            indexToMutation[i] = i;
        }
        shuffleArray(indexToMutation);
        int quantityMutations = random.nextInt(minimumQuantityMutations, maximumQuantityMutations + 1);
        for (int i = 0; i < quantityMutations; i++) {
            genome[indexToMutation[i]] = random.nextInt(0, 8);
        }
    }

    @Override
    public IAnimal newAnimal(IAnimal parentA, IAnimal parentB, int genomeLength, int minimumQuantityMutations, int maximumQuantityMutations,
                             int energyUsedToReproduction, IMap map) {
        parentA.increaseNumberOfChildren(1);
        parentB.increaseNumberOfChildren(1);
        initialVariable(parentA, parentB, genomeLength, minimumQuantityMutations, maximumQuantityMutations, energyUsedToReproduction);
        createNewGenome();
        calculateEnergy();
        mutation();
        Gene gene = new Gene(genome);
        Vector2D initialPosition = new Vector2D(parentA.getPosition().x, parentA.getPosition().y);
        if (parentA instanceof AnimalBehaviourA) {
            return new AnimalBehaviourA(initialPosition, MapDirection.generateRandomDirection(), map, gene, energy);
        } else if (parentA instanceof AnimalBehaviourB) {
            return new AnimalBehaviourB(initialPosition, MapDirection.generateRandomDirection(), map, gene, energy);
        }
        return null;
    }

}
