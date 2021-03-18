package ca.morais.everton.rectangles;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RectangleTest {

    @Test
    public void overlaps_whenRectanglesOverlapOnBottomLeftOrTopRight_returnsTrue() {
        Rectangle rectangleA = new Rectangle(0, 0, 10, 20);
        Rectangle rectangleB = new Rectangle(5, 15, 30, 20);

        assertTrue(rectangleA.overlaps(rectangleB));
        assertTrue(rectangleB.overlaps(rectangleA));
    }

    @Test
    public void overlaps_whenRectanglesOverlapOnBottomRightOrTopLeft_returnsTrue() {
        Rectangle rectangleA = new Rectangle(15, 5, 30, 15);
        Rectangle rectangleB = new Rectangle(5, 10, 20, 20);

        assertTrue(rectangleA.overlaps(rectangleB));
        assertTrue(rectangleB.overlaps(rectangleA));
    }

    @Test
    public void overlaps_whenRectanglesOverlapHorizontally_returnsTrue() {
        Rectangle rectangleA = new Rectangle(10, 5, 20, 20);
        Rectangle rectangleB = new Rectangle(5, 15, 25, 25);

        assertTrue(rectangleA.overlaps(rectangleB));
        assertTrue(rectangleB.overlaps(rectangleA));
    }

    @Test
    public void overlaps_whenRectanglesOverlapVertically_returnsTrue() {
        Rectangle rectangleA = new Rectangle(5, 5, 20, 25);
        Rectangle rectangleB = new Rectangle(15, 10, 25, 20);

        assertTrue(rectangleA.overlaps(rectangleB));
        assertTrue(rectangleB.overlaps(rectangleA));
    }

    @Test
    public void overlaps_whenRectanglesDoNotOverlap_returnsFalse() {
        Rectangle rectangleA = new Rectangle(5, 5, 10, 20);
        Rectangle rectangleB = new Rectangle(10, 20, 20, 30);

        assertFalse(rectangleA.overlaps(rectangleB));
        assertFalse(rectangleB.overlaps(rectangleA));
    }

    @Test
    public void contains_returnsTrue() {
        Rectangle rectangleA = new Rectangle(0, 0, 30, 20);
        Rectangle rectangleB = new Rectangle(5, 5, 25, 15);

        assertTrue(rectangleA.contains(rectangleB));
    }

    @Test
    public void contains_whenRectanglesHaveSameBottomLeftPoint_returnsTrue() {
        Rectangle rectangleA = new Rectangle(0, 0, 30, 20);
        Rectangle rectangleB = new Rectangle(rectangleA.bottomLeft, new Rectangle.Point(25, 15));

        assertTrue(rectangleA.contains(rectangleB));
    }

    @Test
    public void contains_whenRectanglesHaveSameTopRightPoint_returnsTrue() {
        Rectangle rectangleA = new Rectangle(0, 0, 30, 20);
        Rectangle rectangleB = new Rectangle(new Rectangle.Point(5, 5), rectangleA.topRight);

        assertTrue(rectangleA.contains(rectangleB));
    }

    @Test
    public void contains_returnsFalse() {
        Rectangle rectangleA = new Rectangle(5, 5, 10, 20);
        Rectangle rectangleB = new Rectangle(15, 25, 20, 30);

        assertFalse(rectangleA.contains(rectangleB));
    }

    @Test
    public void contains_whenRectanglesOverlap_returnsFalse() {
        Rectangle rectangleA = new Rectangle(0, 0, 10, 20);
        Rectangle rectangleB = new Rectangle(5, 15, 30, 20);

        assertFalse(rectangleA.contains(rectangleB));
    }

    @Test
    public void contains_whenBothRectanglesHaveSamePoints_returnsFalse() {
        Rectangle rectangleA = new Rectangle(5, 5, 10, 20);
        Rectangle rectangleB = new Rectangle(5, 5, 10, 20);

        assertFalse(rectangleA.contains(rectangleB));
    }

    @Test
    public void contains_whenTheOppositeWayContains_returnsFalse() {
        Rectangle rectangleA = new Rectangle(0, 0, 30, 20);
        Rectangle rectangleB = new Rectangle(5, 5, 25, 15);

        assertFalse(rectangleB.contains(rectangleA));
    }

    @Test
    public void contains_whenRectanglesHaveCommonPoint_returnsFalse() {
        Rectangle rectangleA = new Rectangle(5, 5, 10, 20);
        Rectangle rectangleB = new Rectangle(10, 20, 20, 30);

        assertFalse(rectangleA.contains(rectangleB));
    }

    @Test
    public void adjacent_whenHorizontalAdjacencyIsSubLine_returnsTrue() {
        Rectangle rectangleA = new Rectangle(40, 5, 60, 20);
        Rectangle rectangleB = new Rectangle(0, 0, 40, 25);

        assertTrue(rectangleA.adjacent(rectangleB));
        assertTrue(rectangleB.adjacent(rectangleA));
    }

    @Test
    public void adjacent_whenHorizontalAdjacencyIsProper_returnsTrue() {
        Rectangle rectangleA = new Rectangle(40, 0, 60, 25);
        Rectangle rectangleB = new Rectangle(0, 0, 40, 25);

        assertTrue(rectangleA.adjacent(rectangleB));
        assertTrue(rectangleB.adjacent(rectangleA));
    }

    @Test
    public void adjacent_whenHorizontalAdjacencyIsPartialOnTop_returnsTrue() {
        Rectangle rectangleA = new Rectangle(40, 5, 60, 35);
        Rectangle rectangleB = new Rectangle(0, 0, 40, 25);

        assertTrue(rectangleA.adjacent(rectangleB));
        assertTrue(rectangleB.adjacent(rectangleA));
    }

    @Test
    public void adjacent_whenHorizontalAdjacencyIsPartialOnBottom_returnsTrue() {
        Rectangle rectangleA = new Rectangle(40, 0, 60, 25);
        Rectangle rectangleB = new Rectangle(0, 5, 40, 25);

        assertTrue(rectangleA.adjacent(rectangleB));
        assertTrue(rectangleB.adjacent(rectangleA));
    }

    @Test
    public void adjacent_whenVerticalAdjacencyIsSubLine_returnsTrue() {
        Rectangle rectangleA = new Rectangle(0, 0, 40, 25);
        Rectangle rectangleB = new Rectangle(5, 25, 35, 25);

        assertTrue(rectangleA.adjacent(rectangleB));
        assertTrue(rectangleB.adjacent(rectangleA));
    }

    @Test
    public void adjacent_whenVerticalAdjacencyIsProper_returnsTrue() {
        Rectangle rectangleA = new Rectangle(0, 0, 40, 25);
        Rectangle rectangleB = new Rectangle(0, 25, 40, 35);

        assertTrue(rectangleA.adjacent(rectangleB));
        assertTrue(rectangleB.adjacent(rectangleA));
    }

    @Test
    public void adjacent_whenVerticalAdjacencyIsPartialOnLeft_returnsTrue() {
        Rectangle rectangleA = new Rectangle(0, 0, 40, 25);
        Rectangle rectangleB = new Rectangle(5, 25, 40, 35);

        assertTrue(rectangleA.adjacent(rectangleB));
        assertTrue(rectangleB.adjacent(rectangleA));
    }

    @Test
    public void adjacent_whenVerticalAdjacencyIsPartialOnRight_returnsTrue() {
        Rectangle rectangleA = new Rectangle(0, 0, 40, 25);
        Rectangle rectangleB = new Rectangle(0, 25, 35, 35);

        assertTrue(rectangleA.adjacent(rectangleB));
        assertTrue(rectangleB.adjacent(rectangleA));
    }

    @Test
    public void adjacent_whenRectanglesDoNotHaveAdjacency_returnsFalse() {
        Rectangle rectangleA = new Rectangle(5, 5, 15, 20);
        Rectangle rectangleB = new Rectangle(25, 25, 30, 30);

        assertFalse(rectangleA.adjacent(rectangleB));
        assertFalse(rectangleB.adjacent(rectangleA));
    }

    @Test
    public void adjacent_whenRectanglesDoNotHaveAdjacency_andCommonX_returnsFalse() {
        Rectangle rectangleA = new Rectangle(5, 5, 15, 20);
        Rectangle rectangleB = new Rectangle(15, 25, 20, 30);

        assertFalse(rectangleA.adjacent(rectangleB));
        assertFalse(rectangleB.adjacent(rectangleA));
    }

    @Test
    public void adjacent_whenRectanglesDoNotHaveAdjacency_andCommonY_returnsFalse() {
        Rectangle rectangleA = new Rectangle(5, 5, 10, 20);
        Rectangle rectangleB = new Rectangle(15, 20, 20, 30);

        assertFalse(rectangleA.adjacent(rectangleB));
        assertFalse(rectangleB.adjacent(rectangleA));
    }

    @Test
    public void adjacent_whenRectanglesHaveCommonPoint_andDoNotHaveAdjacency_returnsFalse() {
        Rectangle rectangleA = new Rectangle(5, 5, 10, 20);
        Rectangle rectangleB = new Rectangle(10, 20, 20, 30);

        assertFalse(rectangleA.adjacent(rectangleB));
        assertFalse(rectangleB.adjacent(rectangleA));
    }
}