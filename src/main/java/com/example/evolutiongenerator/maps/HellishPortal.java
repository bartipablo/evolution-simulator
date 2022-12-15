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

    @Override
    public Vector2D calculatePositionAfterMovement(Vector2D position, MapDirection direction) {

    }

    @Override
    public MapDirection calculateDirectionAfterMovement(Vector2D position, MapDirection direction) {

    }
}
