package com.example.evolutiongenerator;

import com.example.evolutiongenerator.direction.Vector2D;

import java.util.Random;

public class Static {
    public static void shuffleIntArray(int[] array) {
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int a = array[index];
            array[index] = array[i];
            array[i] = a;
        }
    }

    public static void shuffleVector2DArray(Vector2D[] array) {
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            Vector2D a = array[index];
            array[index] = array[i];
            array[i] = a;
        }
    }

    public static int[] generateIntArray(int firstNumber, int lastNumber) {
        if (lastNumber < firstNumber) {
            return null;
        }
        int[] resultArray = new int[lastNumber - firstNumber + 1];
        for (int i = firstNumber; i <= lastNumber; i++) {
            resultArray[i - firstNumber] = i;
        }
        return resultArray;
    }

    public static Vector2D[] generateVector2DArray(int xMin, int xMax, int yMin, int yMax) {
        if (xMax < xMin || yMax < yMin) {
            return null;
        }
        Vector2D[] resultArray = new Vector2D[(xMax - xMin) * (yMax - yMin)];
        for (int i = 0; i < ( xMax - xMin); i++) {
            for (int j = 0; j < (yMax - yMin); j++) {
                resultArray[i * (yMax - yMin) + j] = new Vector2D(i ,j);
            }
        }
        return resultArray;
    }

    public static int[] concatTwoIntArrays(int[] array1, int[] array2) {
        int[] result = new int[array1.length + array2.length];
        System.arraycopy(array1, 0, result, 0, array1.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }

    public static Vector2D[] concatTwoVector2DArrays(Vector2D[] array1, Vector2D[] array2) {
        Vector2D[] result = new Vector2D[array1.length + array2.length];
        System.arraycopy(array1, 0, result, 0, array1.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }

    public static boolean isStringInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

}
