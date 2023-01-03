package com.example.evolutiongenerator.animals;

import com.example.evolutiongenerator.Gene;
import com.example.evolutiongenerator.direction.MapDirection;
import com.example.evolutiongenerator.direction.Vector2D;
import com.example.evolutiongenerator.interfaces.IAnimal;
import com.example.evolutiongenerator.interfaces.IMap;
import com.example.evolutiongenerator.interfaces.IMapElementsObserver;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAnimal implements IAnimal {
    protected int actualGenomeIndex;
    protected int energy;
    private int age;
    private int childrenNumber;
    private int eatenPlantsNumber;
    private int deathDay;
    private final List<IMapElementsObserver> positionObservers = new ArrayList<>();
    protected Vector2D position;
    protected MapDirection direction;
    protected final IMap map;
    protected Gene gene;

    AbstractAnimal(Vector2D initialPosition, MapDirection initialDirection, IMap map, int genomeLength, int initialEnergy) {
        this.position = initialPosition;
        this.direction = initialDirection;
        this.map = map;
        this.gene = new Gene(genomeLength);
        this.actualGenomeIndex = 0;
        this.eatenPlantsNumber = 0;
        this.childrenNumber = 0;
        this.age = 0;
        this.energy = initialEnergy;
    }

    public AbstractAnimal(Vector2D initialPosition, MapDirection initialDirection, IMap map, Gene gene, int initialEnergy) {
        this.position = initialPosition;
        this.direction = initialDirection;
        this.map = map;
        this.gene = gene;
        this.actualGenomeIndex = 0;
        this.eatenPlantsNumber = 0;
        this.childrenNumber = 0;
        this.age = 0;
        this.energy = initialEnergy;
    }

    @Override
    public Vector2D getPosition() {
        return position;
    }

    @Override
    public MapDirection getDirection() {
        return direction;
    }

    @Override
    public int getEnergy() {
        return energy;
    }

    @Override
    public int getActualGenome() {
        return gene.getGenomes()[actualGenomeIndex];
    }

    @Override
    public int[] getGenomes() {
        return gene.getGenomes();
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public int getChildrenNumber() {
        return childrenNumber;
    }

    @Override
    public int getDeathDay() {
        return deathDay;
    }

    @Override
    public Gene getGene() {
        return gene;
    }

    @Override
    public int getEatenPlantsNumber() {
        return eatenPlantsNumber;
    }

    @Override
    public void changeEnergy(int amount) {
        energy += amount;
    }

    @Override
    public void changeChildrenNumber(int quantity) {
        childrenNumber += quantity;
    }

    @Override
    public void changeAge(int quantity) {
        age += quantity;
    }

    @Override
    public void changeEatenPlantsNumber(int quantity) {
        eatenPlantsNumber += quantity;
    }

    @Override
    public void setDeathDay(int deathDay) {
        this.deathDay = deathDay;
    }

    @Override
    public void addPositionObserver(IMapElementsObserver observer) {
        positionObservers.add(observer);
    }

    @Override
    public void removePositionObserver(IMapElementsObserver observer) {
        positionObservers.remove(observer);
    }

    protected void informObserversAboutPositionChanges(Vector2D oldPosition, Vector2D newPosition) {
        for (IMapElementsObserver observer : positionObservers) {
            observer.changePositionOnMap(this, oldPosition, newPosition);
        }
    }

    @Override
    public void move() {
        Vector2D oldPosition = new Vector2D(position.x, position.y);
        direction = this.direction.rotation(gene.getGenomes()[actualGenomeIndex]);
        Vector2D positionAfterMovement = position.add(direction.toUnitVector());
        Vector2D realPosition = map.calculatePositionAfterMovement(positionAfterMovement);
        MapDirection realDirection = map.calculateDirectionAfterMovement(positionAfterMovement, direction);
        informObserversAboutPositionChanges(oldPosition, realPosition);
        this.position = realPosition;
        this.direction = realDirection;
        updateActualGenomeIndex();
    }

    protected abstract void updateActualGenomeIndex();

}
