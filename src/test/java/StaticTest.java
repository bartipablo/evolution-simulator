import com.example.evolutiongenerator.direction.Vector2D;
import org.junit.jupiter.api.Test;

import static com.example.evolutiongenerator.Static.*;
import static com.example.evolutiongenerator.Static.generateIntArray;
import static org.junit.jupiter.api.Assertions.*;

public class StaticTest {

    @Test
    public void generateVector2DArrayTest(){
        //Test with xMin > xMax
        Vector2D[] result = generateVector2DArray(1,2,3,4);
        Vector2D[] expected = {
                new Vector2D(1, 3),
                new Vector2D(1, 4),
                new Vector2D(2, 3),
                new Vector2D(2, 4)
        };
        System.out.println(result);
        System.out.println(expected);
        assertArrayEquals(expected, result);

        // Test with xMin > xMax
        result = generateVector2DArray(2, 1, 3, 4);
        assertNull(result);

        // Test with yMin > yMax
        result = generateVector2DArray(1, 2, 4, 3);
        assertNull(result);

        // Test with yMin > yMax
        result = generateVector2DArray(1, 1, 3, 3);
        Vector2D[] expected2 = { new Vector2D(1, 3) };
        assertArrayEquals(expected2, result);
    }

    @Test
    public void generateIntArrayTest() {
        // Test with valid input
        int[] result = generateIntArray(1, 5);
        int[] expected = { 1, 2, 3, 4, 5 };
        assertArrayEquals(expected, result);

        // Test with lastNumber < firstNumber
        result = generateIntArray(5, 1);
        assertNull(result);

        // Test with firstNumber = lastNumber
        result = generateIntArray(3, 3);
        int[] expected2 = { 3 };
        assertArrayEquals(expected2, result);
    }
}


