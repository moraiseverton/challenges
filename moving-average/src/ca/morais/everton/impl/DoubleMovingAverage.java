package ca.morais.everton.impl;

import ca.morais.everton.interfaces.MovingAverage;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class DoubleMovingAverage implements MovingAverage<Double> {
    private double[] items;
    private int size;

    public DoubleMovingAverage() {
        this.items = new double[0];
        this.size = 0;
    }

    @Override
    public Double initialResult() {
        return 0d;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void add(Double element) {
        if (element == null) {
            throw new NullPointerException("Null elements are not allowed.");
        }

        double[] old = items.clone();
        this.items = new double[size + 1];

        System.arraycopy(old, 0, this.items, 0, size);
        this.items[size++] = element;
    }

    @Override
    public Double getElement(int index) {
        return this.items[index];
    }

    @Override
    public Collection<Double> getElements() {
        return Arrays.stream(this.items)
                     .boxed()
                     .collect(Collectors.toList());
    }

    @Override
    public Double sum(Double a, Double b) {
        if (a == null || b == null) {
            throw new NullPointerException("Elements cannot be null.");
        }
        return a + b;
    }

    @Override
    public Double dividedByNumberOfElements(Double a, int numberOfElements) {
        if (a == null) {
            throw new NullPointerException("Element cannot be null.");
        }
        if (numberOfElements == 0) {
            throw new ArithmeticException("It is not possible divided by zero.");
        }

        return a / numberOfElements;
    }
}
