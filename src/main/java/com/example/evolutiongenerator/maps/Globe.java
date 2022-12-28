package com.example.evolutiongenerator.maps;

import com.example.evolutiongenerator.direction.Vector2D;
import com.example.evolutiongenerator.direction.MapDirection;

public class Globe extends AbstractMap {

    public Globe(int mapHeight, int mapWidth) {
        super(mapHeight, mapWidth);
    }

    @Override
    public Vector2D calculatePositionAfterMovement(Vector2D position) {
        if (position.x < 0) {
            position = new Vector2D(mapWidth - 1, position.y);
        } else if (position.x > mapWidth - 1) {
            position = new Vector2D(0, position.y);
        }

        if (position.y < 0) {
            position = new Vector2D(position.x, 0);
        } else if (position.x > mapWidth - 1) {
            position = new Vector2D(position.x, mapHeight - 1);
        }
        return position;
    }

    @Override
    public MapDirection calculateDirectionAfterMovement(Vector2D position, MapDirection direction) {
        if (position.y < 0 || position.x > mapHeight - 1) {
            return direction.opposite();
        }
        return direction;
    }

}
