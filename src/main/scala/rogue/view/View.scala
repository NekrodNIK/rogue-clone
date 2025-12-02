package rogue.view

import rogue.Terminal
import rogue.model
import scala.collection.mutable.Buffer
import scala.io.AnsiColor
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.HashMap

enum Symbol(val char: Char, val color: String) {
  case TopBottomEdge    extends Symbol('═', AnsiColor.BLINK + AnsiColor.YELLOW)
  case LeftRightEdge   extends Symbol('║', AnsiColor.YELLOW)
  case TopLeftCorner extends Symbol('╔', AnsiColor.YELLOW)
  case TopRightCorner extends Symbol('╗', AnsiColor.YELLOW)
  case BottomLeftCorner extends Symbol('╚', AnsiColor.YELLOW)
  case BottomRightCorner extends Symbol('╝', AnsiColor.YELLOW)
  
  case RoomInner  extends Symbol('.', AnsiColor.GREEN)
  case Corridor   extends Symbol('█', AnsiColor.WHITE)

  case Player extends Symbol('@', AnsiColor.RED)

  def str = color + char
}

class View(private val terminal: Terminal) {
  def height: Int = terminal.height
  def width: Int  = terminal.width

  val entities = HashMap[Int, model.Point]()

  def renderRoom(room: model.Room) = {
    val it = Iterator(
      (room.shape.topEdge ++ room.shape.bottomEdge, Symbol.TopBottomEdge),
      (room.shape.leftEdge ++ room.shape.rightEdge, Symbol.LeftRightEdge),
      (List(room.shape.topLeft), Symbol.TopLeftCorner),
      (List(room.shape.topRight), Symbol.TopRightCorner),
      (List(room.shape.bottomLeft), Symbol.BottomLeftCorner),
      (List(room.shape.bottomRight), Symbol.BottomRightCorner),
      (room.shape.innerPoints, Symbol.RoomInner)
    )

    for
      (points, symbol) <- it
      p                <- points
    do terminal.set(p.x, p.y, symbol.str)
  }

  def renderCorridor(corridor: model.Corridor) = {
    corridor.points
      .foreach(p => terminal.set(p.x, p.y, Symbol.Corridor.str))
  }

  def updateEntityPosition(id: Int, position: model.Point) = {
    entities.get(id) match
      // TODO: more types of restored symbols
      case Some(prev) =>
        terminal.set(position.x, position.y, Symbol.RoomInner.str)
      case None => ()

    val symbol = entity.t match
      case model.TickEntityType.Player => Symbol.Player

    terminal.set(entity.position.x, entity.position.y, symbol.str)
    entities(entity.id) = entity
  }
}
