package ca.morais.everton.interfaces;

import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * This interface stores elements from {@link java.lang.Number} type and provides the average of the last N elements.<br/>
 * It is also possible to retrieve a single structure's element or a collection of all added elements previously. <br/>
 * Each subclass is free to implement the structure the best it wants to.
 *
 * @param <T> Type of element's structure. It must extend the {@link java.lang.Number} interface.
 * @author Everton Morais
 */
public interface MovingAverage<T extends Number> {

    /**
     * Adds a new element on the structure.<br/>
     *
     * @param element The element to be added
     */
    void add(T element);

    /**
     * Returns the element based on its index.<br/>
     * If you do not know the element's index, use {@link #get(int) get} method.<br/>
     *
     * @param index Element's index
     * @return The element
     */
    T getElement(int index);

    /**
     * Returns a collections of elements added previously on the structure.<br/>
     *
     * @return Collection of elements
     */
    Collection<T> getElements();

    /**
     * Sums two parameters.<br/>
     * This method is used in the {@link #average(int) average} method.<br/>
     * Each interface's implementation is free to implement the correct way to calculate accordingly to its parameter {@param <T>} type.<br/>
     *
     * @param a First number to add
     * @param b Second number to add
     * @return The parameter's sum
     */
    T sum(T a, T b);

    /**
     * Divides the number {@param a} by the {@param numberOfElements} on the structure.<br/>
     * It is used in the {@link #average(int) average} method, after all elements were summed.<br/>
     * Each interface's implementation is free to implement the correct way to calculate accordingly to its parameter {@param <T>} type.<br/>
     *
     * @param a                Number accumulator resulted from {@link #sum(T, T) sum} method
     * @param numberOfElements Number of elements on the structure
     * @return The division between both parameters
     */
    T dividedByNumberOfElements(T a, int numberOfElements);

    /**
     * Returns the initial value based on the {@param <T>} param's interface. Each implementation must take care about the correct value.<br/>
     * It is used in the {@link #average(int) average} method, before it starts to sum all elements on the structure.<br/>
     *
     * @return The initial value to start summing all elements
     */
    T initialResult();

    /**
     * <b>Example:</b></br>
     * <pre>
     * movingAverage.add(4);
     * movingAverage.size(); // returns 1;
     *
     * movingAverage.add(5);
     * movingAverage.size(); // returns 2;
     *
     * movingAverage.add(6);
     * movingAverage.size(); // returns 3;
     * </pre>
     *
     * @return The amount of added elements / The structure's size
     */
    int size();

    /**
     * Returns the element on the structure based on the {@param position}.<br/>
     * <b>Example:</b></br>
     * <pre>
     * movingAverage.add(7);
     * movingAverage.add(8);
     * movingAverage.add(9);
     *
     * movingAverage.get(1); // returns 7;
     * movingAverage.get(2); // returns 8;
     * movingAverage.get(3); // returns 9;
     * </pre>
     *
     * @param position The element's position
     * @return The element
     * @throws NoSuchElementException Thrown when the element is not found or there's no element added on the structure
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
     * Calculates the average of the most recent added elements, based on {@code lastElements} param.<br/>
     * <b>Example:</b></br>
     * <pre>
     * movingAverage.add(2);
     * movingAverage.add(4);
     * movingAverage.add(6);
     *
     * movingAverage.average(1); // 6 => last element was 6 || 6  / 1 = 6;
     * movingAverage.average(2); // 5 => 6 + 4 = 10         || 10 / 2 = 5;
     * movingAverage.average(3); // 4 => 6 + 4 + 2 = 12     || 12 / 3 = 4;
     * </pre>
     *
     * @param lastElements Number of last elements on the structure to calculate the average
     * @return The calculated average based on the last elements
     * @throws UnsupportedOperationException Thrown when there is no item to calculate the average
     * @throws NoSuchElementException        Thrown when the number of elements required for the average is greater than added elements
     */
    default T average(int lastElements) {
        if (size() <= 0) {
            throw new UnsupportedOperationException("There is no element to calculate the average.");
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
