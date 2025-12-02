package model

class Player(var position: Point, val id: Int) extends TickEntity(position, id) {}
