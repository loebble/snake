package de.htwg.snake.model

import org.specs2.mutable.SpecificationWithJUnit

class ScoreSpec extends SpecificationWithJUnit {
  "A new Score " should {
    "have a default speed value of 2" in {
      val score = new Score(2,0)
      val s = score.speed
      s must be_==(2)
    }
    "have a value of 6 after scoring" in {
      val score = new Score(2,0)
      score.scored
      val s = score.get
      s must be_==(6)
    }
    "have a value of 12 after extraScoring" in {
      val score = new Score(2,0)
      score.scoredExtra
      val s = score.get
      s must be_==(12)
    }
  }

}