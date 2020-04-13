package ca.morais.everton.interfaces;

import java.util.Collection;
import java.util.NoSuchElementException;

public interface MovingAverage<T extends Number> {

    /**
     * @param element
     */
    void add(T element);

    /**
     * @param index
     * @return
     */
    T getElement(int index);

    /**
     * @return
     */
    Collection<T> getElements();

    /**
     * @param a
     * @param b
     * @return
     */
    T sum(T a, T b);

    /**
     * @param a
     * @param numberOfElements
     * @return
     */
    T dividedByNumberOfElements(T a, int numberOfElements);

    /**
     * @return
     */
    T initialResult();

    /**
     * @return
     */
    int size();

    /**
     * @param position
     * @return
     */
    default T get(int position) {
        if (size() <= 0) {
            throw new NoSuchElementException("No items were added.");
        }
        int index = position - 1;
        if (size() <= index) {
            throw new NoSuchElementException("Element not found.");
        }
        return getElement(index);
    }

    /**
     * @param lastElements
     * @return
     */
    default T average(int lastElements) {
        if (size() <= 0) {
            throw new UnsupportedOperationException("There is no item to calculate the average.");
        }

        if (size() < lastElements) {
            throw new NoSuchElementException("The number of elements is greater than the number added on the structure. added items: " + (size() + 1) + " - items requrested: " + lastElements);
        }

        if (size() == 1) {
            return getElement(0);
        } else {
            T accumulator = initialResult();

            for (int index = (size() - lastElements); index < size(); index++) {
                accumulator = sum(accumulator, getElement(index));
            }

            return dividedByNumberOfElements(accumulator, lastElements);
        }
    }
}
