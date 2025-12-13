package rogue.controller

class Controller(private val model: rogue.model.Model) {
  KeyEvent.LeftArrow.callback = () => model.movePlayer(rogue.model.Direction.Left)
  KeyEvent.RightArrow.callback = () => model.movePlayer(rogue.model.Direction.Right)
  KeyEvent.UpArrow.callback = () => model.movePlayer(rogue.model.Direction.Up)
  KeyEvent.DownArrow.callback = () => model.movePlayer(rogue.model.Direction.Down)
  
  def tick(): Unit = {
    KeyEventParser.parse(rogue.terminal.reader.read) match
      case Some(keyEvent) => keyEvent.callback()
      case None => ()
  }
}
