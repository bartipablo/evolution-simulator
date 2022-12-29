package com.example.evolutiongenerator.direction;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector2DTest {

    @Test
    void generateRandomVector2D() {
    }

    @Test
    void add() {
        Vector2D vec = new Vector2D(0,1);
        assertEquals(new Vector2D(1,2),vec.add(new Vector2D(1,1)));
    }

    @Test
    void subtract() {
        Vector2D vec = new Vector2D(0,1);
        assertEquals(new Vector2D(1,2),vec.subtract(new Vector2D(-1,-1)));
    }
}