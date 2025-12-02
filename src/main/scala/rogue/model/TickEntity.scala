package rogue.model

enum TickEntityType:
  case Player, Bat

abstract class TickEntity(val position: Point, val id: Int) extends Entity(position, id)
