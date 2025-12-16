package rogue.model

import rogue.model.tiles.*

import scala.util.Random
import scala.collection.mutable.ArrayBuffer
case class Point(x: Int, y: Int) {
  def +(other: Point) = Point(x + other.x, y + other.y)
  def -(other: Point) = Point(x - other.x, y - other.y)
}

enum Direction:
  case Up, Left, Right, Down, UpLeft, UpRight, DownLeft, DownRight

class Model {
  private val random              = Random(1)
  private val player: Player      = Player(Point(0, 0), 0, 10, 10, 0, 1, 10, random)
  private val level: Level        = Level(player, 80, 24, random)
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
          if !m.alive then { level.monsters.remove(i); m.renderObj.unrender }
        })
      } else {
        if level.contains(player.position).get != structure then {
          structure match {
            case r: Room     => r.renderObj.render
            case c: Corridor => c.renderObj.render
          }
        }

        val shouldRemove = ArrayBuffer[Int]()
        structure.tiles
          .zipWithIndex
          .filter(_._1.isInstanceOf[Gold])
          .map((t, i) => (t.asInstanceOf[Gold], i))
          .filter((g, i) => g.position == newPosition)
          .foreach((g, i) => {
            player.gold += g.amount
            shouldRemove += i
            g.renderObj.unrender
            level.renderObj.statusWidget.unrender
            level.renderObj.statusWidget.render
          })
        shouldRemove.foreach(structure.tiles.remove(_))
        level.monsters.filter(m => structure.contains(m.position)).foreach(_.renderObj.render)
        player.position = newPosition
      }
      player.renderObj.render
    }
  }

  def descend(): Unit = {
    if level.rooms.filter(_.contains(player.position)).exists(_.tiles.collect({ case e: Exit => e }).exists(t => t.position == player.position)) then newLevel()
  }

  private def newLevel(): Unit = {
    level.renderObj.unrender
    player.renderObj.reset
    player.renderObj.unrender

    level.regenerate()
    level.renderObj.render

    player.position = {
      val room = level.rooms(random.nextInt(level.rooms.size))
      room.renderObj.render
      level.monsters.filter(m => room.contains(m.position)).foreach(_.renderObj.render)
      Point(random.between(room.shape.topLeft.x, room.shape.bottomRight.x), random.between(room.shape.topLeft.y, room.shape.bottomRight.y))
    }

    player.renderObj.render
  }
}
