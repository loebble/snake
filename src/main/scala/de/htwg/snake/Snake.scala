package de.htwg.snake

import de.htwg.snake.uis.gui.Gui
import de.htwg.snake.controller.SnakeController
import de.htwg.snake.uis.tui.Tui

object Snake {

  val controller = new SnakeController()
  val tui = new Tui(controller)
  val gui = new Gui(controller)

  def main(args: Array[String]) {
    while (tui.processInput(readLine())) {}
  }

}