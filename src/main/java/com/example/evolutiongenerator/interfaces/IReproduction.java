package com.example.evolutiongenerator.interfaces;


public interface IReproduction {
    IAnimal newAnimal(IAnimal parentA, IAnimal parentB, int genomeLength, int minimumQuantityMutations, int maximumQuantityMutations, int energyUsedToReproduction, IMap map);
}
