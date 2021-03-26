package ca.morais.everton.impl;

import ca.morais.everton.interfaces.MovingAverage;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DoubleMovingAverageTest {

    @Test
    public void initialResult_returnsZero() {
        MovingAverage movingAverage = new DoubleMovingAverage();
        Double expected = 0d;
        assertEquals(movingAverage.initialResult(), expected);
    }

    @Test
    public void size_whenAddElements_returnsAmountOfAddedElements() {
        MovingAverage movingAverage = new DoubleMovingAverage();
        assertEquals(movingAverage.size(), 0);

        Random random = new Random();
        Double elementA = random.nextDouble();
        movingAverage.add(elementA);
        assertEquals(movingAverage.size(), 1);

        Double elementB = random.nextDouble();
        movingAverage.add(elementB);
        assertEquals(movingAverage.size(), 2);

        Double elementC = random.nextDouble();
        movingAverage.add(elementC);
        assertEquals(movingAverage.size(), 3);
    }

    @Test(expected = NullPointerException.class)
    public void add_whenElementIsNull_throwsException() {
        MovingAverage movingAverage = new DoubleMovingAverage();
        movingAverage.add(null);
        assertNull(movingAverage.getElement(0));
    }

    @Test
    public void add_whenAddNewElement_addsCorrectly() {
        MovingAverage movingAverage = new DoubleMovingAverage();

        Random random = new Random();
        Double elementA = random.nextDouble();
        movingAverage.add(elementA);

        Double elementB = random.nextDouble();
        movingAverage.add(elementB);

        Number resultedA = movingAverage.getElement(0);
        assertEquals(elementA, resultedA);

        Number resultedB = movingAverage.getElement(1);
        assertEquals(elementB, resultedB);
    }

    @Test
    public void getElements_whenThereIsNoElement_returnsEmptyCollection() {
        MovingAverage movingAverage = new DoubleMovingAverage();
        assertEquals(movingAverage.getElements(), Collections.emptyList());
    }

    @Test
    public void getElements_whenThereAreAddedElements_returnsCorrectly() {
        MovingAverage movingAverage = new DoubleMovingAverage();

        Random random = new Random();
        Double elementA = random.nextDouble();
        movingAverage.add(elementA);
        assertEquals(movingAverage.getElements(), Collections.singletonList(elementA));

        Double elementB = random.nextDouble();
        movingAverage.add(elementB);
        assertEquals(movingAverage.getElements(), Arrays.asList(elementA, elementB));

        Double elementC = random.nextDouble();
        movingAverage.add(elementC);
        assertEquals(movingAverage.getElements(), Arrays.asList(elementA, elementB, elementC));
    }

    @Test(expected = NullPointerException.class)
    public void sum_whenFirstElementIsNull_throwsException() {
        MovingAverage movingAverage = new DoubleMovingAverage();
        movingAverage.sum(null, new Random().nextDouble());
    }

    @Test(expected = NullPointerException.class)
    public void sum_whenSecondElementIsNull_throwsException() {
        MovingAverage movingAverage = new DoubleMovingAverage();
        movingAverage.sum(new Random().nextDouble(), null);
    }

    @Test
    public void sum_whenElementsAreNotNull_sumsCorrectly() {
        MovingAverage movingAverage = new DoubleMovingAverage();

        Random random = new Random();
        Double elementA = random.nextDouble();
        Double elementB = random.nextDouble();

        Double expected = elementA + elementB;
        Number resulted = movingAverage.sum(elementA, elementB);

        assertEquals(expected, resulted);
    }

    @Test(expected = NullPointerException.class)
    public void dividedByNumberOfElements_whenElementIsNull_throwsException() {
        MovingAverage movingAverage = new DoubleMovingAverage();
        movingAverage.dividedByNumberOfElements(null, new Random().nextInt());
    }

    @Test(expected = ArithmeticException.class)
    public void dividedByNumberOfElements_whenNumberOfElementsIsZero_throwsException() {
        MovingAverage movingAverage = new DoubleMovingAverage();
        movingAverage.dividedByNumberOfElements(new Random().nextDouble(), 0);
    }

    @Test
    public void dividedByNumberOfElements_whenElementsAreAllowed_dividesCorrectly() {
        MovingAverage movingAverage = new DoubleMovingAverage();

        Random random = new Random();
        Double elementA = random.nextDouble();
        Double elementB = random.nextDouble();

        int numberOfElements = 2;
        Double sum = (elementA + elementB);
        Number resulted = movingAverage.dividedByNumberOfElements(sum, numberOfElements);

        Double expected = sum / numberOfElements;

        assertEquals(expected, resulted);
    }

    @Test
    public void average_calculatesAverageCorrectly() {
        MovingAverage movingAverage = new DoubleMovingAverage();

        Random random = new Random();
        Double elementA = random.nextDouble();
        movingAverage.add(elementA);

        Double elementB = random.nextDouble();
        movingAverage.add(elementB);

        Double elementC = random.nextDouble();
        movingAverage.add(elementC);

        int numberOfElements = 3;
        Number resulted = movingAverage.average(numberOfElements);

        Number expected = (elementA + elementB + elementC) / numberOfElements;

        assertEquals(expected, resulted);
    }
}