package rogue.view

import rogue.model

class RenderRoom(room: model.Room) extends RenderNode {
  def children =
    (points.map((p, s) => RenderPoint(p, s)) ++ room.tiles.map(_.renderObj)).toSeq

  private def points =
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
      (room.shape.bottomRightCorner, Symbol.BottomRightCorner)
    )

    val floor = room.shape.innerPoints.map((_, Symbol.RoomInner))
    val doors = room.doors.map((_, Symbol.Door))

    floor ++ edges ++ corners ++ doors
}
