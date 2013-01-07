package de.htwg.snake.uis.gui
import java.awt.Dimension
import scala.swing.Frame
import scala.swing.MainFrame
import scala.swing.Menu
import scala.swing.MenuBar
import scala.swing.MenuItem
import scala.swing.TextField
import de.htwg.snake.controller.SnakeController
import de.htwg.snake.model.SnakePanel
import de.htwg.snake.uis.gui.themes.MonochromeTheme
import scala.swing.event.Key
import scala.swing.Action
import de.htwg.snake.uis.gui.themes._
import scala.swing.Button
import scala.swing.event.ButtonClicked

class Gui(controller: SnakeController) extends MainFrame {

  title = "htwgsnake"

  val panel = new SnakePanel(controller)

  contents = panel

  resize
  resizable = true

  menuBar = new MenuBar {
    contents += new Menu("Game") {
      mnemonic = Key.G
      contents += new MenuItem(Action("New") { controller.newGame; resize })
      val popupMenu = new PopupMenu {
        val textField = new TextField("Enter name:")
        contents += textField
        button.text_=("OK")
        button.action_=(Action("changeName") {
          controller.changeName(textField.text)
          button.popUp.hide
        })
        contents += button
      }
      contents += new MenuItem(Action("Namen �ndern") {
        popupMenu.show(this, height / 2, width / 2)
        resize
      })
      contents += new MenuItem(Action("Quit") { exit })
    }
    contents += new Menu("Theme") {
      mnemonic = Key.T
      contents += new MenuItem(Action("Nokia") { panel.theme = new NokiaTheme })
      contents += new MenuItem(Action("Monochrome") { panel.theme = new MonochromeTheme })
    }
    contents += new Menu("Size") {
      mnemonic = Key.S
      contents += new MenuItem(Action("Small") { changeSize(10) })
      contents += new MenuItem(Action("Default") { changeSize(30) })
      contents += new MenuItem(Action("Big") { changeSize(50) })
    }
    contents += new Menu("Speed") {
      mnemonic = Key.S
      contents += new MenuItem(Action("Slow") { controller.setSpeed(1) })
      contents += new MenuItem(Action("Meduim") { controller.setSpeed(2) })
      contents += new MenuItem(Action("Fast") { controller.setSpeed(3) })
    }
  }
  visible = true
  def changeSize(newSize: Int) = {
    controller.changeSize(newSize)
    resize
  }

  def resize() {
    size = new Dimension(width, height)
    centerOnScreen()
  }

  def height = controller.model.y * panel.factor + panel.factor + 60 // menubar offset?
  def width = controller.model.x * panel.factor + panel.factor + 15
}