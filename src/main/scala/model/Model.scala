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
package model

import scala.util.Random

case class Point(x: Int, y: Int) {
  def +(other: Point) = Point(this.x + other.x, this.y + other.y)
  def -(other: Point) = Point(this.x - other.x, this.y - other.y)
}

enum Direction:
  case Up, Left, Right, Down, UpLeft, UpRight, DownLeft, DownRight
case class Room(shape: Square)
case class Corridor(points: Array[Point])

case class Level(rooms: Array[Room], corridors: Array[Corridor])

class Model {
  private var isRunning: Boolean = true
  private val player: Player = Player(Point(0, 0))
  private val level: Level = Level(80, 24)

  def run(): Unit = {
    val random = Random()
    this.level.regenerate()
    this.player.position = {
      val room = level.rooms(random.nextInt(level.rooms.size))
      Point(random.between(room.topleft.x, room.botright.x),
        random.between(room.topleft.y, room.botright.y))
    }
  def movePlayerLeft: Boolean = ???
  def movePlayerRight: Boolean = ???
  def movePlayerTop: Boolean = ???
  def movePlayerBottom: Boolean = ???
}

    while (this.isRunning) {
      // controller.tick
    }
  }
enum EntityType:
  case Player, Bat

  def movePlayer(direction: Direction): Unit = {
    val newPosition = player.position + (direction match {
      case Direction.Up => Point(0, -1)
      case Direction.UpRight => Point(1, -1)
      case Direction.Right => Point(1, 0)
      case Direction.DownRight => Point(1, 1)
      case Direction.Down => Point(0, 1)
      case Direction.DownLeft => Point(-1, 1)
      case Direction.Left => Point(-1, 0)
      case Direction.UpLeft => Point(-1, -1)
    })
    if level.contains(newPosition) then player.position = newPosition
  }
}
case class Entity(id: Int, t: EntityType, position: Point)
