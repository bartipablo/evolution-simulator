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

    private final static Image oneAnimalsPositionObservedImage;

    private final static Image twoAnimalsPositionImage;
    
    private final static Image twoAnimalsPositionObservedImage;

    private final static Image threeAnimalsPositionImage;
    
    private final static Image threeAnimalsPositionObservedImage;

    private final static Image fourAnimalsPositionImage;
    
    private final static Image fourAnimalsPositionObservedImage;

    private final static Image oneAnimalsPositionWithPlantImage;
    
    private final static Image oneAnimalsPositionWithPlantObservedImage;

    private final static Image twoAnimalsPositionWithPlantImage;
    
    private final static Image twoAnimalsPositionWithPlantObservedImage;

    private final static Image threeAnimalsPositionWithPlantImage;
    
    private final static Image threeAnimalsPositionWithPlantObservedImage;

    private final static Image fourAnimalsPositionWithPlantImage;
    
    private final static Image fourAnimalsPositionWithPlantObservedImage;

    private final static Image errorImage;

    static {
        try {
            emptyPositionImage = new Image(new FileInputStream("./src/main/resources/com/example/evolutiongenerator/images/emptyPosition.png"));
            emptyPositionWithPlantImage = new Image(new FileInputStream("./src/main/resources/com/example/evolutiongenerator/images/emptyPositionWithPlant.png"));
            oneAnimalsPositionImage = new Image(new FileInputStream("./src/main/resources/com/example/evolutiongenerator/images/oneAnimalsPosition.png"));
            twoAnimalsPositionImage = new Image(new FileInputStream("./src/main/resources/com/example/evolutiongenerator/images/twoAnimalsPosition.png"));
            threeAnimalsPositionImage = new Image(new FileInputStream("./src/main/resources/com/example/evolutiongenerator/images/threeAnimalsPosition.png"));
            fourAnimalsPositionImage = new Image(new FileInputStream("./src/main/resources/com/example/evolutiongenerator/images/fourAnimalsPosition.png"));
            oneAnimalsPositionWithPlantImage = new Image(new FileInputStream("./src/main/resources/com/example/evolutiongenerator/images/oneAnimalsPositionWithPlant.png"));
            twoAnimalsPositionWithPlantImage = new Image(new FileInputStream("./src/main/resources/com/example/evolutiongenerator/images/twoAnimalsPositionWithPlant.png"));
            threeAnimalsPositionWithPlantImage = new Image(new FileInputStream("./src/main/resources/com/example/evolutiongenerator/images/threeAnimalsPositionWithPlant.png"));
            fourAnimalsPositionWithPlantImage = new Image(new FileInputStream("./src/main/resources/com/example/evolutiongenerator/images/fourAnimalsPositionWithPlant.png"));
            errorImage = new Image(new FileInputStream("./src/main/resources/com/example/evolutiongenerator/images/error.png"));
            oneAnimalsPositionObservedImage = new Image(new FileInputStream("./src/main/resources/com/example/evolutiongenerator/images/oneAnimalsPositionObserved.png"));
            twoAnimalsPositionObservedImage = new Image(new FileInputStream("./src/main/resources/com/example/evolutiongenerator/images/twoAnimalsPositionObserved.png"));
            threeAnimalsPositionObservedImage = new Image(new FileInputStream("./src/main/resources/com/example/evolutiongenerator/images/threeAnimalsPositionObserved.png"));
            fourAnimalsPositionObservedImage = new Image(new FileInputStream("./src/main/resources/com/example/evolutiongenerator/images/fourAnimalsPositionObserved.png"));
            oneAnimalsPositionWithPlantObservedImage = new Image(new FileInputStream("./src/main/resources/com/example/evolutiongenerator/images/oneAnimalsPositionWithPlantObserved.png"));
            twoAnimalsPositionWithPlantObservedImage = new Image(new FileInputStream("./src/main/resources/com/example/evolutiongenerator/images/twoAnimalsPositionWithPlantObserved.png"));
            threeAnimalsPositionWithPlantObservedImage = new Image(new FileInputStream("./src/main/resources/com/example/evolutiongenerator/images/threeAnimalsPositionWithPlantObserved.png"));
            fourAnimalsPositionWithPlantObservedImage = new Image(new FileInputStream("./src/main/resources/com/example/evolutiongenerator/images/fourAnimalsPositionWithPlantObserved.png"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private final VBox vBox = new VBox();

    private Image getImageAtPosition(IMap map, Vector2D position, boolean observedPosition) {
        int quantityOfAnimalsAtPosition = map.getAnimalsAtPosition(position).size();
        int quantityOfPlantsAtPosition = map.getPlantAtPosition(position) == null ? 0 : 1;

        if (quantityOfPlantsAtPosition == 0) {
            if (quantityOfAnimalsAtPosition == 0) {
                return emptyPositionImage;
            } else if (quantityOfAnimalsAtPosition == 1) {
                return observedPosition ? oneAnimalsPositionObservedImage : oneAnimalsPositionImage;
            } else if (quantityOfAnimalsAtPosition == 2) {
                return observedPosition ? twoAnimalsPositionObservedImage : twoAnimalsPositionImage;
            } else if (quantityOfAnimalsAtPosition == 3) {
                return observedPosition ? threeAnimalsPositionObservedImage : threeAnimalsPositionImage;
            } else {
                return observedPosition ? fourAnimalsPositionObservedImage : fourAnimalsPositionImage;
            }
        } else {
            if (quantityOfAnimalsAtPosition == 0) {
                return emptyPositionWithPlantImage;
            } else if (quantityOfAnimalsAtPosition == 1) {
                return observedPosition ? oneAnimalsPositionWithPlantObservedImage : oneAnimalsPositionWithPlantImage;
            } else if (quantityOfAnimalsAtPosition == 2) {
                return observedPosition ? twoAnimalsPositionWithPlantObservedImage : twoAnimalsPositionWithPlantImage;
            } else if (quantityOfAnimalsAtPosition == 3) {
                return observedPosition ? threeAnimalsPositionWithPlantObservedImage : threeAnimalsPositionWithPlantImage;
            } else {
                return observedPosition ? fourAnimalsPositionWithPlantObservedImage : fourAnimalsPositionWithPlantImage;
            }
        }

    }

    GuiElementBox(IMap map, Vector2D position, int sideLengthOfSquarePx, boolean observedPosition) {
        Image image = getImageAtPosition(map, position, observedPosition);
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