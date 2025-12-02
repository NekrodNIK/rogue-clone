package rogue.model

import scala.collection.immutable.ListSet
import scala.collection.mutable
import scala.util.Random

case class Corridor(points: ListSet[Point])

case class Level(width: Int, height: Int) {
  private val maxrooms: Int = 2
  val rooms: mutable.ArraySeq[Room] = mutable.ArraySeq.fill(maxrooms)(null)
  val corridors: mutable.ArraySeq[Corridor] = mutable.ArraySeq.fill(maxrooms * (maxrooms - 1) / 2)(null)
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
    rooms(0) = Room(Rectangle(Point(11, 11), Point(21, 21)), Iterable(Point(16, 22)))
    rooms(1) = Room(Rectangle(Point(5, 28), Point(25, 34)), Iterable(Point(16, 27)))
    
    corridors(0) = Corridor(ListSet.from(for i <- 23 to 26 yield Point(16, i)))
  }

  def contains(point: Point): Boolean = rooms.exists(_.contains(point))
}

