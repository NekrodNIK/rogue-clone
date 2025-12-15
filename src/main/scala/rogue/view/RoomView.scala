package rogue.view

import rogue.model
import scala.collection.mutable

object RoomView {
  private val hidden_map = mutable.Map[model.Room, Boolean]().withDefault(_ => true)
  
  extension (room: model.Room)
    private def symbols = (Iterator(
      (room.shape.topEdge, Symbol.TopBottomEdge),
      (room.shape.bottomEdge, Symbol.TopBottomEdge),
      (room.shape.leftEdge, Symbol.LeftRightEdge),
      (room.shape.rightEdge, Symbol.LeftRightEdge),
      (List(room.shape.topLeftCorner), Symbol.TopLeftCorner),
      (List(room.shape.topRightCorner), Symbol.TopRightCorner),
      (List(room.shape.bottomLeftCorner), Symbol.BottomLeftCorner),
      (List(room.shape.bottomRightCorner), Symbol.BottomRightCorner),
      (room.doors, Symbol.Door),
      (room.shape.innerPoints, Symbol.RoomInner),
      (room.nextLevelExit, Symbol.NextLevel),
    ) ++ room.tiles.map {
      case g: model.tiles.Gold => (List(g.position), Symbol.Gold)
      case e: model.tiles.Exit => (List(e.position), Symbol.Exit)
    }).flatMap((ps, s) => ps.map((_, s)))
        
    def render =
      hidden_map(room) = false
      symbols.foreach((p, s) => gameField.set(p.x, p.y, s))
  
    def unrender =
      hidden_map(room) = true
      symbols.foreach((p, _) => gameField.set(p.x, p.y, Symbol.Empty))

    def hidden: Boolean = hidden_map(room)
}
    
