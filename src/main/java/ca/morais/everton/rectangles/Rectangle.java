package ca.morais.everton.rectangles;

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
        return (this.topRight.x == other.bottomLeft.x && (topRightYPartOfBottomLeft(this, other) || topRightYPartOfBottomLeft(other, this))) ||
                (this.bottomLeft.x == other.topRight.x && (bottomLeftYPartOfTopRight(this, other) || bottomLeftYPartOfTopRight(other, this))) ||
                (this.topRight.y == other.bottomLeft.y && (topRightXPartOfBottomLeft(this, other) || topRightXPartOfBottomLeft(other, this))) ||
                (this.bottomLeft.y == other.topRight.y && (bottomLeftXPartOfTopRight(this, other) || bottomLeftXPartOfTopRight(other, this)));
    }

    private boolean topRightXPartOfBottomLeft(Rectangle a, Rectangle other) {
        return other.bottomLeft.x >= a.bottomLeft.x && other.bottomLeft.x <= a.topRight.x && !other.bottomLeft.equals(a.topRight);
    }

    private boolean topRightYPartOfBottomLeft(Rectangle a, Rectangle other) {
        return other.bottomLeft.y >= a.bottomLeft.y && other.bottomLeft.y <= a.topRight.y && !other.bottomLeft.equals(a.topRight);
    }

    private boolean bottomLeftXPartOfTopRight(Rectangle a, Rectangle other) {
        return other.topRight.x <= a.topRight.x && other.topRight.x >= a.bottomLeft.x && !other.topRight.equals(a.bottomLeft);
    }

    private boolean bottomLeftYPartOfTopRight(Rectangle a, Rectangle other) {
        return other.topRight.y <= a.topRight.y && other.topRight.y >= a.bottomLeft.y && !other.topRight.equals(a.bottomLeft);
    }
}
