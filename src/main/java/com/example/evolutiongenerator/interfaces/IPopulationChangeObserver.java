package com.example.evolutiongenerator.interfaces;

import com.example.evolutiongenerator.Animal;

public interface IPopulationChangeObserver {

    void addedNewAnimal(Animal animal);

    void removedAnimal(Animal animal);

}
