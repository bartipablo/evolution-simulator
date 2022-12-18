package com.example.evolutiongenerator.animals;

import com.example.evolutiongenerator.Gene;
import com.example.evolutiongenerator.direction.MapDirection;
import com.example.evolutiongenerator.direction.Vector2D;
import com.example.evolutiongenerator.interfaces.IMap;

import java.util.Random;

//behaviour variant "a bit of craziness"
public class AnimalBehaviourB extends AbstractAnimal {

    public AnimalBehaviourB(Vector2D initialPosition, MapDirection initialDirection, IMap map, Gene gene) {
        super(initialPosition, initialDirection, map, gene);
    }

    public AnimalBehaviourB(Vector2D initialPosition, MapDirection initialDirection, IMap map, int genomeLength) {
        super(initialPosition, initialDirection, map, genomeLength);
    }

    private void updateActualGenomeIndex() {
        Random random = new Random();
        int randomInt = random.nextInt(0, 10);
        if (randomInt < 7) {
            if (actualGenomeIndex == gene.getGenomeLength() - 1) {
                actualGenomeIndex = 0;
            } else {
                actualGenomeIndex += 1;
            }
        } else {
            actualGenomeIndex = random.nextInt(0, gene.getGenomeLength());
        }
    }

    @Override
    public void move() {
        Vector2D oldPosition = new Vector2D(position.x, position.y);
        direction = direction.rotation(gene.getGenome()[actualGenomeIndex]);
        Vector2D newPosition = map.calculatePositionAfterMovement(position, direction);
        MapDirection newDirection = map.calculateDirectionAfterMovement(position, direction);
        position = newPosition;
        direction = newDirection;
        informObserversAboutChanges(oldPosition, newPosition);
        updateActualGenomeIndex();
    }

}
