package rogue.model

trait TickEntity extends Entity

class Bat(var position: Point, val id: Int) extends TickEntity {}
