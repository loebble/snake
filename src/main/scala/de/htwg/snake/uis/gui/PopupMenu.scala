package de.htwg.snake.uis.gui

import javax.swing.JPopupMenu
import scala.swing.{ Component, MenuItem }
import scala.swing.SequentialContainer.Wrapper
import scala.swing.Button

object PopupMenu {
  private[PopupMenu] trait JPopupMenuMixin { def popupMenuWrapper: PopupMenu }
}

class PopupMenu extends Component with Wrapper {
  val ref = this
  private var _button = new Button{
    val popUp = ref
  }
  def button = _button

  override lazy val peer: JPopupMenu = new JPopupMenu with PopupMenu.JPopupMenuMixin with SuperMixin {
    def popupMenuWrapper = PopupMenu.this
  }

  def show(invoker: Component, x: Int, y: Int): Unit = peer.show(invoker.peer, x, y)
  def hide() = { peer.setVisible(false) }

  /* Create any other peer methods here */
}