package rogue.view

import rogue.model

object CorridorView {
  extension (corridor: model.Corridor) 
    def render =
      corridor.points
        .foreach(p => gameField.set(p.x, p.y, Symbol.Corridor))

    def unrender =
      corridor.points
        .foreach(p => gameField.set(p.x, p.y, Symbol.Empty))
}
