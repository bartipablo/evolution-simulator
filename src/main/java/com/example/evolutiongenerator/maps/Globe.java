package com.example.evolutiongenerator.maps;

import com.example.evolutiongenerator.Animal;
import com.example.evolutiongenerator.Vector2D;
import com.example.evolutiongenerator.direction.MapDirection;
import com.example.evolutiongenerator.direction.MoveDirection;

public class Globe extends AbstractMap {

    private final int mapHeight;
    private final int mapWidth;

    // constructor------------------------------
    Globe(int mapHeight, int mapWidth) {
        this.mapHeight = mapHeight;
        this.mapWidth  = mapWidth;
    }
    //------------------------------------------

    public int getMapHeight() {
        return mapHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    @Override
    public Vector2D calculatePositionAfterMovement(Vector2D position, MapDirection direction) {
        Vector2D unitVector = direction.toUnitVector();
        Vector2D newPosition = new Vector2D(position.x, position.y);
        newPosition.add(unitVector);

        if (newPosition.x < 0) {
            newPosition = new Vector2D(mapWidth - 1, newPosition.y);
        } else if (newPosition.x > mapWidth - 1) {
            newPosition = new Vector2D(0, newPosition.y);
        }

        if (newPosition.y < 0) {
            newPosition = new Vector2D(newPosition.x, 0);
        } else if (newPosition.x > mapWidth - 1) {
            newPosition = new Vector2D(newPosition.x, mapHeight - 1);
        }

        return newPosition;
    }

    @Override
    public MapDirection calculateDirectionAfterMovement(Vector2D position, MapDirection direction) {
        Vector2D unitVector = direction.toUnitVector();
        Vector2D newPosition = new Vector2D(position.x, position.y);
        newPosition.add(unitVector);

        if (newPosition.y < 0 || newPosition.x > mapHeight - 1) {
            return direction.opposite();
        }
        return direction;
    }
}
