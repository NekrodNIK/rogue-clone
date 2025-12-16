package rogue.view

import rogue.model
import scala.collection.mutable.Buffer
import scala.io.AnsiColor
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.HashMap

enum Color {
  case White, Red, Green, Blue, Cyan, Yellow

  def ansi_escape: String =
    this match
      case White  => AnsiColor.WHITE
      case Red    => AnsiColor.RED
      case Green  => AnsiColor.GREEN
      case Blue   => AnsiColor.BLUE
      case Yellow => AnsiColor.YELLOW
      case Cyan   => AnsiColor.CYAN
}

enum Symbol(val char: Char, val color: Color) {
  case Empty                 extends Symbol(' ', Color.White)
  case TopBottomEdge         extends Symbol('═', Color.Yellow)
  case LeftRightEdge         extends Symbol('║', Color.Yellow)
  case TopLeftCorner         extends Symbol('╔', Color.Yellow)
  case TopRightCorner        extends Symbol('╗', Color.Yellow)
  case BottomLeftCorner      extends Symbol('╚', Color.Yellow)
  case BottomRightCorner     extends Symbol('╝', Color.Yellow)
  case Door                  extends Symbol('╬', Color.Yellow)
  case RoomInner             extends Symbol('.', Color.Green)
  case Corridor              extends Symbol('█', Color.White)
  case Player                extends Symbol('@', Color.Red)
  case Bat                   extends Symbol('B', Color.Red)
  case Gold                  extends Symbol('₽', Color.Yellow)
  case Exit                  extends Symbol('%', Color.Cyan)
  case Any(override val char: Char)   extends Symbol(char, Color.White)
}

trait Renderable {
  def renderObj: RenderNode
}

trait RenderNode {
  def children: Iterable[RenderNode]
  protected def renderThis: Unit   = ()
  protected def unrenderThis: Unit = ()

  def rendered: Boolean = _rendered
  private var _rendered = false

  def reset: Unit = ()

  def render: Unit = {
    _rendered = true
    renderThis
    children.foreach(_.render)
  }

  def unrender: Unit = {
    _rendered = false
    unrenderThis
    children.foreach(_.unrender)
  }
}

trait RenderLeaf extends RenderNode {
  override def children = Nil
}
