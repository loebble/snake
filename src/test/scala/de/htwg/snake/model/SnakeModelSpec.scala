package de.htwg.snake.model

import org.specs2.mutable.SpecificationWithJUnit
class SnakeModelSpec() extends SpecificationWithJUnit {
  "A new Model" should {
    
    "have a field with the same size than itself" in {
      val model = new SnakeModel
      model.x must be_==(model.field.x)
      model.y must be_==(model.field.y)
    }
    "have a snake which starts in the middle of the field" in {
      val model = new SnakeModel
      var j: List[SnakePosition]= Nil
      model.snake must be_==(j)
      model.initSnake
      model.getHead must be_==(new SnakePosition(model.x / 2, model.y / 2))
    }
    "let the snake die when it bites itself" in {
      val model = new SnakeModel
      val pos = new SnakePosition(10,10)
      model.snake ::= pos
      val action = model.handleNewPosition(pos)
      action must be_==("death")
    }
    "let the snake die when rans into a Barricade" in {
      val model = new SnakeModel
      model.initSnake
      model.field.cells(10)(10) = CellType.BARRICADE
      val pos = new SnakePosition(10,10)
      val res = model.handleNewPosition(pos)
      res must be_==("death")
    }
    "not let the snake grow if ther is no food" in {
      val model = new SnakeModel
      model.initSnake
      model.field.cells(14)(15) = CellType.EMPTY
      val pos = new SnakePosition(14,15)
      model.handleNewPosition(pos)
      model.snake.size must be_==(1)
    }
    "let the snake grow if ther IS food" in {
      val model = new SnakeModel
      model.initSnake
      val pos = new SnakePosition(10,10)
      model.field.cells(10)(10) = CellType.FOOD
      val res = model.handleNewPosition(pos)
      res must be_==("grow")
    }
    "let the snake grow if ther IS extraFood" in {
       val model = new SnakeModel
      model.initSnake
      val pos = new SnakePosition(10,10)
      model.field.cells(10)(10) = CellType.EXTRAFOOD
      val res = model.handleNewPosition(pos)
      res must be_==("growExtra")
    }
  }
}