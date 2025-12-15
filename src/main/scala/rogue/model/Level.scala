package rogue.model

import rogue.model.monsters.{Bat, Monster}
import rogue.model.tiles.{Exit, Gold}
import rogue.view

import scala.collection.immutable.ListSet
import scala.collection.mutable
import scala.util.Random

case class Corridor(points: ListSet[Point]) extends Structure, view.Renderable {
  override val renderObj = view.RenderCorridor(this)
  
  def contains(point: Point): Boolean = points.contains(point)
}

object Corridor {
  val empty: Corridor = Corridor(ListSet.empty)
}

case class Level(width: Int, height: Int, random: Random) extends view.Renderable {  
  override val renderObj = view.RenderLevel(this)
  
  private val maxrooms: Int                 = 9
  val rooms: mutable.ArraySeq[Room]         = mutable.ArraySeq.fill(maxrooms)(null)
  val corridors: mutable.ArraySeq[Corridor] = mutable.ArraySeq.fill(12)(Corridor(ListSet.empty))
  val monsters: mutable.ListBuffer[Monster] = mutable.ListBuffer.empty
  var floor: Int = 0
  var id_cnt: Int = 1
  regenerate()

  def contains(point: Point): Option[Structure] = 
    (rooms.iterator ++ corridors.iterator).find(s => s.contains(point))

  def regenerate(): Unit = {
    monsters.clear()
    regenerate_rooms()
    regenerate_corridors()
  }

  private def regenerate_rooms(): Unit = {
    val sectorSize: Point = Point(width / 3, height / 3)
    for i <- 0 until this.maxrooms do {
      val sector  = Point(sectorSize.x * (i % 3), sectorSize.y * (i / 3))
      val size    = Point(random.between(4, sectorSize.x - 4 + 1), random.between(4, sectorSize.y - 4 + 1))
      val topleft = Point(sector.x + random.between(2, sectorSize.x - size.x - 1), sector.y + random.between(2, sectorSize.y - size.y - 1))
      rooms(i) = Room(Rectangle(topleft, topleft + size))
      fill_room(rooms(i))
    }
    val exit_room = rooms(random.nextInt(maxrooms))
    exit_room.tiles.addOne(
      Exit(Point(random.between(exit_room.shape.topLeft.x, exit_room.shape.bottomRight.x + 1),
        random.between(exit_room.shape.topLeft.y, exit_room.shape.bottomRight.y + 1)), id_cnt)
    )
    id_cnt += 1
  }

  private def fill_room(room: Room): Unit = {
    val chance: Int = if random.nextBoolean() then {
      room.tiles.addOne(Gold(random.nextInt(50 + 10 * floor) + 2,
        Point(random.between(room.shape.topLeft.x, room.shape.bottomRight.x + 1),
          random.between(room.shape.topLeft.y, room.shape.bottomRight.y + 1)),
        id_cnt))
      id_cnt += 1
      80
    } else 25
    if random.nextInt(100) < chance then {
      val monster = Bat(
        Point(random.between(room.shape.topLeft.x, room.shape.bottomRight.x + 1),
          random.between(room.shape.topLeft.y, room.shape.bottomRight.y + 1)),
        id_cnt, random
      )
      id_cnt += 1
      monsters.addOne(monster)
    }
  }

  private def regenerate_corridors(): Unit = {
    val inGraph: Array[Boolean]            = Array.fill(maxrooms)(false)
    val connections: Array[Array[Boolean]] = Array.fill(maxrooms)(Array.fill(maxrooms)(false))
    val possible: Array[Array[Boolean]]    = Array(
      Array(false, true, false, true, false, false, false, false, false),
      Array(true, false, true, false, true, false, false, false, false),
      Array(false, true, false, false, false, true, false, false, false),
      Array(true, false, false, false, true, false, true, false, false),
      Array(false, true, false, true, false, true, false, true, false),
      Array(false, false, true, false, true, false, false, false, true),
      Array(false, false, false, true, false, false, false, true, false),
      Array(false, false, false, false, true, false, true, false, true),
      Array(false, false, false, false, false, true, false, true, false)
    )
    for i <- 0 until maxrooms do {
      for j <- 0 until maxrooms do connections(i)(j) = false
      inGraph(i) = false
    }
    for i <- corridors.indices do corridors(i) = Corridor.empty

    var corridorCount = 0
    var roomCount     = 1
    var room1         = random.nextInt(maxrooms)
    inGraph(room1) = true
    while roomCount < maxrooms do {
      var room2: Int = -1
      var tries = 0
      for i <- 0 until maxrooms do {
        if possible(room1)(i) && !inGraph(i) then {
          tries += 1
          if random.nextInt(tries) == 0 then room2 = i
        }
      }
      if room2 == -1 then {
        room1 = random.nextInt(maxrooms)
        while !inGraph(room1) do room1 = random.nextInt(maxrooms)
      } else {
        inGraph(room2) = true
        corridors(corridorCount) = connect(room1, room2)
        corridorCount += 1
        connections(room1)(room2) = true
        connections(room2)(room1) = true
        roomCount += 1
      }
    }
    // we have spanning tree, let's make it more dungeon-y
    for i <- 0 until random.nextInt(5) do {
      room1 = random.nextInt(maxrooms)
      var tries = 0
      var room2: Int = -1
      for i <- 0 until maxrooms do {
        if possible(room1)(i) && !connections(room1)(i) then {
          tries += 1
          if random.nextInt(tries) == 0 then room2 = i
        }
      }
      if room2 != -1 then {
        corridors(corridorCount) = connect(room1, room2)
        corridorCount += 1
        connections(room1)(room2) = true
        connections(room2)(room1) = true
      }
    }
  }

  private def connect(i: Int, j: Int): Corridor = {
    val room_from_i = i min j
    val room_to_i   = i max j
    val room_from   = rooms(room_from_i)
    val room_to     = rooms(room_to_i)
    val toRight     = if i < j then i + 1 == j else j + 1 == i // right or down

    if toRight then {
      val startPoint = Point(room_from.shape.bottomRight.x + 1, random.between(room_from.shape.topLeft.y + 1, room_from.shape.bottomRight.y))
      val endPoint   = Point(room_to.shape.topLeft.x - 1, random.between(room_to.shape.topLeft.y + 1, room_to.shape.bottomRight.y))

      val turnSpot = random.between(startPoint.x + 1, endPoint.x)

      val col = (
        for i <- startPoint.x + 1 until turnSpot yield Point(i, startPoint.y)
      ).concat(
        for i <- startPoint.y until endPoint.y by (if startPoint.y <= endPoint.y then 1 else -1) yield Point(turnSpot, i)
      ).concat(
        for i <- turnSpot until endPoint.x yield Point(i, endPoint.y)
      )
      rooms(room_from_i).doors.addOne(startPoint)
      rooms(room_to_i).doors.addOne(endPoint)
      Corridor(ListSet.from(col))
    } else {
      val startPoint = Point(random.between(room_from.shape.topLeft.x + 1, room_from.shape.bottomRight.x), room_from.shape.bottomRight.y + 1)
      val endPoint   = Point(random.between(room_to.shape.topLeft.x + 1, room_to.shape.bottomRight.x), room_to.shape.topLeft.y - 1)

      val turnSpot = random.between(startPoint.y + 1, endPoint.y)

      val col = (for i <- startPoint.y + 1 until turnSpot yield Point(startPoint.x, i))
        .concat(
          for i <- startPoint.x until endPoint.x by (if startPoint.x <= endPoint.x then 1 else -1) yield Point(i, turnSpot)
        )
        .concat(
          for i <- turnSpot until endPoint.y yield Point(endPoint.x, i)
        )
      rooms(room_from_i).doors.addOne(startPoint)
      rooms(room_to_i).doors.addOne(endPoint)
      Corridor(ListSet.from(col))
    }
  }
}
