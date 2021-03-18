package ca.morais.everton.rectangles.services;

import ca.morais.everton.rectangles.domain.Rectangle;
import ca.morais.everton.rectangles.exceptions.RectangleIntersectionNotFoundException;
import ca.morais.everton.rectangles.services.RectangleService;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RectangleServiceTest {

    private RectangleService service = new RectangleService();

    @Test
    public void intersection_whenRectanglesOverlapHorizontallyOnTop_returnsOverlappedRectangleArea() {
        Rectangle rectangleA = new Rectangle(5, 5, 20, 25);
        Rectangle rectangleB = new Rectangle(15, 10, 25, 20);

        Rectangle expected = new Rectangle(15, 10, 20, 20);
        Rectangle resulted = service.intersection(rectangleA, rectangleB);

        assertThat(resulted, is(expected));
    }

    @Test
    public void intersection_whenRectanglesOverlapHorizontallyOnBottom_returnsOverlappedRectangleArea() {
        Rectangle rectangleA = new Rectangle(15, 10, 25, 20);
        Rectangle rectangleB = new Rectangle(5, 5, 20, 25);

        Rectangle expected = new Rectangle(15, 10, 20, 20);
        Rectangle resulted = service.intersection(rectangleA, rectangleB);

        assertThat(resulted, is(expected));
    }

    @Test
    public void intersection_whenRectanglesOverlapVerticallyOnLeft_returnsOverlappedRectangleArea() {
        Rectangle rectangleA = new Rectangle(5, 15, 25, 25);
        Rectangle rectangleB = new Rectangle(10, 5, 20, 20);

        Rectangle expected = new Rectangle(10, 15, 20, 20);
        Rectangle resulted = service.intersection(rectangleA, rectangleB);

        assertThat(expected, is(resulted));
    }

    @Test
    public void intersection_whenRectanglesOverlapVerticallyOnRight_returnsOverlappedRectangleArea() {
        Rectangle rectangleA = new Rectangle(10, 5, 20, 20);
        Rectangle rectangleB = new Rectangle(5, 15, 25, 25);

        Rectangle expected = new Rectangle(10, 15, 20, 20);
        Rectangle resulted = service.intersection(rectangleA, rectangleB);

        assertThat(expected, is(resulted));
    }

    @Test
    public void intersection_whenRectanglesOverlapOnBottomLeftOrTopRight_returnsOverlappedRectangleArea() {
        Rectangle rectangleA = new Rectangle(0, 0, 10, 20);
        Rectangle rectangleB = new Rectangle(5, 15, 30, 20);

        Rectangle expected = new Rectangle(5, 15, 10, 20);
        Rectangle resulted = service.intersection(rectangleA, rectangleB);

        assertThat(expected, is(resulted));
    }

    @Test
    public void intersection_whenRectanglesOverlapOnTopRight_returnsOverlappedRectangleArea() {
        Rectangle rectangleA = new Rectangle(5, 15, 30, 20);
        Rectangle rectangleB = new Rectangle(0, 0, 10, 20);

        Rectangle expected = new Rectangle(5, 15, 10, 20);
        Rectangle resulted = service.intersection(rectangleA, rectangleB);

        assertThat(expected, is(resulted));
    }

    @Test
    public void intersection_whenRectanglesOverlapOnBottomRight_returnsOverlappedRectangleArea() {
        Rectangle rectangleA = new Rectangle(15, 5, 30, 15);
        Rectangle rectangleB = new Rectangle(5, 10, 20, 20);

        Rectangle expected = new Rectangle(15, 10, 20, 15);
        Rectangle resulted = service.intersection(rectangleA, rectangleB);

        assertThat(expected, is(resulted));
    }

    @Test
    public void intersection_whenRectanglesOverlapOnTopLeft_returnsOverlappedRectangleArea() {
        Rectangle rectangleA = new Rectangle(5, 10, 20, 20);
        Rectangle rectangleB = new Rectangle(15, 5, 30, 15);

        Rectangle expected = new Rectangle(15, 10, 20, 15);
        Rectangle resulted = service.intersection(rectangleA, rectangleB);

        assertThat(expected, is(resulted));
    }

    @Test
    public void intersection_whenRectanglesDoNotOverlap_returnsNull() {
        Rectangle rectangleA = new Rectangle(5, 5, 10, 20);
        Rectangle rectangleB = new Rectangle(10, 25, 20, 30);

        Exception exception = assertThrows(RectangleIntersectionNotFoundException.class, () ->
                service.intersection(rectangleA, rectangleB)
        );

        String resultedMessage = exception.getMessage();

        assertThat(resultedMessage, containsString("Rectangle's intersection not found."));
        assertThat(resultedMessage, containsString("alpha: " + rectangleA.toString()));
        assertThat(resultedMessage, containsString("beta: " + rectangleB.toString()));
    }

    @Test
    public void intersection_whenRectanglesDoNotOverlap_andHaveAnAdjacentPoint_returnsNull() {
        Rectangle rectangleA = new Rectangle(5, 5, 10, 20);
        Rectangle rectangleB = new Rectangle(10, 20, 20, 30);

        Exception exception = assertThrows(RectangleIntersectionNotFoundException.class, () ->
                service.intersection(rectangleA, rectangleB)
        );

        String resultedMessage = exception.getMessage();

        assertThat(resultedMessage, containsString("Rectangle's intersection not found."));
        assertThat(resultedMessage, containsString("alpha: " + rectangleA.toString()));
        assertThat(resultedMessage, containsString("beta: " + rectangleB.toString()));
    }
}