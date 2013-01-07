package de.htwg.snake.model

import java.awt.Color
import java.awt.Font
import java.awt.FontMetrics
import java.awt.GraphicsEnvironment
import scala.swing.event.Key
import scala.swing.event.KeyPressed
import scala.swing.Graphics2D
import scala.swing.Panel
import de.htwg.snake.controller.SnakeController
import de.htwg.snake.uis.gui.themes.NokiaTheme
import de.htwg.snake.uis.gui.themes.Theme
import de.htwg.util.Observer
import java.awt.RenderingHints

class SnakePanel(controller: SnakeController) extends Panel with Observer {

  val factor = 10;
  var theme: Theme = new NokiaTheme
  visible = true
  focusable = true;

  controller.add(this)

  listenTo(keys)
  reactions += {
    case KeyPressed(_, Key.Up, _, _) => controller.moveUp
    case KeyPressed(_, Key.Down, _, _) => controller.moveDown
    case KeyPressed(_, Key.Left, _, _) => controller.moveLeft
    case KeyPressed(_, Key.Right, _, _) => controller.moveRight
    case KeyPressed(_, Key.Space, _, _) => controller.togglePause
  }

  override def paint(g: Graphics2D) = {
    super.paint(g)
    background = theme.backgroundColor
    if (!controller.model.dead) {
      renderField(g)
    } else {
      renderGameOver(g)
    }
  }

  def renderField(g: Graphics2D) = {
    for (y <- 0 to controller.model.field.y) {
      for (x <- 0 to controller.model.field.x) {
        controller.model.field.cells(x)(y) match {
          case CellType.BARRICADE => renderRect(g, x, y, theme.barricadeColor)
          case CellType.EMPTY => if (controller.model.snake.contains(new SnakePosition(x, y))) renderOval(g, x, y, theme.snakeColor)
          case CellType.FOOD => renderRect(g, x, y, theme.foodColor)
          case CellType.EXTRAFOOD => renderRect(g, x, y, theme.extraFoodColor)
        }
      }
    }
  }

  def renderRect(g: Graphics2D, x: Int, y: Int, c: Color) = {
    g.setColor(c)
    g.fillRect(x * this.factor, y * this.factor, this.factor, this.factor)
  }

  def renderOval(g: Graphics2D, x: Int, y: Int, c: Color) = {
    g.setColor(c)
    g.fillOval(x * this.factor, y * this.factor, this.factor, this.factor)
  }

  def renderGameOver(g: Graphics2D) = {

    // game over
    val gameOverFont = new Font("LucidaSans", Font.PLAIN, 40)
    g.setFont(gameOverFont)
    val gameOver = "Game Over!"
    g.drawString(gameOver, (controller.model.x * factor - g.getFontMetrics().stringWidth(gameOver)) / 2, 50)

    // score
    val scoreFont = new Font("LucidaSans", Font.PLAIN, 30)
    g.setFont(scoreFont)
    val score = "Your Score: " + controller.score.get
    g.drawString(score, (controller.model.x * factor - g.getFontMetrics().stringWidth(score)) / 2, 100)

    // highscores
    val highscoreFont = new Font("LucidaSans", Font.PLAIN, 15)
    g.setFont(highscoreFont)
    var highscoreStart = 150
    g.drawString("Highscores ("+controller.getHighscoresKind+"):", 10, highscoreStart)
    val highscores = controller.getHighscores
    highscores.foreach { e =>
      highscoreStart += 20
      g.drawString(e._1 + ": " + e._2, 10, highscoreStart)
    }

  }

  override def update = {
    repaint()
  }

}