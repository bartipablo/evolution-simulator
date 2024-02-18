package com.example.evolutiongenerator.maps;

import com.example.evolutiongenerator.Plant;
import com.example.evolutiongenerator.animals.AnimalBehaviourA;
import com.example.evolutiongenerator.direction.MapDirection;
import com.example.evolutiongenerator.direction.Vector2D;
import com.example.evolutiongenerator.interfaces.IAnimal;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class AbstractMapTest {

    @Test
    void mapTest(){
        AbstractMap map = new Globe(10,10);
        IAnimal animal = new AnimalBehaviourA(new Vector2D(0,0), MapDirection.NORTH,map,10,10);
        IAnimal animal1= new AnimalBehaviourA(new Vector2D(0,1), MapDirection.NORTH,map,10,10);
        map.addAnimalToMap(animal);
        map.addAnimalToMap(animal1);
        assertEquals(map.getAnimalsAtPosition(new Vector2D(0,0)).get(0),animal);
        assertEquals(map.getAnimalsAtPosition(new Vector2D(0,1)).get(0),animal1);
        map.changePositionOnMap(animal,new Vector2D(0,0),new Vector2D(1,1));
        map.removeAnimalFromMap(animal1);
        assertEquals(map.getAnimalsAtPosition(new Vector2D(1,1)).get(0),animal);
        assertEquals(map.getAnimalsAtPosition(new Vector2D(0,1)).size(),0);
        System.out.println(Arrays.toString(map.getAnimalsPositions()));
    }

    @Test
    void deathTest(){
        AbstractMap map = new Globe(10,10);
        IAnimal animal = new AnimalBehaviourA(new Vector2D(0,0), MapDirection.NORTH,map,10,10);
        IAnimal animal1= new AnimalBehaviourA(new Vector2D(1,1), MapDirection.NORTH,map,10,10);
        IAnimal animal2 = new AnimalBehaviourA(new Vector2D(0,2), MapDirection.NORTH,map,10,10);
        IAnimal animal3= new AnimalBehaviourA(new Vector2D(1,1), MapDirection.NORTH,map,10,10);
        map.addAnimalToMap(animal);
        map.addAnimalToMap(animal1);
        map.addAnimalToMap(animal2);
        map.addAnimalToMap(animal3);
        assertEquals(map.getAnimalsAtPosition(new Vector2D(1,1)).size(),2);
        map.removeAnimalFromMap(animal);
        map.removeAnimalFromMap(animal1);
        map.removeAnimalFromMap(animal2);
        map.removeAnimalFromMap(animal3);
        System.out.println(map.getPositionsSortedByNumbersOfDeaths());
    }

    @Test
    void plantTest(){
        AbstractMap map = new Globe(10,10);
        Plant plant = new Plant(new Vector2D(1,0),5);
        Plant plant1 = new Plant(new Vector2D(0,0),8);
        map.addPlantToMap(plant);
        map.addPlantToMap(plant1);
        map.removePlantFromMap(plant1);
        assertNull(map.getPlantAtPosition(plant1.getPosition()));
    }

}