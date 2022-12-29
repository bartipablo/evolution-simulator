package com.example.evolutiongenerator.direction;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapDirectionTest {

    @Test
    void directionTest() {
        MapDirection dir = MapDirection.NORTH;
        Vector2D vec = dir.nextDirection().opposite().rotation(3).toUnitVector();
        assertEquals(vec, new Vector2D(0,1));
    }
}