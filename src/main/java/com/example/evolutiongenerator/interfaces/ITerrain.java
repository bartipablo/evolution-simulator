package com.example.evolutiongenerator.interfaces;

import com.example.evolutiongenerator.Plant;

import java.util.List;

public interface ITerrain {

    void removePlant(Plant plant);

    List<Plant> getPlants();

}
