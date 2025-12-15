package rogue.model

import scala.util.Random

case class Damage (dice: Int, count: Int, random: Random) {
  def roll(): Int = (for i <- 0 until count yield random.nextInt(dice)).sum
}
