package de.htwg.snake.controller

import org.specs2.mutable.SpecificationWithJUnit

class SnakeControllerSpec extends SpecificationWithJUnit {
  "A new SnakeController" should {
    val controller = new SnakeController
    "be able to change the size correctly and save playerData" in{
      val pName = "Steve"
      controller.model.name_=("Steve")
      controller.changeSize(10)
      controller.model.x must be_==(10)
      controller.model.y must be_==(10)
      controller.model.name must be_==(pName)
      controller.score.get must be_==(0)
    }
    "be able to move correctly" in{
      val controller = new SnakeController
      controller.moveUp
      controller.move
      controller.model.getHead.x must be_==(15)
      controller.model.getHead.y must be_==(14)
      controller.moveRight
      controller.move
      controller.model.getHead.x must be_==(16)
      controller.model.getHead.y must be_==(14)
      controller.moveDown
      controller.move
      controller.model.getHead.x must be_==(16)
      controller.model.getHead.y must be_==(15)
      controller.moveLeft
      controller.move
      controller.model.getHead.x must be_==(15)
      controller.model.getHead.y must be_==(15)
    }
    "correctly start a new Game" in {
      val controller = new SnakeController
      val pName = "Hans"
      controller.model.name = pName
      controller.newGame
      controller.model.name must be_==(pName)
      controller.score.get must be_==(0)
    }
    
  }

}