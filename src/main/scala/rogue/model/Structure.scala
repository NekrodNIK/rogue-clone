package rogue.model

import rogue.model.Point

abstract class Structure {
  def contains(point: Point): Boolean
}
