package controller

import rogue.Terminal

class Controller(private val terminal: Terminal, private val model: rogue.model.Model) {
  KeyEvent.LeftArrow.callback = () => model.movePlayer(model.Direction.Left)
  KeyEvent.RightArrow.callback = () => model.movePlayer(model.Direction.Right)
  KeyEvent.UpArrow.callback = () => model.movePlayer(model.Direction.Up)
  KeyEvent.DownArrow.callback = () => model.movePlayer(model.Direction.Down)
  
  def tick = {
    KeyEventParser.parse(terminal.readKeyboard) match
      case Some(keyEvent) => keyEvent.callback()
      case None => ()
  }
}
