package rogue.view
import rogue.model
import scala.collection.mutable.HashMap
import scala.io.AnsiColor

object TickEntityView {
  val saved_points = HashMap[Int, (model.Point, Symbol)]()

  extension (tickEntity: model.TickEntity) {
    def render = {
      unrender
      
      val symbol = Symbol.Player

      saved_points(tickEntity.id) = (tickEntity.position, gameField.get(tickEntity.position.x, tickEntity.position.y))
      gameField.set(tickEntity.position.x, tickEntity.position.y, symbol)
    }

    def unrender = {
      saved_points.get(tickEntity.id).foreach((p, s) => gameField.set(p.x, p.y, s))
    }
  }
}
