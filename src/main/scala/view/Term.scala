package rogue.view

import org.jline

class Term(private val jlineTerm: jline.terminal.Terminal):  
  def putString(x: Int, y: Int, str: String) =
    jlineTerm.puts(jline.utils.InfoCmp.Capability.cursor_address, x, y)
    jlineTerm.writer().write(str)
    jlineTerm.flush()

  def putChar(x: Int, y: Int, char: Char) =
    putString(x, y, char.toString)
  
object Term:
  def setup: Option[Term] =
    val jlineTerm =
      jline.terminal.TerminalBuilder
        .builder()
        .system(true)
        .build()

    val ca_supported =
      jlineTerm.puts(jline.utils.InfoCmp.Capability.enter_ca_mode)
    val cursor_addr_supported =
      jlineTerm.puts(jline.utils.InfoCmp.Capability.cursor_address, 0, 0)
    val clear_supported =
      jlineTerm.puts(jline.utils.InfoCmp.Capability.clr_eos)
    jlineTerm.flush()

    if ca_supported && cursor_addr_supported
    then Some(Term(jlineTerm))
    else None
