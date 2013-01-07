package de.htwg.snake.model

import org.specs2.mutable.SpecificationWithJUnit

class ScoreSpec extends SpecificationWithJUnit {
  "A new Score " should {
    val score = new Score(2,0)
    "have a default speed value of 2" in {
      val s = score.speed
      s must be_==(2)
    }
    "have a value of 6 after scoring" in {
      score.scored
      val s = score.get
      s must be_==(6)
    }
    "have a value of 18 after extraScoring" in {
      score.scoredExtra
      val s = score.get
      s must be_==(18)
    }
  }

}