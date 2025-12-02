package rogue.model

import scala.collection.mutable
import scala.util.Random

case class Level(width: Int, height: Int) {
  private val maxrooms: Int = 1
  val rooms: mutable.ArraySeq[Room] = mutable.ArraySeq.fill(maxrooms)(null)
  regenerate()

  def regenerate(): Unit = {
//    val random = Random()
//    val roomMaxSize: Point = Point(width / 3, height / 3)
//    for i <- 0 until this.maxrooms do {
//      val sector = Point(roomMaxSize.x * (i % 3), roomMaxSize.y * (i / 3))
//      val size = Point(random.between(4, roomMaxSize.x), random.between(4, roomMaxSize.y))
//      val topleft = Point(sector.x + random.between(1, roomMaxSize.x - size.x),
//        sector.y + random.between(1, roomMaxSize.y - size.y))
//      rooms(i) = Room(Rectangle(topleft, topleft + size))
//    }
    rooms(0) = Room(Rectangle(Point(21, 21), Point(31, 31)))
  }

  def contains(point: Point): Boolean = rooms.exists(_.contains(point))
}

