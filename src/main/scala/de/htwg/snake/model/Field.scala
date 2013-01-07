package de.htwg.snake.model

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class Field(val x: Int, val y: Int) {
  var cells = Array.ofDim[Any](x + 1, y + 1)
  cells(0)(0) = CellType.EMPTY
  var food = Array(-1, -1)
  var count = 0; var countExtra = 0
  var extraActive = false

  def getFood(snake: List[SnakePosition]): Array[Int] = {
    if (this.food(0) == -1) {
      count += 1
      var foodX = -1
      var foodY = -1

      do {
        foodX = Random.nextInt(x) // random int with upper bound x
        foodY = Random.nextInt(y) // ...
      } while (snake.contains(new SnakePosition(foodX, foodY)))

      if (cells(foodX)(foodY) == CellType.EMPTY) {
        if (count == 5) {
          cells(foodX)(foodY) = CellType.EXTRAFOOD
          extraActive = true
          count = 0
        } else cells(foodX)(foodY) = CellType.FOOD
        this.food = Array(foodX, foodY)
        return this.food
      } else getFood(snake)
    } else {
      if (extraActive) {
        countExtra += 1
        if (countExtra == 35) {
          cells(food(0))(food(1)) = CellType.EMPTY
          this.food = Array(-1, -1)
          countExtra = 0
          extraActive = false
        }
      }
      return food;
    }
  }
}

object CellType extends Enumeration {
  type CellType = Value
  val EMPTY, BARRICADE, FOOD, EXTRAFOOD = Value
}