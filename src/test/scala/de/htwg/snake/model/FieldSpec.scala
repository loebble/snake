package de.htwg.snake.model
import org.specs2.mutable.SpecificationWithJUnit
class FieldSpec extends SpecificationWithJUnit {
  "A new Field(x,y) " should {
    val field = new Field(30, 30)
    field.initialize
    println(field.count)
    var snake: List[SnakePosition] = Nil
    snake ::= new SnakePosition(field.x / 2, field.y / 2)
    "have a default cells Array of Dimension (x+1,y+1) " in {
      val cells = field.cells
      cells.length must be_==(31)
      cells(0).length must be_==(31)
    }
    "give me a Food and set the CellType on the field to FOOD" in {
      field.food = Array(-1, -1)
      val p = field.getFood(snake)
      println(field.count)
      val cellType = field.cells(p(0))(p(1))
      cellType must be_==(CellType.FOOD)
    }
    "give me an ExtraFood after the 5th time requesting a food" in {
      for(x <- 0 to 3){
        field.food = Array(-1, -1)
        val food = field.getFood(snake)
        if(x == 3){
          val cellType = field.cells(food(0))(food(1))
          cellType must be_==(CellType.EXTRAFOOD)
          field.count must be_==(0)
          field.extraActive must be_==(true)
          field.countExtra must be_==(0)
          "and after 35 times requesting ExtraFood it must be gone and replaced with regular food" in {
            val oldPos = field.getFood(snake)
            for(x<- 0 to 35){
              val food = field.getFood(snake)
            }
            val food = field.getFood(snake)
            val cellType = field.cells(food(1))(food(2))
            cellType must be_==(CellType.FOOD)
            val cells = field.cells
            cells(food(1))(food(2))must be_==(CellType.FOOD)
            oldPos must be_!=(field.getFood(snake))
            field.extraActive must be_==(false)
          }
        }
      }
    }
  }
}