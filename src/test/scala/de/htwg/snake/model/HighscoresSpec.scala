package de.htwg.snake.model
import org.specs2.mutable.SpecificationWithJUnit
import scala.collection.mutable.ArrayBuffer
import java.io.BufferedWriter
import java.io.FileWriter
class HighscoresSpec extends SpecificationWithJUnit {
  "A new default Highscore " should {
    val highscore = new Highscores()
    "have a path to highscoresDefault" in {
    	highscore.path must be_==("highscoresDefault.txt")
    }
    "have a Highscore Ranking <= 10" in{
      val score = highscore.readFromHighscores
      val check = (score.size <= 10)
      check must be_==(true)
    }
    "be able to read from the textFile" in{
      val score = highscore.readFromHighscores
      val m = score match{
        case score:scala.collection.mutable.ArrayBuffer[(String, Int)] => "array"
        case _ => "fail"
      }
      m must be_==("array")
    }
    "be able to write scores into the file and sort it by score" in{
      val savedScore = highscore.readFromHighscores
      highscore.writeToHighscores("TESTNAME", 10000)
      val scores = highscore.readFromHighscores
      val (name,score) = scores(0)
      val newScores = scores.remove(0)
      val f = new BufferedWriter(new FileWriter(highscore.path))
      try{
        savedScore.foreach(s => f.write(s._1 + ";" + s._2+"\n"))
      } finally { f.close() }
      name must be_==("TESTNAME")
      score must be_==(10000)
    }
  }
}