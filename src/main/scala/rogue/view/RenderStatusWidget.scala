package rogue.view

import rogue.model
import scala.collection.mutable
import rogue.terminal

class RenderStatusWidget(player: model.Player) extends RenderNode {
  override def children =
    val str = s"GOLD: ${player.gold}, HP: ${player.hp}".zipWithIndex
    str.map((d, i) => RenderPoint(model.Point(i, 0), Symbol.Any(d)))

  override def render =
    super.unrender
    super.render
}
