package ca.morais.everton.algorithms;

import java.util.Arrays;


public class RemoveDuplicates {

    static int[] removeDuplicates(int[] array) {
        int n = array.length;
        if (n == 0 || n == 1) {
            return array;
        }

        Arrays.sort(array);

        int[] result = new int[n];

        int index = 0;
        int newIndex = 0;

        while (index < n-1) {
            if (array[index] != array[index+1]) {
                result[newIndex++] = array[index];
            }
            index++;
        }

        result[newIndex++] = array[index];

        int newArray[] = new int[newIndex];
        System.arraycopy(result, 0, newArray, 0, newIndex);

        return newArray;
    }
}
