package rogue.model

import scala.util.Random

class Player(var position: Point, val id: Int, var max_hp: Int, var hp: Int, var exp: Int, var lvl: Int, var ac: Int, random: Random) extends TickEntity {
  var dmg: Damage = Damage(4, 1, random)
  var gold: Int = 0

  override def attack(): Attack = Attack(dmg, random.nextInt(20))

  override def damage(attack: Attack): Unit = super.damage(attack)
}
