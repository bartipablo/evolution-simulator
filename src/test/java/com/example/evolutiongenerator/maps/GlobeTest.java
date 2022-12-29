package com.example.evolutiongenerator.maps;

import com.example.evolutiongenerator.animals.AnimalBehaviourA;
import com.example.evolutiongenerator.direction.MapDirection;
import com.example.evolutiongenerator.direction.Vector2D;
import com.example.evolutiongenerator.interfaces.IAnimal;
import com.example.evolutiongenerator.interfaces.IMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GlobeTest {

    @Test
    void changeAnimalPositionTest(){
        AbstractMap map = new Globe(10,10);
        IAnimal animal = new AnimalBehaviourA(new Vector2D(0,0), MapDirection.NORTH,map,10,10);
        map.addAnimalToMap(animal);
        map.changePositionOnMap(animal,new Vector2D(0,0),new Vector2D(1,1));
        System.out.println(map.getAnimalsPositions().toString());
    }

}