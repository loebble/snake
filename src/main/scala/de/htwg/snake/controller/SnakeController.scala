package de.htwg.snake.controller

import scala.swing.TextField
import de.htwg.util.Observable
import de.htwg.snake.model.SnakeModel
import de.htwg.snake.model.SnakePosition
import de.htwg.snake.model.Highscores
import de.htwg.snake.model.Score

class SnakeController() extends Observable {
  var speed = 2;	// default
  var gameSize = 30	// default
  var direction = Direction.LEFT // default
  var score = new Score
  var isDead = false
  var isPaused = false
  var model = new SnakeModel
  var highscores = new Highscores(gameSize)
  initModel()

  // Change Direction
  def moveLeft = {
    if (direction != Direction.RIGHT) {
      direction = Direction.LEFT
    }
  }

  def moveRight {
    if (direction != Direction.LEFT) {
      direction = Direction.RIGHT
    }
  }

  def moveUp = {
    if (direction != Direction.DOWN) {
      direction = Direction.UP
    }
  }

  def moveDown = {
    if (direction != Direction.UP) {
      direction = Direction.DOWN
    }
  }

  def move() = {
    val oldPos = model.getHead
    val newPos = direction match {
      case Direction.UP => new SnakePosition(oldPos.x, oldPos.y - (1))
      case Direction.DOWN => new SnakePosition(oldPos.x, oldPos.y + (1))
      case Direction.RIGHT => new SnakePosition(oldPos.x + (1), oldPos.y)
      case Direction.LEFT => new SnakePosition(oldPos.x - (1), oldPos.y)
    }
    val res = model.handleNewPosition(newPos)
    res match {
      //TODO Namensabfrage
      case "death" => println("tot - score: " + score) ; highscores.writeToHighscores(model.name,score.get); model.dead = true
      case "grow" => println("wachsen"); score.scored
      case "growExtra" => println("Extra wachsen"); score.scoredExtra
      case "empty" => println("nix");
    }
    notifyObservers
  }

  def initModel() = {
    model = new SnakeModel(model.x,model.y)
    model.initSnake
    model.initField
  }

  def changeSize(size: Int) = {
    val player=model.name
    score = new Score(_speed = speed)
    this.highscores = new Highscores(size)
    model = new SnakeModel(size, size)
    model.initSnake
    model.initField
    model.name=player
  }
  
  def getHighscores = {
    highscores.readFromHighscores
  }
  def getHighscoresKind = {
    highscores.kind
  }

  def initTimer() = {
    val time = speed match {
      case 1 => 500
      case 2 => 200
      case 3 => 50
    }
    var timer = new Thread(new Runnable {
      def run() {
        while (!model.dead) {
          Thread.sleep(time)
          if (!isPaused) move
        }
      }
    })
    timer.start
  }
  def changeName(name:String) {
    model.name = name
  }
  def newGame = {
    val player=model.name
    score = new Score(_speed = speed)
    initModel()
    model.name = player
    initTimer()
  }
  def setSpeed(x:Int){
    speed = x
  }
  def togglePause = {
    isPaused = !isPaused
  }
}

