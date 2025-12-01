package rogue.model

case class Point(x: Int, y: Int)

case class Square(topLeft: Point, bottomRight: Point) {
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
      x <- (topLeft.x + 1 to bottomRight.x - 1).iterator
      y <- (topLeft.y + 1 to bottomRight.y - 1).iterator
    yield Point(x, y)
}

case class Room(shape: Square)
case class Corridor(points: Array[Point])

case class Level(rooms: Array[Room], corridors: Array[Corridor])

class Model {
  def movePlayerLeft: Boolean = ???
  def movePlayerRight: Boolean = ???
  def movePlayerTop: Boolean = ???
  def movePlayerBottom: Boolean = ???
}

enum EntityType:
  case Player, Bat

case class Entity(id: Int, t: EntityType, position: Point)
