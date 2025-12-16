package rogue.model.monsters

import rogue.model.*

import scala.util.Random

trait Monster(val max_hp: Int, val dmg: Damage, val ac: Int, val lvl: Int, val exp: Int) extends TickEntity {
  def random: Random
  var hp: Int = max_hp
  var position: Point

  override def attack(): Attack = Attack(dmg, random.nextInt(20) + 1)
}

case class Bat(
                var position: Point,
                override val id: Int,
                random: Random)
  extends Monster(random.nextInt(8), Damage(4, 1, random), 7, 0, 15) {
}