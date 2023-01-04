package com.example.evolutiongenerator.gui;

import com.example.evolutiongenerator.Properties;
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
    private int sideLengthOfSquarePx;

    public MapVisualizer(IMap map, GridPane gridPane) {
        this.map = map;
        this.gridPane = gridPane;
        calculateColumnsAndRowsSize();
    }

    private void calculateColumnsAndRowsSize() {
        int squareHeight = (int) Math.ceil(1.0 * Properties.GRID_PANE_HEIGHT_PX / map.getMapHeight());
        int squareWidth  = (int) Math.ceil(1.0 * Properties.GRID_PANE_WIDTH_PX / map.getMapWidth());
        this.sideLengthOfSquarePx = Math.max(40, Math.max(squareWidth, squareHeight));
        gridPane.getColumnConstraints().add(new ColumnConstraints(sideLengthOfSquarePx));
        gridPane.getRowConstraints().add(new RowConstraints(sideLengthOfSquarePx));
    }

    public void  visualizeMap() {
        gridPane.getChildren().clear();
        for (int x = 0; x < map.getMapWidth(); x++) {
            for (int y = 0; y < map.getMapHeight(); y++) {
                Vector2D position = new Vector2D(x, y);
                GuiElementBox guiElementBox = new GuiElementBox(map, position, sideLengthOfSquarePx);
                gridPane.add(guiElementBox.getVBox(), x, y);
            }
        }
    }

}