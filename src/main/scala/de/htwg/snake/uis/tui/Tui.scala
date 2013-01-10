package de.htwg.snake.uis.tui
import de.htwg.snake.controller.SnakeController
import de.htwg.util.Observer
import scala.annotation.target.field
import de.htwg.snake.model.CellType
import de.htwg.snake.model.SnakePosition
import com.sun.j3d.utils.scenegraph.io.retained.Controller

class Tui(var controller: SnakeController) extends Observer {

  controller.add(this)
  println("Enter command: n-New, speed[1-3]-set speed level, size[1-3]-set field size")
  println("Controls: 8-move up, 5-move down, 4-move left, 6-move right")

  def processInput(input: String) = {
    var continue = true
    input match {
      case "n" => controller.newGame
      case "4" => controller.moveLeft
      case "5" => controller.moveDown
      case "6" => controller.moveRight
      case "8" => controller.moveUp
      case "speed1" => controller.speed = 1
      case "speed2" => controller.speed = 2
      case "speed3" => controller.speed = 3
      case "size1" => controller.changeSize(10)
      case "size2" => controller.changeSize(30)
      case "size3" => controller.changeSize(50)
      case _ => if (input.startsWith("cn ")) controller.model.name = input.split(" ")(1) else println("Falsche Eingabe");
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
    if (controller.isDead == true) {
      println("Highscores:")
      controller.getHighscores.foreach(s => println(s._1 + ":" + s._2))
    }
  }

  override def update = {
    drawField
  }
}