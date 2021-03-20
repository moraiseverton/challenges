package ca.morais.everton.algorithms;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class RemoveDuplicatesTest {

    @Test
    public void removeDuplicates_whenArrayIsEmpty_returnsEmptyArray() {
        int[] array = {};

        int[] resulted = RemoveDuplicates.removeDuplicates(array);

        assertArrayEquals(resulted, array);
    }

    @Test
    public void removeDuplicates_whenArrayDoesNotHaveDuplicates_returnsSameArray() {
        int[] array = {1, 2, 5, 8, 10};

        int[] resulted = RemoveDuplicates.removeDuplicates(array);

        assertArrayEquals(resulted, array);
    }

    @Test
    public void removeDuplicates_whenArrayDoesHaveDuplicates_returnsCorrectArray() {
        int[] array = {5, 1, 2, 5, 8, 10};

        int[] resulted = RemoveDuplicates.removeDuplicates(array);

        int[] expected = {1, 2, 5, 8, 10};

        assertArrayEquals(resulted, expected);
    }

    @Test
    public void removeDuplicates_whenArrayDoesHaveDuplicatesInTheEnd_returnsCorrectArray() {
        int[] array = {8, 2, 1, 10, 3, 4, 5, 5};

        int[] resulted = RemoveDuplicates.removeDuplicates(array);

        int[] expected = {1, 2, 3, 4, 5, 8, 10};

        assertArrayEquals(resulted, expected);
    }

    @Test
    public void removeDuplicates_whenArrayDoesHaveMoreThanTwoDuplicates_returnsCorrectArray() {
        int[] array = {3, 8, 10, 4, 2, 1, 10, 3, 1, 4, 5, 5};

        int[] resulted = RemoveDuplicates.removeDuplicates(array);

        int[] expected = {1, 2, 3, 4, 5, 8, 10};

        assertArrayEquals(resulted, expected);
    }
}