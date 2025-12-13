package rogue.view

import org.jline.terminal.Terminal
import org.jline.terminal.TerminalBuilder
import org.jline.utils.InfoCmp.Capability
import java.nio.charset.Charset
import scala.collection.ArrayOps.ArrayView

class GameField {
  private val field = Array.ofDim[Symbol](height, width)

  def set(x: Int, y: Int, s: Symbol) = {
    field(y)(x) = s
    rogue.terminal.puts(Capability.cursor_address, y, x)
    rogue.terminal.writer.write(s.color.ansi_escape)
    rogue.terminal.writer.write(s.char)
    rogue.terminal.writer.write(Color.White.ansi_escape)
    rogue.terminal.puts(Capability.cursor_address, y, x)
    rogue.terminal.flush
  }

  def get(x: Int, y: Int): Symbol = field(y)(x)

  def clear = {
    rogue.terminal.puts(Capability.clear_screen)
    rogue.terminal.flush
  }

  def width = rogue.terminal.getWidth
  def height = rogue.terminal.getHeight
}

val gameField = new GameField()
