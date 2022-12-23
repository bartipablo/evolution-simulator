package com.example.evolutiongenerator.maps;

import com.example.evolutiongenerator.direction.Vector2D;
import com.example.evolutiongenerator.direction.MapDirection;

public class HellishPortal extends AbstractMap {

    public HellishPortal(int mapHeight, int mapWidth) {
        super(mapHeight, mapWidth);
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
