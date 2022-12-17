package com.example.evolutiongenerator.direction;

import com.example.evolutiongenerator.Vector2D;

import java.util.Random;

public enum MapDirection {
    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST,
    NORTH_WEST;


    public Vector2D toUnitVector() {
        return switch(this) {
            case NORTH      -> new Vector2D(0, 1);
            case NORTH_EAST -> new Vector2D(1, 1);
            case EAST       -> new Vector2D(1, 0);
            case SOUTH_EAST -> new Vector2D(1, -1);
            case SOUTH      -> new Vector2D(0, -1);
            case SOUTH_WEST -> new Vector2D(-1, -1);
            case WEST       -> new Vector2D(-1, 0);
            case NORTH_WEST -> new Vector2D(-1, 1);
        };
    }

    public MapDirection opposite() {
        return switch(this) {
            case NORTH      -> SOUTH;
            case NORTH_EAST -> SOUTH_WEST;
            case EAST       -> WEST;
            case SOUTH_EAST -> NORTH_WEST;
            case SOUTH      -> NORTH;
            case SOUTH_WEST -> NORTH_EAST;
            case WEST       -> EAST;
            case NORTH_WEST -> SOUTH_EAST;
        };
    }

    public static MapDirection generateRandomDirection() {
        Random random = new Random();
        int choosing = random.nextInt(0, 7);
        return switch (choosing) {
            case 0 -> NORTH;
            case 1 -> NORTH_EAST;
            case 2 -> EAST;
            case 3 -> SOUTH_EAST;
            case 4 -> SOUTH;
            case 5 -> SOUTH_WEST;
            case 6 -> WEST;
            case 7 -> NORTH_WEST;
            default -> throw new IllegalStateException("Unexpected value: " + choosing);
        };
    }


    public MapDirection nextDirection() {
        return switch (this) {
            case NORTH      -> NORTH_EAST;
            case NORTH_EAST -> EAST;
            case EAST       -> SOUTH_EAST;
            case SOUTH_EAST -> SOUTH;
            case SOUTH      -> SOUTH_WEST;
            case SOUTH_WEST -> WEST;
            case WEST       -> NORTH_WEST;
            case NORTH_WEST -> NORTH;
        };
    }

    public MapDirection rotation(int n) {
        MapDirection newDirection = this;
        for (int i = 0; i < n; i++) {
            newDirection = newDirection.nextDirection();
        }
        return newDirection;
    }

}
