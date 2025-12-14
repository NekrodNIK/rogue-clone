package rogue.model

class Player(var position: Point, val id: Int, var max_hp: Int, var hp: Int, var exp: Int, var lvl: Int, var ac: Int) extends TickEntity {}
