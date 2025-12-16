package rogue.view

import rogue.model

class RenderLevel(player: model.Player, level: model.Level) extends RenderNode {
  val statusWidget = RenderStatusWidget(player)

  def children =
    val statusWidget = RenderStatusWidget(player)
    val rooms = level.rooms.map(RenderRoom(_))
    val corridors = level.corridors.map(RenderCorridor(_))
    val entities = level.monsters.map(RenderTickEntity(_))
    List(statusWidget) ++ rooms ++ corridors ++ entities 

  override def render = statusWidget.render
  override def unrender = gameField.clear
}
