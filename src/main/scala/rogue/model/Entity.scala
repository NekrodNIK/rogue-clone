package rogue.model

import rogue.view

trait Entity extends view.Renderable {
  override val renderObj = view.RenderEntity(this)
  
  def position: Point
  def id: Int
}
