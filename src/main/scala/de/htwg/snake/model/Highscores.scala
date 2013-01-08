package de.htwg.snake.model

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileReader
import java.io.FileWriter
import collection._

import scala.collection.mutable.Map

class Highscores(x:Int = 30) {
  var path = ""
  var kind = "default"
  x match{
    case 50 => path = "highscoresBig.txt"; kind = "Big"
    case 10 => path = "highscoresSmall.txt"; kind = "Small"
    case _ => path = "highscoresDefault.txt"; kind = "Default"
  }
    
  def writeToHighscores(name: String, score: Int) {
    var highscore = readFromHighscores();
    highscore.+=((name, score))
    val sortedScores = highscore.sortWith(_._2 > _._2)
    val best10 = sortedScores.splitAt(10)._1
    val f = new BufferedWriter(new FileWriter(path))
    try {
      best10.foreach(s => f.write(s._1 + ";" + s._2+"\n"))
    } finally { f.close() }
  }

  def readFromHighscores() = {
    var highscores = scala.collection.mutable.ArrayBuffer[(String, Int)]()
    for {
      (line) <- io.Source.fromFile(path).getLines
      val sub = line.split(";")
    } highscores.+=((sub(0), sub(1).toInt))
    highscores
  }
}
