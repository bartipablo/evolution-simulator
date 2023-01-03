package com.example.evolutiongenerator.gui;

import com.example.evolutiongenerator.direction.Vector2D;
import com.example.evolutiongenerator.interfaces.IMap;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox {
    private final VBox vBox = new VBox();

    GuiElementBox(IMap map, Vector2D position, int sideLengthOfSquarePx) {
        try {
            Image image = new Image(new FileInputStream(map.getPathImageAtPosition(position)));
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(sideLengthOfSquarePx);
            imageView.setFitWidth(sideLengthOfSquarePx);
            vBox.getChildren().add(imageView);
            vBox.setAlignment(Pos.BASELINE_CENTER);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public VBox getVBox() {
        return vBox;
    }

}