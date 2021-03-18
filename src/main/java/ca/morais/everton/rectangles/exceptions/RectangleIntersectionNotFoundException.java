package ca.morais.everton.rectangles.exceptions;

import ca.morais.everton.rectangles.Rectangle;

public class RectangleIntersectionNotFoundException extends RuntimeException {

    public RectangleIntersectionNotFoundException(Rectangle alpha, Rectangle beta) {
        super("Rectangle's intersection not found. alpha: " + alpha + " - beta: " + beta);
    }
}