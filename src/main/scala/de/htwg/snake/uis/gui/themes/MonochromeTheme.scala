package de.htwg.snake.uis.gui.themes
import java.awt.Color

class MonochromeTheme extends Theme {

  def backgroundColor: Color = {
    Color.WHITE
  }

  def snakeColor: Color = {
    Color.BLACK;
  }

  def barricadeColor: Color = {
    Color.BLACK
  }

  def foodColor: Color = {
    Color.GRAY
  }
  
  def extraFoodColor: Color = {
    Color.RED
  }

}