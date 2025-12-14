package rogue.model

import rogue.model.TickEntity

enum MonsterType:
  case Bat, Zombie

class Monster(val position: Point, val id: Int, monsterType: MonsterType) extends TickEntity {
  var max_hp: Int = 10
  var hp: Int = 10
  var exp: Int = 10
  var lvl: Int = 1
  var ac: Int = 10
}
