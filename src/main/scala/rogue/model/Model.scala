package rogue.model

import scala.util.Random

case class Point(x: Int, y: Int) {
  def +(other: Point) = Point(x + other.x, y + other.y)
  def -(other: Point) = Point(x - other.x, y - other.y)
}

enum Direction:
  case Up, Left, Right, Down, UpLeft, UpRight, DownLeft, DownRight

case class Corridor(points: Array[Point])

class Model(private val view: rogue.view.View) {
  private val random = Random()
  private var _isRunning: Boolean = true
  private val player: Player = Player(Point(0, 0), 0)

  private val level: Level = Level(80, 24)
  level.regenerate()
  player.position = {
    val room = level.rooms(random.nextInt(level.rooms.size))
    Point(random.between(room.shape.topLeft.x, room.shape.bottomRight.x),
      random.between(room.shape.topLeft.y, room.shape.bottomRight.y))
  }
  view.renderRoom(level.rooms(0))
  view.updateEntityPosition(0, player.position)
  
  def isRunning: Boolean = _isRunning
  
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
    if level.contains(newPosition) || true then {
      player.position = newPosition
      view.updateEntityPosition(player.id, newPosition)
    }
  }
}
