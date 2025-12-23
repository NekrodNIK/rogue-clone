package rogue.model

import rogue.model.Point

import scala.collection.mutable.ArrayBuffer

trait Structure {
  def contains(point: Point): Boolean
  var tiles: ArrayBuffer[TileEntity] = ArrayBuffer.empty
}
