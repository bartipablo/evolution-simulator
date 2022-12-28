package com.example.evolutiongenerator.gui;

import com.example.evolutiongenerator.interfaces.IMapElement;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox {
    private final VBox vBox = new VBox();

    GuiElementBox(IMapElement element) {
        try {
            Image image = new Image(new FileInputStream(element.getImagePath()));
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(40);
            imageView.setFitWidth(40);


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