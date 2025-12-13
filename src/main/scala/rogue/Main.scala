package rogue

import org.jline.terminal.TerminalBuilder
import java.nio.charset.Charset
import org.jline.utils.InfoCmp.Capability

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
  val controller = rogue.controller.Controller(model)

  while model.isRunning do controller.tick()

  


    
