package de.htwg.snake.uis.gui.themes
import java.awt.Color

trait Theme {
  def backgroundColor : Color
  def snakeColor: Color
  def barricadeColor: Color
  def foodColor: Color
  def extraFoodColor: Color
}