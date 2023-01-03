package com.example.evolutiongenerator.gui;

import com.example.evolutiongenerator.Constant;
import com.example.evolutiongenerator.direction.Vector2D;
import com.example.evolutiongenerator.interfaces.IMap;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class MapVisualizer {

    //grid pane size:
    //width: 867 px
    //height: 715 px
    private final IMap map;
    private GridPane gridPane;

    public MapVisualizer(IMap map) {
        this.map = map;
        calculateColumnsAndRowsSize();
    }

    private void calculateColumnsAndRowsSize() {
        this.gridPane = new GridPane();
        int squareHeight = (int) Math.ceil(1.0 * Constant.GRID_PANE_HEIGHT_PX / map.getMapHeight());
        int squareWidth  = (int) Math.ceil(1.0 * Constant.GRID_PANE_WIDTH_PX / map.getMapWidth());
        int sideLengthOfSquarePx = Math.min(squareWidth, squareHeight);
        gridPane.getColumnConstraints().add(new ColumnConstraints(sideLengthOfSquarePx));
        gridPane.getRowConstraints().add(new RowConstraints(sideLengthOfSquarePx));
        gridPane.setGridLinesVisible(true);
    }

    public GridPane visualizeMap() {
        calculateColumnsAndRowsSize();
        for (int x = 0; x < map.getMapWidth(); x++) {
            for (int y = 0; y < map.getMapHeight(); y++) {
                Vector2D position = new Vector2D(x, y);
                GuiElementBox guiElementBox = new GuiElementBox(map, position);
                gridPane.add(guiElementBox.getVBox(), x, y);
            }
        }
        return gridPane;
    }

}