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
    @Test
    public void ConcatTwoIntArraysTest() {
        //two non empty arrays
        int[] array1 = {1, 2, 3};
        int[] array2 = {4, 5, 6};
        int[] expected = {1, 2, 3, 4, 5, 6};
        int[] result = concatTwoIntArrays(array1, array2);
        assertArrayEquals(expected, result);

        //two empty arrays
        int[] array3 = {};
        int[] array4 = {};
        int[] expected1 = {};
        int[] result1 = concatTwoIntArrays(array3, array4);
        assertArrayEquals(expected1, result1);

        //test ConcatEmptyArray And Non Empty Array

        int[] array5 = {};
        int[] array6 = {1, 2, 3};
        int[] expected2 = {1, 2, 3};
        int[] result2 = concatTwoIntArrays(array5, array6);
        assertArrayEquals(expected2, result2);

        int[] array7 = {1, 2, 3};
        int[] array8 = {};
        int[] expected3 = {1, 2, 3};
        int[] result3 = concatTwoIntArrays(array3, array4);
        assertArrayEquals(expected2, result2);


        //test ConcatTwoArrays With Single Element Each

        int[] array9 = {1};
        int[] array0 = {2};
        int[] expected4 = {1, 2};
        int[] result4 = concatTwoIntArrays(array9, array0);
        assertArrayEquals(expected4, result4);

    }
    @Test
    public void ConcatTwoVector2DArraysTest(){
        //testConcat Two NonEmpty Arrays
            Vector2D[] array1 = {new Vector2D(1, 2), new Vector2D(3, 4)};
            Vector2D[] array2 = {new Vector2D(5, 6), new Vector2D(7, 8)};
            Vector2D[] expected = {new Vector2D(1, 2), new Vector2D(3, 4), new Vector2D(5, 6), new Vector2D(7, 8)};
            Vector2D[] result = concatTwoVector2DArrays(array1, array2);
            assertArrayEquals(expected, result);


        //testConcat Two Empty Arrays
            Vector2D[] array3 = {};
            Vector2D[] array4 = {};
            Vector2D[] expected1 = {};
            Vector2D[] result1 = concatTwoVector2DArrays(array3, array4);
            assertArrayEquals(expected1, result1);


        //testConcat Empty Array And NonEmpty Array
            Vector2D[] array5 = {};
            Vector2D[] array6 = {new Vector2D(1, 2), new Vector2D(3, 4)};
            Vector2D[] expected2 = {new Vector2D(1, 2), new Vector2D(3, 4)};
            Vector2D[] result2 = concatTwoVector2DArrays(array5, array6);
            assertArrayEquals(expected2, result2);

            Vector2D[] array7 = {new Vector2D(1, 2), new Vector2D(3, 4)};
            Vector2D[] array8 = {};
            Vector2D[] expected3 = {new Vector2D(1, 2), new Vector2D(3, 4)};
            Vector2D[] result3 = concatTwoVector2DArrays(array7, array8);
            assertArrayEquals(expected3, result3);



        //testConcatTwoArrays With Single Element Each
            Vector2D[] array9 = {new Vector2D(1, 2)};
            Vector2D[] array0 = {new Vector2D(3, 4)};
            Vector2D[] expected4 = {new Vector2D(1, 2), new Vector2D(3, 4)};
            Vector2D[] result4 = concatTwoVector2DArrays(array9, array0);
            assertArrayEquals(expected4, result4);


    }

}


