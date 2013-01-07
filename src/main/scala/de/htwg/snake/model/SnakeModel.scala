package de.htwg.snake.model

import de.htwg.snake.controller.Direction
import de.htwg.snake.controller.Direction._

class SnakeModel(val x: Int = 30, val y: Int = 30) {
  var field = new Field(x, y);
  var snake: List[SnakePosition] = Nil
  var dead = false
  private var _name = "noName"
  def name = _name
  def name_= (value:String):Unit = _name = value

  def initSnake = {
    snake ::= new SnakePosition(field.x / 2, field.y / 2)
  }

  def initField = {
    for (x <- 0 to field.x) {
      for (y <- 0 to field.y) {
        // default: leer
        field.cells(x)(y) = CellType.EMPTY

        // x-borders
        field.cells(0)(y) = CellType.BARRICADE
        field.cells(field.x)(y) = CellType.BARRICADE
      }
      // y-borders
      field.cells(x)(0) = CellType.BARRICADE
      field.cells(x)(field.y) = CellType.BARRICADE
    }
    // First Food
    val foodPos = field.getFood(snake)
    println("Food:" + foodPos(0) + foodPos(1))
  }

  def handleNewPosition(pos: SnakePosition): String = {
    val tail = snake.last
    if (snake.contains(pos)) {
      println("selbst gefressen -> tot")
      return "death"
    }
    snake ::= pos
    if (pos.x < 0 || pos.y < 0) return "death"
    if (field.cells(pos.x)(pos.y) != CellType.FOOD && field.cells(pos.x)(pos.y) != CellType.EXTRAFOOD) {
      snake -= tail
    }
    val cellType = field.cells(pos.x)(pos.y)
    val result = cellType match {
      case CellType.BARRICADE =>
        "death"
      case CellType.FOOD =>
        field.food = Array(-1, -1)
        field.cells(pos.x)(pos.y) = CellType.EMPTY
        "grow"
      case CellType.EXTRAFOOD =>
        field.food = Array(-1, -1)
        field.extraActive = false
        field.cells(pos.x)(pos.y) = CellType.EMPTY
        "growExtra"
      case CellType.EMPTY =>
        "empty"
    }
    val foodPos = this.field.getFood(snake)
    result
  }

  def getHead = {
    snake.head
  }

}

case class SnakePosition(x: Int, y: Int)