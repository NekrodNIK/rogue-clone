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
  case Empty             extends Symbol(' ', Color.White)
  case NextLevel         extends Symbol('%', Color.Cyan)
  case TopBottomEdge     extends Symbol('═', Color.Yellow)
  case LeftRightEdge     extends Symbol('║', Color.Yellow)
  case TopLeftCorner     extends Symbol('╔', Color.Yellow)
  case TopRightCorner    extends Symbol('╗', Color.Yellow)
  case BottomLeftCorner  extends Symbol('╚', Color.Yellow)
  case BottomRightCorner extends Symbol('╝', Color.Yellow)
  case Door              extends Symbol('╬', Color.Yellow)
  case RoomInner         extends Symbol('.', Color.Green)
  case Corridor          extends Symbol('█', Color.White)
  case Player            extends Symbol('@', Color.Red)
  case Bat               extends Symbol('B', Color.Red)
}
