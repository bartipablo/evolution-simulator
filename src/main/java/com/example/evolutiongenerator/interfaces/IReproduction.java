package com.example.evolutiongenerator.interfaces;


public interface IReproduction {
    IAnimal newAnimal(IAnimal parentA, IAnimal parentB, int genomeLength, int quantityMutations, int energyUsedToReproduction, IMap map);

}
