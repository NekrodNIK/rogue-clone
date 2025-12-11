package rogue

@main def main(): Unit =
  val terminal = Terminal.open match
    case Some(terminal) => terminal
    case None => sys.error("Unable to initialize terminal")

  val view = rogue.view.View(terminal)
  val model = rogue.model.Model(view)
  val controller = rogue.controller.Controller(terminal, model)

  while model.isRunning do controller.tick()

  


    
