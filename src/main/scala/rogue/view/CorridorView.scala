package rogue.view

import rogue.model
import scala.collection.mutable

object CorridorView {
  private val hidden_map = mutable.Map[model.Corridor, Boolean]().withDefault(_ => true)
  
  extension (corridor: model.Corridor) 
    def render =
      hidden_map(corridor) = false
      corridor.points
        .foreach(p => gameField.set(p.x, p.y, Symbol.Corridor))

    def unrender =
      hidden_map(corridor) = true
      corridor.points
        .foreach(p => gameField.set(p.x, p.y, Symbol.Empty))

    def hidden = hidden_map(corridor)
}
