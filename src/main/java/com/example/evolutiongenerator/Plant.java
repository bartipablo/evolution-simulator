package com.example.evolutiongenerator;

import com.example.evolutiongenerator.direction.Vector2D;
import com.example.evolutiongenerator.interfaces.IMapElement;

public class Plant implements IMapElement {

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

    @Override
    public String getImagePath() {
        String str = "resources\\com\\example\\evolutiongenerator\\icon.png";
        return str;
    }
}
