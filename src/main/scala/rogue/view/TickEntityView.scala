package rogue.view
import rogue.model
import scala.collection.mutable.HashMap

object TickEntityView {
  private case class Meta (
    pos: model.Point,
    erased_symbol: Symbol,    
    hidden: Boolean = true,
  )
  private val metadata = HashMap[Int, Meta]()

  extension (obj: model.TickEntity) {
    def render = {
      unrender
      
      val symbol = obj match
        case _ : model.Player => Symbol.Player
        case _ : model.Bat => Symbol.Bat
        case _ => Symbol.Door
        
      metadata(obj.id) = Meta(obj.position, gameField.get(obj.position.x, obj.position.y))
      gameField.set(obj.position.x, obj.position.y, symbol)
    }

    def unrender = {
      metadata.get(obj.id).foreach{ case Meta(p, s, _) =>
        gameField.set(p.x, p.y, s)
      }
    }

    def hidden = metadata.get(obj.id).map(_.hidden) match {
      case Some(v) => v
      case None => true
    }
  }
}
