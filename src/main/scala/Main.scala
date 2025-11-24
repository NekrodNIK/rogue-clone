import rogue.view

@main def main(): Unit =
  val term =
    view.Term.setup match
      case Some(term) => term
      case None       => return

  term.putString(10, 10, "Hello, rogue!")
  while true do ()
