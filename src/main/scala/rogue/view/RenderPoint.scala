package rogue.view

import rogue.model

class RenderPoint(point: model.Point, symbol: Symbol) extends RenderLeaf {
  override protected def renderThis =
    gameField.set(point.x, point.y, symbol)

  override protected def unrenderThis =
    gameField.set(point.x, point.y, Symbol.Empty)
}
