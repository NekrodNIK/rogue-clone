package rogue.view

import rogue.model

class RenderRoom(room: model.Room) extends RenderNode {
  def children =
    val floor = room.shape.innerPoints.toSeq.map(RenderPoint(_, Symbol.RoomInner))
    val tiles = room.tiles.map(RenderEntity(_))
    floor ++ tiles

  private def border =
    val edges = Iterator(
      (room.shape.topEdge, Symbol.TopBottomEdge),
      (room.shape.bottomEdge, Symbol.TopBottomEdge),
      (room.shape.leftEdge, Symbol.LeftRightEdge),
      (room.shape.rightEdge, Symbol.LeftRightEdge)
    ).flatMap((ps, s) => ps.map((_, s)))

    val corners = Iterator(
      (room.shape.topLeftCorner, Symbol.TopLeftCorner),
      (room.shape.topRightCorner, Symbol.TopRightCorner),
      (room.shape.bottomLeftCorner, Symbol.BottomLeftCorner),
      (room.shape.bottomRightCorner, Symbol.BottomRightCorner),
    )

    val doors = room.doors.map((_, Symbol.Door))

    edges ++ corners ++ doors

  override protected def renderThis =
    border.foreach((p, s) => gameField.set(p.x, p.y, s))

  override protected def unrenderThis =
    border.foreach((p, _) => gameField.set(p.x, p.y, Symbol.Empty))
}
