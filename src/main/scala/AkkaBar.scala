import akka.actor.{ActorSystem, Props}

case class Order(quantity: Int)
case class FullPint(number: Int)
case class EmptyPint(number: Int)

object AkkaBar extends App {
  val system = ActorSystem("akka-bar")

  // Bartender
  // Takes 5 seconds to serve a pint
  val moe = system.actorOf(Props(new BarTender), "Moe")

  // Customers
  // Takes 30 seconds to drink a pint
  val homer = system.actorOf(Props(new Customer), "Homer")
  val sam = system.actorOf(Props(new Customer), "Sam")
  val larry = system.actorOf(Props(new Customer), "Larry")
  val barney = system.actorOf(Props(new Customer), "Barney")

  moe tell (Order(5), homer)
  moe tell (Order(2), sam)
  moe tell (Order(3), larry)
  moe tell (Order(1), barney)
}