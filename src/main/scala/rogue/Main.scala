package rogue

import controller.{Controller, KeyEvent}
import model.Point
import rogue.model
import rogue.view.{controller, model}

@main def main =
  val terminal = Terminal.open match
    case Some(terminal) => terminal
    case None => sys.error("Unable to initialize terminal")

  val view = rogue.view.View(terminal)
  val controller = Controller(terminal, model.Model())
  
  view.renderRoom(model.Room(model.Rectangle(Point(10, 10), model.Point(20, 21))))
  view.renderCorridor(model.Corridor(
    Array(
      model.Point(21, 20),
      model.Point(22, 20),
      model.Point(23, 20),
      model.Point(24, 20),
      model.Point(24, 21),
      model.Point(24, 22)
    )
  ))
  view.renderRoom(model.Room(model.Rectangle(model.Point(19, 23), model.Point(30, 30))))

  var player = model.Entity(0, model.TickEntityType.Player, model.Point(11, 11))
  view.updateEntity(player)
  
  KeyEvent.LeftArrow.callback = () => {
    player = player.copy(position = model.Point(Math.max(0, player.position.x-1), player.position.y))
    view.updateEntity(player)
  }
  controller.KeyEvent.RightArrow.callback = () => {
    player = player.copy(position = model.Point(Math.min(view.width, player.position.x+1), player.position.y))
    view.updateEntity(player)
  }
  controller.KeyEvent.UpArrow.callback = () => {
    player = player.copy(position = model.Point(player.position.x, Math.min(view.height, player.position.y+1)))
    view.updateEntity(player)
  }
  controller.KeyEvent.DownArrow.callback = () => {
    player = player.copy(position = model.Point(player.position.x, Math.max(0, player.position.y-1)))
    view.updateEntity(player)
  }

  while true do controller.tick

  


    
