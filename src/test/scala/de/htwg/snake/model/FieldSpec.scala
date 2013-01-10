package de.htwg.snake.model
import org.specs2.mutable.SpecificationWithJUnit
class FieldSpec extends SpecificationWithJUnit {
  "A new Field(x,y) " should {
    val field = new Field(30, 30)
    field.initialize
    var snake: List[SnakePosition] = Nil
    snake ::= new SnakePosition(field.x / 2, field.y / 2)
    "have a default cells Array of Dimension (x+1,y+1) " in {
      val cells = field.cells
      cells.length must be_==(31)
      cells(0).length must be_==(31)
    }
    "have its borders to be Barricades" in {
      val f1 = new Field(30, 30)
      f1.initialize
      val cells = f1.cells
      var bar = true
      for (x <- 0 to f1.x) {
        for (y <- 0 to f1.y) {
          if(f1.cells(0)(y) != CellType.BARRICADE) bar =false
          if(f1.cells(f1.x)(y) != CellType.BARRICADE)bar =false
        }
        // y-borders
        if(f1.cells(x)(0) != CellType.BARRICADE) bar = false
        if(f1.cells(x)(f1.y) != CellType.BARRICADE) bar = false
      }
      bar must be_==(true)
    }
    "give me a Food and set the CellType on the field to FOOD" in {
      field.food = Array(-1, -1)
      val p = field.getFood(snake)
      val cellType = field.cells(p(0))(p(1))
      cellType must be_==(CellType.FOOD)
    }
    "give me an ExtraFood after the 5th time requesting a food" in {
      val field = new Field(30, 30)
      field.initialize
      for (x <- 0 to 4) {
        field.food = Array(-1, -1)
        val food = field.getFood(snake)
        if (x == 4) {
          val cellType = field.cells(food(0))(food(1))
          cellType must be_==(CellType.EXTRAFOOD)
          field.count must be_==(0)
          field.extraActive must be_==(true)
          field.countExtra must be_==(0)
        }
      }
    }
    "After 35 times requesting (Extra)Food it must be gone and replaced with regular food" in {
      for (x <- 0 to 35) {
        val food = field.getFood(snake)
      }
      val food = field.getFood(snake)
      val cells = field.cells
      cells(food(0))(food(1)) must be_==(CellType.FOOD)
      field.extraActive must be_==(false)
    }
  }
}