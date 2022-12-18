module com.example.evolutiongenerator {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens com.example.evolutiongenerator to javafx.fxml;
    exports com.example.evolutiongenerator;
    exports com.example.evolutiongenerator.gui;
    opens com.example.evolutiongenerator.gui to javafx.fxml;
    exports com.example.evolutiongenerator.maps;
    opens com.example.evolutiongenerator.maps to javafx.fxml;
    exports com.example.evolutiongenerator.direction;
    opens com.example.evolutiongenerator.direction to javafx.fxml;
    exports com.example.evolutiongenerator.genes;
    opens com.example.evolutiongenerator.genes to javafx.fxml;
    exports com.example.evolutiongenerator.animals;
    opens com.example.evolutiongenerator.animals to javafx.fxml;
}