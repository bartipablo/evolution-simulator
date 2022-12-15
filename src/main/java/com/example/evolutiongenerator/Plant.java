package com.example.evolutiongenerator;

public class Plant {

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
