package rogue.view

import rogue.model
import scala.collection.mutable

class RenderCorridor(corridor: model.Corridor) extends RenderNode {
  val children = corridor.points.map(RenderPoint(_, Symbol.Corridor))
}
