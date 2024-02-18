package com.example.evolutiongenerator.direction;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Vector2DTest {
    @Test
    public void testConstructor() {
        Vector2D v1 = new Vector2D(1, 2);
        assertEquals(1, v1.x);
        assertEquals(2, v1.y);
    }

    @Test
    public void testGenerateRandomVector2D() {
        for (int i = 0; i < 100; i++) {
            Vector2D v1 = Vector2D.generateRandomVector2D(-10, 10, -10, 10);
            assertTrue(v1.x >= -10 && v1.x < 10);
            assertTrue(v1.y >= -10 && v1.y < 10);
        }
    }

    @Test
    public void testToString() {
        Vector2D v1 = new Vector2D(1, 2);
        assertEquals("(1, 2)", v1.toString());
    }

    @Test
    public void testAdd() {
        Vector2D v1 = new Vector2D(1, 2);
        Vector2D v2 = new Vector2D(3, 4);
        Vector2D v3 = v1.add(v2);
        assertEquals(4, v3.x);
        assertEquals(6, v3.y);
    }

    @Test
    public void testSubtract() {
        Vector2D v1 = new Vector2D(1, 2);
        Vector2D v2 = new Vector2D(3, 4);
        Vector2D v3 = v1.subtract(v2);
        assertEquals(-2, v3.x);
        assertEquals(-2, v3.y);
    }

    @Test
    public void testUpperRight() {
        Vector2D v1 = new Vector2D(1, 2);
        Vector2D v2 = new Vector2D(3, 4);
        Vector2D v3 = v1.upperRight(v2);
        assertEquals(3, v3.x);
        assertEquals(4, v3.y);
    }

    @Test
    public void testLowerLeft() {
        Vector2D v1 = new Vector2D(1, 2);
        Vector2D v2 = new Vector2D(3, 4);
        Vector2D v3 = v1.lowerLeft(v2);
        assertEquals(1, v3.x);
        assertEquals(2, v3.y);
    }

    @Test
    public void testOpposite() {
        Vector2D v1 = new Vector2D(1, 2);
        Vector2D v2 = v1.opposite();
        assertEquals(-1, v2.x);
        assertEquals(-2, v2.y);
    }
}