package rogue.view

import rogue.model

class RenderTickEntity(tick: model.TickEntity) extends RenderEntity(tick) {
  override protected def renderThis =
    super.unrenderThis
    super.renderThis
}

class RenderEntity(entity: model.Entity) extends RenderLeaf {
  protected var erased: (model.Point, Symbol) = null
    
  private val symbol =
    entity match
      case gold: model.tiles.Gold => Symbol.Gold
      case exit: model.tiles.Exit => Symbol.Exit
      case bat: model.monsters.Bat => Symbol.Bat
      case player: model.Player => Symbol.Player

  override def reset =
    erased = null

  override protected def renderThis =
    erased = (entity.position, gameField.get(entity.position.x, entity.position.y))
    gameField.set(entity.position.x, entity.position.y, symbol)

  override protected def unrenderThis =
    if erased != null then 
      gameField.set(erased._1.x, erased._1.y, erased._2)
}

