package com.example.evolutiongenerator.reproduction;

import com.example.evolutiongenerator.Gene;
import com.example.evolutiongenerator.animals.AnimalBehaviourA;
import com.example.evolutiongenerator.animals.AnimalBehaviourB;
import com.example.evolutiongenerator.direction.MapDirection;
import com.example.evolutiongenerator.direction.Vector2D;
import com.example.evolutiongenerator.interfaces.IAnimal;
import com.example.evolutiongenerator.interfaces.IMap;
import com.example.evolutiongenerator.interfaces.IReproduction;

import java.util.Random;

public abstract class AbstractReproduction implements IReproduction {

    protected IAnimal parentA;
    protected IAnimal parentB;
    protected int[] genome;
    protected int genomeLength;
    protected int minimumQuantityMutations;
    protected int maximumQuantityMutations;
    protected int energyUsedToReproduction;
    protected int energy;

    protected void initialVariable(IAnimal parentA, IAnimal parentB, int genomeLength, int minimumQuantityMutations,
                                   int maximumQuantityMutations, int energyUsedToReproduction) {
        this.parentA = parentA;
        this.parentB = parentB;
        this.genome = new int[genomeLength];
        this.genomeLength = genomeLength;
        this.minimumQuantityMutations = minimumQuantityMutations;
        this.maximumQuantityMutations = maximumQuantityMutations;
        this.energyUsedToReproduction = energyUsedToReproduction;
    }

    protected void createNewGenome() {
        int quantityOfGenomeFromTheParentA;
        int quantityOfGenomeFromTheParentB;
        int totalEnergy = parentA.getEnergy() + parentB.getEnergy();
        int[] parentAGenome = parentA.getGene().getGenomes();
        int[] parentBGenome = parentB.getGene().getGenomes();
        if (parentA.getEnergy() > parentB.getEnergy()) {
            quantityOfGenomeFromTheParentA = (int) Math.ceil((1.0 * parentA.getEnergy() / totalEnergy) * genomeLength);
            quantityOfGenomeFromTheParentB = (int) Math.floor((1.0 * parentB.getEnergy() / totalEnergy) * genomeLength);
        } else {
            quantityOfGenomeFromTheParentA = (int) Math.floor((1.0 * parentA.getEnergy() / totalEnergy) * genomeLength);
            quantityOfGenomeFromTheParentB = (int) Math.ceil((1.0 * parentB.getEnergy() / totalEnergy) * genomeLength);
        }
        Random random = new Random();
        int sideToCopy = random.nextInt(1, 3);
        if (sideToCopy == 1) {
            System.arraycopy(parentAGenome, 0, genome, 0, quantityOfGenomeFromTheParentA);
            System.arraycopy(parentBGenome, quantityOfGenomeFromTheParentA, genome, quantityOfGenomeFromTheParentA, quantityOfGenomeFromTheParentB);
        } else {
            System.arraycopy(parentBGenome, 0, genome, 0, quantityOfGenomeFromTheParentB);
            System.arraycopy(parentAGenome, quantityOfGenomeFromTheParentB, genome, quantityOfGenomeFromTheParentB, quantityOfGenomeFromTheParentA);
        }
    }

    protected void calculateEnergy() {
        int quantityOfEnergyFromTheParentA;
        int quantityOfEnergyFromTheParentB;
        int totalEnergy = parentA.getEnergy() + parentB.getEnergy();
        if (parentA.getEnergy() > parentB.getEnergy()) {
            quantityOfEnergyFromTheParentA = (int) Math.ceil((1.0 * parentA.getEnergy() / totalEnergy) * energyUsedToReproduction);
            quantityOfEnergyFromTheParentB = (int) Math.floor((1.0 * parentB.getEnergy() / totalEnergy) * energyUsedToReproduction);
        } else {
            quantityOfEnergyFromTheParentA = (int) Math.floor((1.0 * parentA.getEnergy() / totalEnergy) * energyUsedToReproduction);
            quantityOfEnergyFromTheParentB = (int) Math.ceil((1.0 * parentB.getEnergy() / totalEnergy) * energyUsedToReproduction);
        }
        parentA.increaseEnergy(-quantityOfEnergyFromTheParentA);
        parentB.increaseEnergy(-quantityOfEnergyFromTheParentB);
        energy = quantityOfEnergyFromTheParentA + quantityOfEnergyFromTheParentB;
    }

    @Override
    public IAnimal newAnimal(IAnimal parentA, IAnimal parentB, int genomeLength, int minimumQuantityMutations, int maximumQuantityMutations,
                             int energyUsedToReproduction, IMap map) {
        parentA.changeChildrenNumber(1);
        parentB.changeChildrenNumber(1);
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


    protected abstract void mutation();

}
