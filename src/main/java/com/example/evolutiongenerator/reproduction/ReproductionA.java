package com.example.evolutiongenerator.reproduction;

import com.example.evolutiongenerator.Static;
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

    protected void mutation() {
        Random random = new Random();
        int[] indexToMutation = new int[genomeLength];
        for (int i = 0; i < genomeLength; i++) {
            indexToMutation[i] = i;
        }
        Static.shuffleIntArray(indexToMutation);
        int quantityMutations = random.nextInt(minimumQuantityMutations, maximumQuantityMutations + 1);
        for (int i = 0; i < quantityMutations; i++) {
            genome[indexToMutation[i]] = random.nextInt(0, 8);
        }
    }


}
