package rogue.view

import rogue.model

class RenderLevel(level: model.Level) extends RenderNode {
  def children =
    val rooms = level.rooms.map(RenderRoom(_))
    val corridors = level.corridors.map(RenderCorridor(_))
    val entities = level.monsters.map(RenderTickEntity(_))
    rooms ++ corridors  

  override def render = ()
}
