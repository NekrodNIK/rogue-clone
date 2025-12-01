package model

import model.Room

import scala.collection.mutable
import scala.util.Random

case class Level(width: Int, height: Int) {
  private val maxrooms: Int = 9
  val rooms: mutable.ArraySeq[Room] = mutable.ArraySeq.fill(maxrooms)(Room(Point(0, 0), Point(0, 0)))

  def regenerate(): Unit = {
    val random = Random()
    val roomMaxSize: Point = Point(width / 3, height / 3)
    for i <- 0 until this.maxrooms do {
      val sector = Point(roomMaxSize.x * (i % 3), roomMaxSize.y * (i / 3))
      val size = Point(random.between(4, roomMaxSize.x), random.between(4, roomMaxSize.y))
      val topleft = Point(sector.x + random.between(1, roomMaxSize.x - size.x),
        sector.y + random.between(1, roomMaxSize.y - size.y))
      rooms(i) = Room(topleft, topleft + size)
    }
  }

  def contains(point: Point): Boolean = rooms.exists(_.contains(point))
}

