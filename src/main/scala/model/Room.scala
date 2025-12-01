package model

import model.Point

case class Room(topleft: Point, botright: Point) {
  def contains(point: Point): Boolean =
    topleft.x <= point.x && point.x <= botright.x && topleft.y <= point.y && point.y <= botright.y
}
