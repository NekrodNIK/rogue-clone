package model

case class Rectangle(topLeft: Point, bottomRight: Point) {
  def topRight = Point(bottomRight.x, topLeft.y)

  def bottomLeft = Point(topLeft.x, bottomRight.y)

  def topEdge: Iterator[Point] =
    (topLeft.x + 1 until bottomRight.x).iterator.map(Point(_, topLeft.y))

  def bottomEdge: Iterator[Point] =
    (topLeft.x + 1 until bottomRight.x).iterator.map(Point(_, bottomRight.y))

  def leftEdge: Iterator[Point] =
    (topLeft.y + 1 until bottomRight.y).iterator.map(Point(topLeft.x, _))

  def rightEdge: Iterator[Point] =
    (topLeft.y + 1 until bottomRight.y).iterator.map(Point(bottomRight.x, _))

  def innerPoints: Iterator[Point] =
    for
      x <- (topLeft.x + 1 until bottomRight.x).iterator
      y <- (topLeft.y + 1 until bottomRight.y).iterator
    yield Point(x, y)
}

case class Room(shape: Rectangle) {
  def contains(point: Point): Boolean =
    shape.topLeft.x <= point.x && point.x <= shape.bottomRight.x 
      && shape.topLeft.y <= point.y && point.y <= shape.bottomRight.y
}

object Room {
  val empty: Room = Room(Rectangle(Point(0, 0), Point(0, 0)))
}