package rogue.view

import rogue.model

object RoomView {
  extension (room: model.Room)
    private def symbols = Iterator(
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
      (room.nextLevelExit, Symbol.NextLevel)
    ).flatMap((ps, s) => ps.map((_, s)))

    def render: Unit =
      symbols.foreach((p, s) => gameField.set(p.x, p.y, s))
  
    def unrender: Unit =
      symbols.foreach((p, _) => gameField.set(p.x, p.y, Symbol.Empty))
}
    
