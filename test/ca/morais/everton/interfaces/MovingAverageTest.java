package ca.morais.everton.interfaces;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class MovingAverageTest {

    private static final int NUMBER_OF_ELEMENTS_TEST = 3;

    @Test(expected = NoSuchElementException.class)
    public void get_whenThereIsNoElements_throwsException() {
        MovingAverage movingAverage = new MovingAverageImplTest();

        int position = new Random().nextInt(Integer.SIZE - 1);
        movingAverage.get(position);
    }

    @Test(expected = NoSuchElementException.class)
    public void get_whenPositionIsGreaterThanSize_throwsException() {
        MovingAverage movingAverage = new MovingAverageImplTest();
        movingAverage.add(new Random().nextInt());

        movingAverage.get(movingAverage.size() + 1);
    }

    @Test
    public void average_whenPositionExists_returnsItemCorrectly() {
        MovingAverage movingAverage = new MovingAverageImplTest();

        Random random = new Random();
        Integer elementA = random.nextInt();
        Integer elementB = random.nextInt();

        movingAverage.add(elementA);
        movingAverage.add(elementB);

        Number resultedA = movingAverage.get(1);
        assertEquals(elementA, resultedA);

        Number resultedB = movingAverage.get(2);
        assertEquals(elementB, resultedB);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void average_whenThereIsNoElements_throwsException() {
        MovingAverage movingAverage = new MovingAverageImplTest();

        int lastElements = new Random().nextInt(Integer.SIZE - 1);
        movingAverage.average(lastElements);
    }

    @Test(expected = NoSuchElementException.class)
    public void average_whenNumberOfElementsRequestedIsGreaterThanAdded_throwsException() {
        MovingAverage movingAverage = new MovingAverageImplTest();

        Random random = new Random();
        Integer elementA = random.nextInt();
        Integer elementB = random.nextInt();

        movingAverage.add(elementA);
        movingAverage.add(elementB);

        movingAverage.average(NUMBER_OF_ELEMENTS_TEST);
    }

    @Test
    public void average_whenThereIsOnlyOneElement_returnsItself() {
        MovingAverage movingAverage = new MovingAverageImplTest();

        Integer element = new Random().nextInt();
        movingAverage.add(element);

        Number resulted = movingAverage.average(1);

        assertEquals(element, resulted);
    }

    @Test
    public void average_whenNumberOfElementsAddedAndRequestedAreTheSame_returnsCalculatedAverage() {
        MovingAverage movingAverage = new MovingAverageImplTest();

        Random random = new Random();
        Integer elementA = random.nextInt();
        Integer elementB = random.nextInt();
        Integer elementC = random.nextInt();

        movingAverage.add(elementA);
        movingAverage.add(elementB);
        movingAverage.add(elementC);

        Number resulted = movingAverage.average(NUMBER_OF_ELEMENTS_TEST);

        Number expected = (elementA + elementB + elementC) / NUMBER_OF_ELEMENTS_TEST;

        assertEquals(expected, resulted);
    }

    @Test
    public void average_whenNumberOfElementsAddedIsGreaterThanRequestedForAverage_returnsCalculatedAverage() {
        MovingAverage movingAverage = new MovingAverageImplTest();

        Random random = new Random();
        Integer elementA = random.nextInt();
        Integer elementB = random.nextInt();
        Integer elementC = random.nextInt();
        Integer elementD = random.nextInt();

        movingAverage.add(elementA);
        movingAverage.add(elementB);
        movingAverage.add(elementC);
        movingAverage.add(elementD);

        Number resulted = movingAverage.average(NUMBER_OF_ELEMENTS_TEST);

        Number expected = (elementB + elementC + elementD) / NUMBER_OF_ELEMENTS_TEST;

        assertEquals(expected, resulted);
    }

    private class MovingAverageImplTest implements MovingAverage<Integer> {
        private List<Integer> items;
        private int size;

        private MovingAverageImplTest() {
            this.items = new ArrayList<>();
            this.size = 0;
        }

        @Override
        public Integer initialResult() {
            return 0;
        }

        @Override
        public int size() {
            return size;
        }

        @Override
        public void add(Integer element) {
            items.add(element);
            size++;
        }

        @Override
        public Integer getElement(int index) {
            return items.get(index);
        }

        @Override
        public Collection<Integer> getElements() {
            return items;
        }

        @Override
        public Integer sum(Integer a, Integer b) {
            return a + b;
        }

        @Override
        public Integer dividedByNumberOfElements(Integer a, int numberOfElements) {
            return a / numberOfElements;
        }
    }
}