package ca.morais.everton.rectangles;

import java.util.Objects;

public class Rectangle {

    Point bottomLeft;
    Point topRight;

    public Rectangle(int leftX, int leftY, int rightX, int rightY) {
        this.bottomLeft = new Point(leftX, leftY);
        this.topRight = new Point(rightX, rightY);
    }

    public Rectangle(Point bottomLeft, Point topRight) {
        this.bottomLeft = bottomLeft;
        this.topRight = topRight;
    }

    public static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Point point = (Point) o;
            return x == point.x &&
                    y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    boolean overlaps(Rectangle other) {
        if (this.topRight.x <= other.bottomLeft.x || this.bottomLeft.x >= other.topRight.x) {
            return false;
        }

        if (this.topRight.y <= other.bottomLeft.y || this.bottomLeft.y >= other.topRight.y) {
            return false;
        }

        return true;
    }

    boolean contains(Rectangle other) {
        if (this.equals(other)) {
            return false;
        }
        return (this.bottomLeft.x <= other.bottomLeft.x && this.bottomLeft.y <= other.bottomLeft.y &&
                this.topRight.x >= other.topRight.x && this.topRight.y >= other.topRight.y);
    }

    boolean adjacent(Rectangle other) {
        return (this.topRight.x == other.bottomLeft.x) || (this.bottomLeft.x == other.topRight.x) ||
                (this.topRight.y == other.bottomLeft.y) || (this.bottomLeft.y == other.topRight.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Rectangle rectangle = (Rectangle) o;
        return Objects.equals(bottomLeft, rectangle.bottomLeft) &&
                Objects.equals(topRight, rectangle.topRight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bottomLeft, topRight);
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "bottomLeft=" + bottomLeft +
                ", topRight=" + topRight +
                '}';
    }
}
