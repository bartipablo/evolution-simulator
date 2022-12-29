package com.example.evolutiongenerator.direction;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class MapDirectionTest {
    @Test
    public void testToUnitVector() {
        assertEquals(new Vector2D(0, 1), MapDirection.NORTH.toUnitVector());
        assertEquals(new Vector2D(1, 1), MapDirection.NORTH_EAST.toUnitVector());
        assertEquals(new Vector2D(1, 0), MapDirection.EAST.toUnitVector());
        assertEquals(new Vector2D(1, -1), MapDirection.SOUTH_EAST.toUnitVector());
        assertEquals(new Vector2D(0, -1), MapDirection.SOUTH.toUnitVector());
        assertEquals(new Vector2D(-1, -1), MapDirection.SOUTH_WEST.toUnitVector());
        assertEquals(new Vector2D(-1, 0), MapDirection.WEST.toUnitVector());
        assertEquals(new Vector2D(-1, 1), MapDirection.NORTH_WEST.toUnitVector());
    }

    @Test
    public void testOpposite() {
        assertEquals(MapDirection.SOUTH, MapDirection.NORTH.opposite());
        assertEquals(MapDirection.SOUTH_WEST, MapDirection.NORTH_EAST.opposite());
        assertEquals(MapDirection.WEST, MapDirection.EAST.opposite());
        assertEquals(MapDirection.NORTH_WEST, MapDirection.SOUTH_EAST.opposite());
        assertEquals(MapDirection.NORTH, MapDirection.SOUTH.opposite());
        assertEquals(MapDirection.NORTH_EAST, MapDirection.SOUTH_WEST.opposite());
        assertEquals(MapDirection.EAST, MapDirection.WEST.opposite());
        assertEquals(MapDirection.SOUTH_EAST, MapDirection.NORTH_WEST.opposite());
    }

    @Test
    public void testGenerateRandomDirection() {
        // Test if generateRandomDirection() returns a random direction a few times
        for (int i = 0; i < 100; i++) {
            MapDirection direction = MapDirection.generateRandomDirection();
            assertTrue(direction == MapDirection.NORTH || direction == MapDirection.NORTH_EAST || direction == MapDirection.EAST ||
                    direction == MapDirection.SOUTH_EAST || direction == MapDirection.SOUTH || direction == MapDirection.SOUTH_WEST ||
                    direction == MapDirection.WEST || direction == MapDirection.NORTH_WEST);
        }
    }

    @Test
    public void testNextDirection() {
        // Test nextDirection() on each direction
        assertEquals(MapDirection.NORTH_EAST, MapDirection.NORTH.nextDirection());
        assertEquals(MapDirection.EAST, MapDirection.NORTH_EAST.nextDirection());
        assertEquals(MapDirection.SOUTH_EAST, MapDirection.EAST.nextDirection());
        assertEquals(MapDirection.SOUTH, MapDirection.SOUTH_EAST.nextDirection());
        assertEquals(MapDirection.SOUTH_WEST, MapDirection.SOUTH.nextDirection());
        assertEquals(MapDirection.WEST, MapDirection.SOUTH_WEST.nextDirection());
        assertEquals(MapDirection.NORTH_WEST, MapDirection.WEST.nextDirection());
        assertEquals(MapDirection.NORTH, MapDirection.NORTH_WEST.nextDirection());

        // Test nextDirection() multiple times on a direction
        MapDirection direction = MapDirection.NORTH;
        for (int i = 0; i < 7; i++) {
            direction = direction.nextDirection();
        }
        assertEquals(MapDirection.NORTH_WEST, direction);
    }

    @Test
    public void testRotation() {
        // Test rotation() with positive n
        MapDirection direction = MapDirection.NORTH;
        direction = direction.rotation(27);
        assertEquals(MapDirection.SOUTH_EAST, direction);


        // Test rotation() with negative n
        direction = MapDirection.NORTH;
        direction = direction.rotation(-51);
        assertEquals(MapDirection.NORTH, direction);


        // Test rotation() with n equal to 0
        direction = MapDirection.NORTH;
        direction = direction.rotation(0);
        assertEquals(MapDirection.NORTH, direction);
    }
}