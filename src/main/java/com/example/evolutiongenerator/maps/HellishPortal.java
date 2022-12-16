package com.example.evolutiongenerator.maps;

import com.example.evolutiongenerator.Animal;
import com.example.evolutiongenerator.Vector2D;
import com.example.evolutiongenerator.direction.MapDirection;
import com.example.evolutiongenerator.direction.MoveDirection;

public class HellishPortal extends AbstractMap {

    private final int mapHeight;
    private final int mapWidth;

    // constructor------------------------------
    HellishPortal(int mapHeight, int mapWidth) {
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
        if (position.x < 0 || position.x > mapWidth - 1 || position.y < 0 || position.y > mapHeight - 1) {
            return Vector2D.generateRandomVector2D(0, mapWidth, 0, mapHeight);
        }
        return position;
    }

    @Override
    public MapDirection calculateDirectionAfterMovement(Vector2D position, MapDirection direction) {
        return direction;
    }

}
