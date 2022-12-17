package com.example.evolutiongenerator.interfaces;

import com.example.evolutiongenerator.Animal;

public interface IReproduction {
    Animal newAnimal(Animal parentA, Animal parentB, int genomeLength, int quantityMutations, IMap map);
}
