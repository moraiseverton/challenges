# Challenge: The Rectangle

## Usage

### Dependencies

- Java 11
- Maven
  * Lombok - to avoid boilerplate

### Running Unit Tests

```
mvn test
```

### Features

#### `Rectangle`
A representation of the rectangle. 
It contains the start point `bottomLeft` and `topRight` as the end point of the shape.

##### `Rectangle.Point`
A static inner class that represents coordinates points: *x* and *y*.

##### `overlaps(Rectangle other)`
Returns true if has any intersection line. 

```$java
Rectangle alpha = new Rectangle(0, 0, 10, 20);
Rectangle beta = new Rectangle(5, 15, 30, 20);

rectangleA.overlaps(rectangleB); // true
```

##### `contains(Rectangle other)`
Returns true when `other` is fully contained in the main rectangle area. 

```$java
Rectangle alpha = new Rectangle(0, 0, 30, 20);
Rectangle beta = new Rectangle(5, 5, 25, 15);

alpha.contains(beta); // true
beta.contains(alpha); // false
```

##### `adjacent(Rectangle other)`
Returns true if both rectangles share one side, fully or partially.

```$java
Rectangle alpha = new Rectangle(40, 0, 60, 25);
Rectangle beta = new Rectangle(0, 5, 40, 25);

alpha.adjacent(beta);
```

#### `RectangleService`

Provides services related to `Rectangle` domain class.

##### `intersection(Rectangle alpha, Rectangle beta)`
- Returns the intersection of two rectangles in a brand new one;
- In case of no intersection, returns `RectangleIntersectionNotFoundException` exception.

```$java
Rectangle alpha = new Rectangle(5, 5, 20, 25);
Rectangle beta = new Rectangle(15, 10, 25, 20);

Rectangle intersection = service.intersection(alpha, beta); // bottomLeft {15, 10}, topRight {25, 20}
```
