package de.htwg.snake.model

class Score(_speed:Int = 2, var score:Int = 0) {
	def speed = _speed
	def get = score
	def scored = score+=(3* _speed)
	def scoredExtra = score+=(6* _speed)
}