package com.example.evolutiongenerator.gui;

import com.example.evolutiongenerator.Constant;
import com.example.evolutiongenerator.direction.Vector2D;
import com.example.evolutiongenerator.interfaces.IMap;
import com.example.evolutiongenerator.interfaces.IMapElement;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class MapVisualizer {

    //grid pane size:
    //width: 867 px
    //height: 715 px

    private int sideLengthOfSquarePx;
    private final IMap map;


    public MapVisualizer(IMap map) {
        this.map = map;
        calculateColumnsAndRowsSize();
    }

    private void calculateColumnsAndRowsSize() {
        int squareHeight = (int) Math.ceil(1.0 * Constant.GRID_PANE_HEIGHT_PX / map.getMapHeight());
        int squareWidth  = (int) Math.ceil(1.0 * Constant.GRID_PANE_WIDTH_PX / map.getMapWidth());
        sideLengthOfSquarePx = Math.min(squareWidth, squareHeight);
    }


    public GridPane visualizeMap() {
        GridPane grid = new GridPane();
        grid.getColumnConstraints().add(new ColumnConstraints(sideLengthOfSquarePx));
        grid.getRowConstraints().add(new RowConstraints(sideLengthOfSquarePx));

        for (int x = 0; x <= map.getMapWidth(); x++) {
            for (int y = 0; y <= map.getMapHeight(); y++) {
                Vector2D position = new Vector2D(x, y);
                if (map.isOccupied(position)) {
                    IMapElement object = (IMapElement) map.objectAt(position);
                    GuiElementBox guiElementBox = new GuiElementBox(object);
                    grid.add(guiElementBox.getVBox(), x, y);
                }
            }
        }
        return grid;
    }

}