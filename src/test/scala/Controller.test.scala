import rogue.controller.Controller
import rogue.controller.KeyEvent

class ControllerTest extends munit.FunSuite {
  test("press key") {
    for event <- KeyEvent.values do
      var read_it = event match {
        case KeyEvent.RightArrow        => Iterator[Int](0x1b, '[', 'C')
        case KeyEvent.LeftArrow         => Iterator[Int](0x1b, '[', 'D')
        case KeyEvent.DownArrow         => Iterator[Int](0x1b, '[', 'B')
        case KeyEvent.UpArrow           => Iterator[Int](0x1b, '[', 'A')
        case KeyEvent.RightAngleBracket => Iterator[Int](0, 0, 0x3e)
      }

      val controller = Controller(() => read_it.nextOption.getOrElse(0))

      var called = false
      controller.set_callback(event, () => called = true)
      assertEquals(called, false)
      controller.tick()
      assertEquals(called, false)
      controller.tick()
      assertEquals(called, false)
      controller.tick()
      assertEquals(called, true)
  }
}
