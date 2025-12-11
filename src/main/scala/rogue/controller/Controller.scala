package rogue.controller

import rogue.Terminal

class Controller(private val terminal: Terminal, private val model: rogue.model.Model) {
  KeyEvent.LeftArrow.callback = () => model.movePlayer(rogue.model.Direction.Left)
  KeyEvent.RightArrow.callback = () => model.movePlayer(rogue.model.Direction.Right)
  KeyEvent.UpArrow.callback = () => model.movePlayer(rogue.model.Direction.Up)
  KeyEvent.DownArrow.callback = () => model.movePlayer(rogue.model.Direction.Down)
  
  def tick(): Unit = {
    KeyEventParser.parse(terminal.readKeyboard) match
      case Some(keyEvent) => keyEvent.callback()
      case None => ()
  }
}
