package rogue.controller

import scala.collection.mutable

class Controller(read: () => Int) {
  private val callbacks = mutable.HashMap[KeyEvent, () => Unit]().withDefaultValue(() => {})

  def set_callback(event: KeyEvent, f: () => Unit) = {
    callbacks(event) = f
  }

  def tick(): Unit = {
    KeyEventParser.parse(read()) match
      case Some(keyEvent) => callbacks(keyEvent)()
      case None           => ()
  }
}
