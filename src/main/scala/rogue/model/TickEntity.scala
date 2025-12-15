package rogue.model

trait TickEntity extends Entity {
  var hp: Int
  def max_hp: Int
  def exp: Int
  def lvl: Int
  def ac: Int
  def dmg: Damage

  def alive: Boolean = hp > 0

  def attack(): Attack

  def damage(attack: Attack): Unit = {
    val damage = if attack.hit >= ac then attack.damage.roll() else 0
    hp -= hp - damage
  }
}
