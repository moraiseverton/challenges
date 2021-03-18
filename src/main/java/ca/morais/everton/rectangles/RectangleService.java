package ca.morais.everton.rectangles;

import ca.morais.everton.rectangles.exceptions.RectangleIntersectionNotFoundException;

public class RectangleService {

    public Rectangle intersection(Rectangle alpha, Rectangle beta) {
        if (!alpha.overlaps(beta)) {
            throw new RectangleIntersectionNotFoundException(alpha, beta);
        }

        if (overlapHorizontally(alpha, beta)) {
            return new Rectangle(beta.bottomLeft.x, beta.bottomLeft.y, alpha.topRight.x, beta.topRight.y);
        }

        if (overlapHorizontally(beta, alpha)) {
            return new Rectangle(alpha.bottomLeft.x, alpha.bottomLeft.y, beta.topRight.x, alpha.topRight.y);
        }

        if (overlapVertically(alpha, beta)) {
            return new Rectangle(alpha.bottomLeft.x, beta.bottomLeft.y, alpha.topRight.x, alpha.topRight.y);
        }

        if (overlapVertically(beta, alpha)) {
            return new Rectangle(beta.bottomLeft.x, alpha.bottomLeft.y, beta.topRight.x, beta.topRight.y);
        }

        if (overlapOnBottomLeftOrTopRight(alpha, beta)) {
            return new Rectangle(beta.bottomLeft, alpha.topRight);
        }

        if (overlapOnBottomLeftOrTopRight(beta, alpha)) {
            return new Rectangle(alpha.bottomLeft, beta.topRight);
        }

        if (overlapOnTopLeftOrBottomRight(alpha, beta)) {
            return new Rectangle(beta.bottomLeft.x, alpha.bottomLeft.y, alpha.topRight.x, beta.topRight.y);
        }

        if (overlapOnTopLeftOrBottomRight(beta, alpha)) {
            return new Rectangle(alpha.bottomLeft.x, beta.bottomLeft.y, beta.topRight.x, alpha.topRight.y);
        }

        throw new RectangleIntersectionNotFoundException(alpha, beta);
    }

    private boolean overlapOnBottomLeftOrTopRight(Rectangle a, Rectangle b) {
        return (a.bottomLeft.x < b.bottomLeft.x && a.bottomLeft.y < b.bottomLeft.y) &&
                (a.topRight.x > b.bottomLeft.x && a.topRight.y > b.bottomLeft.y);
    }

    private boolean overlapOnTopLeftOrBottomRight(Rectangle a, Rectangle b) {
        return (b.bottomLeft.x > a.bottomLeft.x && b.bottomLeft.y < a.bottomLeft.y) &&
                (b.topRight.x > a.topRight.x && b.topRight.y < a.topRight.y);
    }

    private boolean overlapHorizontally(Rectangle a, Rectangle b) {
        return (a.bottomLeft.x < b.bottomLeft.x && a.bottomLeft.y < b.bottomLeft.y) &&
                (b.topRight.x > a.topRight.x && b.topRight.y < a.topRight.y);
    }

    private boolean overlapVertically(Rectangle a, Rectangle b) {
        return (a.bottomLeft.x > b.bottomLeft.x && a.bottomLeft.y < b.bottomLeft.y) &&
                (b.topRight.x > a.topRight.x && b.topRight.y > a.topRight.y);
    }
}
