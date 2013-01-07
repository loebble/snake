package de.htwg.snake.uis.tui
import de.htwg.snake.controller.SnakeController
import de.htwg.util.Observer
import scala.annotation.target.field
import de.htwg.snake.model.CellType
import de.htwg.snake.model.SnakePosition

class Tui(var controller: SnakeController) extends Observer {

  controller.add(this)

  def processInput(input: String) = {
    var continue = true
    input match {
      case "4" => controller.moveLeft
      case "5" => controller.moveDown
      case "6" => controller.moveRight
      case "8" => controller.moveUp
      case _ => println("Falsche Eingabe");
    }
    continue
  }

  def drawField = {

    for (y <- 0 to controller.model.field.y) {
      for (x <- 0 to controller.model.field.x) {
        controller.model.field.cells(x)(y) match {
          case CellType.BARRICADE => print("#")
          case CellType.EMPTY => if (controller.model.snake.contains(new SnakePosition(x, y))) print("o") else print(" ")
          case CellType.FOOD => print("*")
          case CellType.EXTRAFOOD => print("+")
        }
      }
      println("");
    }
  }

  override def update = {
    drawField
  }
}