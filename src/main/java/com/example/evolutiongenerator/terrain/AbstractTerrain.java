package com.example.evolutiongenerator.terrain;

import com.example.evolutiongenerator.Plant;
import com.example.evolutiongenerator.interfaces.ITerrain;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTerrain implements ITerrain {
    private final List<Plant> plants = new ArrayList<>();

    @Override
    public void removePlant(Plant plant) {
        plants.remove(plant);
    }

}
