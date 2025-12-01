package rogue.controller
import rogue.Terminal

class Controller(private val terminal: Terminal, private val model: rogue.model.Model) {
  // KeyEvent.LeftArrow.callback = () => model.movePlayerLeft
  // KeyEvent.RightArrow.callback = () => model.movePlayerRight
  // KeyEvent.TopArrow.callback = () => model.movePlayerTop
  // KeyEvent.BottomArrow.callback = () => model.movePlayerBottom
  
  def tick = {
    KeyEventParser.parse(terminal.readKeyboard) match
      case Some(keyEvent) => keyEvent.callback()
      case None => ()
  }
}
