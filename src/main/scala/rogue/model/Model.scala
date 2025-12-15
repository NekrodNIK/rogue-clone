package rogue.model

import rogue.view.CorridorView.*
import rogue.view.RoomView.*
import rogue.view.TickEntityView.*

import scala.util.Random
case class Point(x: Int, y: Int) {
  def +(other: Point) = Point(x + other.x, y + other.y)
  def -(other: Point) = Point(x - other.x, y - other.y)
}

enum Direction:
  case Up, Left, Right, Down, UpLeft, UpRight, DownLeft, DownRight

class Model {
  private val random              = Random(0)
  private val player: Player      = Player(Point(0, 0), 0, 10, 10, 0, 1, 10, random)
  private val level: Level        = Level(80, 24, random)
  private var _isRunning: Boolean = true
  newLevel()

  def isRunning: Boolean = _isRunning

  def gameOver(): Unit = {
    _isRunning = false
  }

  def movePlayer(direction: Direction): Unit = {
    val newPosition = player.position + (direction match {
      case Direction.Up        => Point(0, -1)
      case Direction.UpRight   => Point(1, -1)
      case Direction.Right     => Point(1, 0)
      case Direction.DownRight => Point(1, 1)
      case Direction.Down      => Point(0, 1)
      case Direction.DownLeft  => Point(-1, 1)
      case Direction.Left      => Point(-1, 0)
      case Direction.UpLeft    => Point(-1, -1)
    })
    val structureO = level.contains(newPosition)
    if structureO.isDefined then {
      val structure = structureO.get
      val hit       = level.monsters.zipWithIndex.filter((m, i) => m.position == newPosition)
      if hit.nonEmpty then {
        hit.foreach((m, i) => {
          m.damage(player.attack())
          if !m.alive then { level.monsters.remove(i); m.unrender }
        })
      } else {
        if level.contains(player.position).get != structure then {
          structure match {
            case r: Room     => r.render
            case c: Corridor => c.render
          }
        }
        level.monsters.filter(m => structure.contains(m.position)).foreach(_.render)
        player.position = newPosition
      }
      player.render
    }
  }

  def descend(): Unit = {
    if level.rooms.filter(_.contains(player.position)).map(_.tiles.exists(t => t.position == player.position)).nonEmpty then newLevel()
  }

  private def newLevel(): Unit = {
    level.regenerate()
    rogue.view.gameField.clear

    player.position = {
      val room = level.rooms(random.nextInt(level.rooms.size))
      room.render
      level.monsters.filter(m => room.contains(m.position)).foreach(_.render)
      Point(random.between(room.shape.topLeft.x, room.shape.bottomRight.x), random.between(room.shape.topLeft.y, room.shape.bottomRight.y))
    }
    player.render
  }
}
