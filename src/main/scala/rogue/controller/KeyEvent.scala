package rogue.controller

enum KeyEvent(var callback: () => Unit = () => {}) {
  case RightArrow, LeftArrow, DownArrow, UpArrow
}

object KeyEventParser {
  private var prev0 = 0
  private var prev1 = 0

  def parse(code: Int): Option[KeyEvent] = {
    val result = (prev0, prev1, code) match
      case (0x1b, '[' | 'O', 'C') => Some(KeyEvent.RightArrow)
      case (0x1b, '[' | 'O', 'D') => Some(KeyEvent.LeftArrow)
      case (0x1b, '[' | 'O', 'B') => Some(KeyEvent.DownArrow)
      case (0x1b, '[' | 'O', 'A') => Some(KeyEvent.UpArrow)
      case _ => None

    prev0 = prev1
    prev1 = code
    result
  }
}
