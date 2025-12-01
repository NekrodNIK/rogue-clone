package rogue

import org.jline.terminal.Terminal as JlineTerminal
import org.jline.terminal.TerminalBuilder as JlineTerminalBuilder
import org.jline.utils.InfoCmp.Capability
import java.nio.charset.Charset

class Terminal(private val jlineTerminal: JlineTerminal):
  def height = jlineTerminal.getHeight
  def width = jlineTerminal.getWidth
  
  def set(x: Int, y: Int, str: String) = {
    jlineTerminal.puts(Capability.cursor_address, y, x)
    jlineTerminal.writer.write(str)
    jlineTerminal.puts(Capability.cursor_address, y, x)
    jlineTerminal.flush
  }
    
  def clear = {
    jlineTerminal.puts(Capability.clear_screen)
    jlineTerminal.flush
  }

  def readKeyboard = jlineTerminal.reader.read

  def close = jlineTerminal.close

object Terminal:
  def open: Option[Terminal] = {
    val jlineTerminal = JlineTerminalBuilder
      .builder()
      .system(true)
      .encoding(Charset.forName("UTF-8"))
      .build()

    jlineTerminal.enterRawMode()
    val ca_mode_supported = jlineTerminal.puts(Capability.enter_ca_mode)
    val clear_screen_supported = jlineTerminal.puts(Capability.clear_screen)
    val cursor_address_supported = jlineTerminal.puts(Capability.cursor_address, 0, 0)

    if ca_mode_supported && clear_screen_supported && cursor_address_supported
    then Some(new Terminal(jlineTerminal))
    else None
  }
    
