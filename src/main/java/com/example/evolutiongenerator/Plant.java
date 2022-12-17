package com.example.evolutiongenerator;

import com.example.evolutiongenerator.direction.Vector2D;
import com.example.evolutiongenerator.interfaces.IMapElement;

public class Plant implements IMapElement {

    private Vector2D position;

    //constructor-------------------------
    Plant(Vector2D initialPosition) {
        this.position = initialPosition;
    }
    //------------------------------------

    public Vector2D getPosition() {
        return position;
    }

}
