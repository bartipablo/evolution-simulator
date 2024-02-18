package com.example.evolutiongenerator;

import com.example.evolutiongenerator.direction.Vector2D;

public class Plant {

    private final Vector2D position;
    private final int energy;

    //constructor-------------------------
    public Plant(Vector2D initialPosition, int plantEnergy) {
        this.position = initialPosition;
        this.energy = plantEnergy;
    }
    //------------------------------------

    public Vector2D getPosition() {
        return position;
    }

    public int getEnergy() {
        return energy;
    }

}
