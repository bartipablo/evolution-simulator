package com.example.evolutiongenerator.animals;

import com.example.evolutiongenerator.Gene;
import com.example.evolutiongenerator.direction.MapDirection;
import com.example.evolutiongenerator.direction.Vector2D;
import com.example.evolutiongenerator.interfaces.IAnimal;
import com.example.evolutiongenerator.maps.Globe;
import com.example.evolutiongenerator.maps.HellishPortal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalBehaviourATest {

    @Test
    void moveGlobeTest(){
        IAnimal animal = new AnimalBehaviourA(new Vector2D(0,0), MapDirection.NORTH,
                new Globe(10, 10), new Gene(new int[]{3,0,6,7}) , 10);
        animal.move();
        assertEquals(animal.getPosition(),new Vector2D(1,0));
        assertEquals(animal.getDirection(),MapDirection.NORTH_WEST);
        animal.move();
        assertEquals(animal.getPosition(),new Vector2D(0,1));
        assertEquals(animal.getDirection(),MapDirection.NORTH_WEST);
        animal.move();
        assertEquals(animal.getPosition(),new Vector2D(9,0));
        assertEquals(animal.getDirection(),MapDirection.SOUTH_WEST);
    }

    @Test
    void moveHellishTest(){
        AbstractAnimal animal = new AnimalBehaviourA(new Vector2D(0,0), MapDirection.NORTH,
                new HellishPortal(10, 10), new Gene(new int[]{5,2,0,6}) , 10);
        animal.move();
        assertEquals(animal.getDirection(),MapDirection.SOUTH_WEST);
    }
}