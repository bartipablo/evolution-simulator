package com.example.evolutiongenerator.interfaces;


public interface IPopulationChangeObserver {

    void addedNewAnimal(IAnimal animal);

    void removedAnimal(IAnimal animal);

}
