package rogue.model

import rogue.model.Structure

import scala.collection.mutable
import rogue.model.TileEntity
import rogue.view

import scala.collection.mutable.ArrayBuffer

case class Rectangle(topLeft: Point, bottomRight: Point) {
  def topRightCorner: Point = Point(bottomRight.x + 1, topLeft.y - 1)
  def topLeftCorner: Point = Point(topLeft.x - 1, topLeft.y - 1)
  def bottomRightCorner: Point = Point(bottomRight.x + 1, bottomRight.y + 1)
  def bottomLeftCorner: Point = Point(topLeft.x - 1, bottomRight.y + 1)
  
  def topEdge: Iterator[Point] =
    (topLeft.x to bottomRight.x).iterator.map(Point(_, topLeft.y - 1))

  def bottomEdge: Iterator[Point] =
    (topLeft.x to bottomRight.x).iterator.map(Point(_, bottomRight.y + 1))

  def leftEdge: Iterator[Point] =
    (topLeft.y to bottomRight.y).iterator.map(Point(topLeft.x - 1, _))

  def rightEdge: Iterator[Point] =
    (topLeft.y to bottomRight.y).iterator.map(Point(bottomRight.x + 1, _))

  def innerPoints: Iterator[Point] =
    for
      x <- (topLeft.x to bottomRight.x).iterator
      y <- (topLeft.y to bottomRight.y).iterator
    yield Point(x, y)
}

case class Room(shape: Rectangle) extends Structure, view.Renderable {
  override val renderObj = view.RenderRoom(this)
  
  val doors: mutable.ArrayBuffer[Point] = ArrayBuffer.empty
//  var tiles: mutable.ArrayBuffer[TileEntity] = ArrayBuffer.empty
  
  def contains(point: Point): Boolean =
    (shape.topLeft.x <= point.x && point.x <= shape.bottomRight.x 
      && shape.topLeft.y <= point.y && point.y <= shape.bottomRight.y)
      || doors.contains(point)
}

object Room {
  val empty: Room = Room(Rectangle(Point(0, 0), Point(0, 0)))
}
