package ca.morais.everton.rectangles.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
@Accessors(fluent = true)
public class Rectangle {

    private Point bottomLeft;
    private Point topRight;

    public Rectangle(int leftX, int leftY, int rightX, int rightY) {
        this.bottomLeft = new Point(leftX, leftY);
        this.topRight = new Point(rightX, rightY);
    }

    @Getter
    @ToString
    @EqualsAndHashCode
    @AllArgsConstructor
    @Accessors(fluent = true)
    public static class Point {
        private int x;
        private int y;
    }

    public boolean overlaps(Rectangle other) {
        if (this.topRight.x <= other.bottomLeft.x || this.bottomLeft.x >= other.topRight.x) {
            return false;
        }

        return this.topRight.y > other.bottomLeft.y && this.bottomLeft.y < other.topRight.y;
    }

    public boolean contains(Rectangle other) {
        if (this.equals(other)) {
            return false;
        }
        return (this.bottomLeft.x <= other.bottomLeft.x && this.bottomLeft.y <= other.bottomLeft.y &&
                this.topRight.x >= other.topRight.x && this.topRight.y >= other.topRight.y);
    }

    public boolean adjacent(Rectangle other) {
        return (this.topRight.x == other.bottomLeft.x && (this.containsBottomLeftY(other) || other.containsBottomLeftY(this))) ||
                (this.bottomLeft.x == other.topRight.x && (this.containsTopRightY(other) || other.containsTopRightY(this))) ||
                (this.topRight.y == other.bottomLeft.y && (this.containsBottomLeftX(other) || other.containsBottomLeftX(this))) ||
                (this.bottomLeft.y == other.topRight.y && (this.containsTopRightX(other) || other.containsTopRightX(this)));
    }

    private boolean containsBottomLeftX(Rectangle other) {
        return this.bottomLeft.x <= other.bottomLeft.x && other.bottomLeft.x <= this.topRight.x && !other.bottomLeft.equals(this.topRight);
    }

    private boolean containsBottomLeftY(Rectangle other) {
        return this.bottomLeft.y <= other.bottomLeft.y && other.bottomLeft.y <= this.topRight.y && !other.bottomLeft.equals(this.topRight);
    }

    private boolean containsTopRightX(Rectangle other) {
        return this.topRight.x >= other.topRight.x && other.topRight.x >= this.bottomLeft.x && !other.topRight.equals(this.bottomLeft);
    }

    private boolean containsTopRightY(Rectangle other) {
        return this.topRight.y >= other.topRight.y && other.topRight.y >= this.bottomLeft.y && !other.topRight.equals(this.bottomLeft);
    }
}
