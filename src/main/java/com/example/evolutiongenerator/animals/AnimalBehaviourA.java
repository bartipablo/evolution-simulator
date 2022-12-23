package com.example.evolutiongenerator.animals;

import com.example.evolutiongenerator.Gene;
import com.example.evolutiongenerator.direction.MapDirection;
import com.example.evolutiongenerator.direction.Vector2D;
import com.example.evolutiongenerator.interfaces.IMap;


//behaviour variant "full predestination"
public class AnimalBehaviourA extends AbstractAnimal {

    public AnimalBehaviourA(Vector2D initialPosition, MapDirection initialDirection, IMap map, Gene gene, int initialEnergy) {
        super(initialPosition, initialDirection, map, gene, initialEnergy);
    }

    public AnimalBehaviourA(Vector2D initialPosition, MapDirection initialDirection, IMap map, int genomeLength, int initialEnergy) {
        super(initialPosition, initialDirection, map, genomeLength, initialEnergy);
    }

    protected void updateActualGenomeIndex() {
        if (actualGenomeIndex == gene.getGenomeLength() - 1) {
            actualGenomeIndex = 0;
        } else {
            actualGenomeIndex += 1;
        }
    }

}
