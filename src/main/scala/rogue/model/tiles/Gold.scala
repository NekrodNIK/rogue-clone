package rogue.model.tiles

import rogue.model.{Point, TileEntity}

case class Gold(amount: Int, position: Point, id: Int) extends TileEntity {
}
