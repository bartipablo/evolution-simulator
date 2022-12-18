package com.example.evolutiongenerator.animals;

import com.example.evolutiongenerator.Gene;
import com.example.evolutiongenerator.direction.MapDirection;
import com.example.evolutiongenerator.direction.Vector2D;
import com.example.evolutiongenerator.interfaces.IMap;


//behaviour variant "full predestination"
public class AnimalBehaviourA extends AbstractAnimal {

    public AnimalBehaviourA(Vector2D initialPosition, MapDirection initialDirection, IMap map, Gene gene) {
        super(initialPosition, initialDirection, map, gene);
    }

    public AnimalBehaviourA(Vector2D initialPosition, MapDirection initialDirection, IMap map, int genomeLength) {
        super(initialPosition, initialDirection, map, genomeLength);
    }

    private void updateActualGenomeIndex() {
        if (actualGenomeIndex == gene.getGenomeLength() - 1) {
            actualGenomeIndex = 0;
        } else {
            actualGenomeIndex += 1;
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
