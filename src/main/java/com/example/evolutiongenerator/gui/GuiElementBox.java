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

    private final static Image emptyPositionImage;

    private final static Image emptyPositionWithPlantImage;

    private final static Image oneAnimalsPositionImage;

    private final static Image twoAnimalsPositionImage;

    private final static Image threeAnimalsPositionImage;

    private final static Image fourAnimalsPositionImage;

    private final static Image oneAnimalsPositionWithPlantImage;

    private final static Image twoAnimalsPositionWithPlantImage;

    private final static Image threeAnimalsPositionWithPlantImage;

    private final static Image fourAnimalsPositionWithPlantImage;

    private final static Image errorImage;

    static {
        try {
            emptyPositionImage = new Image(new FileInputStream("./src/main/resources/com/example/evolutiongenerator/emptyPosition.png"));
            emptyPositionWithPlantImage = new Image(new FileInputStream("./src/main/resources/com/example/evolutiongenerator/emptyPositionWithPlant.png"));
            oneAnimalsPositionImage = new Image(new FileInputStream("./src/main/resources/com/example/evolutiongenerator/oneAnimalsPosition.png"));
            twoAnimalsPositionImage = new Image(new FileInputStream("./src/main/resources/com/example/evolutiongenerator/twoAnimalsPosition.png"));
            threeAnimalsPositionImage = new Image(new FileInputStream("./src/main/resources/com/example/evolutiongenerator/threeAnimalsPosition.png"));
            fourAnimalsPositionImage = new Image(new FileInputStream("./src/main/resources/com/example/evolutiongenerator/fourAnimalsPosition.png"));
            oneAnimalsPositionWithPlantImage = new Image(new FileInputStream("./src/main/resources/com/example/evolutiongenerator/oneAnimalsPositionWithPlant.png"));
            twoAnimalsPositionWithPlantImage = new Image(new FileInputStream("./src/main/resources/com/example/evolutiongenerator/twoAnimalsPositionWithPlant.png"));
            threeAnimalsPositionWithPlantImage = new Image(new FileInputStream("./src/main/resources/com/example/evolutiongenerator/threeAnimalsPositionWithPlant.png"));
            fourAnimalsPositionWithPlantImage = new Image(new FileInputStream("./src/main/resources/com/example/evolutiongenerator/fourAnimalsPositionWithPlant.png"));
            errorImage = new Image(new FileInputStream("./src/main/resources/com/example/evolutiongenerator/error.png"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private final VBox vBox = new VBox();

    private Image getImageAtPosition(IMap map, Vector2D position) {
        int quantityOfAnimalsAtPosition = map.getAnimalsAtPosition(position).size();
        int quantityOfPlantsAtPosition = map.getPlantAtPosition(position) == null ? 0 : 1;

        if (quantityOfPlantsAtPosition == 0) {
            if (quantityOfAnimalsAtPosition == 0) {
                return emptyPositionImage;
            } else if (quantityOfAnimalsAtPosition == 1) {
                return oneAnimalsPositionImage;
            } else if (quantityOfAnimalsAtPosition == 2) {
                return twoAnimalsPositionImage;
            } else if (quantityOfAnimalsAtPosition == 3) {
                return threeAnimalsPositionImage;
            } else {
                return fourAnimalsPositionImage;
            }
        } else {
            if (quantityOfAnimalsAtPosition == 0) {
                return emptyPositionWithPlantImage;
            } else if (quantityOfAnimalsAtPosition == 1) {
                return oneAnimalsPositionWithPlantImage;
            } else if (quantityOfAnimalsAtPosition == 2) {
                return twoAnimalsPositionWithPlantImage;
            } else if (quantityOfAnimalsAtPosition == 3) {
                return threeAnimalsPositionWithPlantImage;
            } else {
                return fourAnimalsPositionWithPlantImage;
            }
        }

    }

    GuiElementBox(IMap map, Vector2D position, int sideLengthOfSquarePx) {
        Image image = getImageAtPosition(map, position);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(sideLengthOfSquarePx);
        imageView.setFitWidth(sideLengthOfSquarePx);
        vBox.getChildren().add(imageView);
        vBox.setAlignment(Pos.BASELINE_CENTER);
    }

    public VBox getVBox() {
        return vBox;
    }

}