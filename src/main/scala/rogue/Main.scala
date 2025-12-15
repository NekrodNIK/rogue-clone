package rogue

import org.jline.terminal.TerminalBuilder
import java.nio.charset.Charset
import org.jline.utils.InfoCmp.Capability
import rogue.controller.KeyEvent

val terminal =
  TerminalBuilder
    .builder()
    .system(true)
    .encoding(Charset.forName("UTF-8"))
    .build()

@main def main(): Unit =
  terminal.enterRawMode()
  terminal.puts(Capability.enter_ca_mode)
  terminal.puts(Capability.clear_screen)
  terminal.puts(Capability.cursor_address, 0, 0)

  val model = rogue.model.Model()
  val controller = rogue.controller.Controller(terminal.reader.read)

  Iterator(
    (KeyEvent.LeftArrow, () => model.movePlayer(rogue.model.Direction.Left)),
    (KeyEvent.RightArrow, () => model.movePlayer(rogue.model.Direction.Right)),
    (KeyEvent.UpArrow, () => model.movePlayer(rogue.model.Direction.Up)),
    (KeyEvent.DownArrow, () => model.movePlayer(rogue.model.Direction.Down)),
    (KeyEvent.RightAngleBracket, () => model.descend()),
  ).foreach((e,f) => controller.set_callback(e, f))

  while model.isRunning do controller.tick()

  


    
