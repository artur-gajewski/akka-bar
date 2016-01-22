import akka.actor.{ActorLogging, Actor}

class Customer extends Actor with ActorLogging {
  def receive = {
    case FullPint(number) =>
      log.info(Console.GREEN + s"I'm drinking pint $number now..." + Console.RESET)

      Thread.sleep(30000)

      log.info(Console.CYAN + s"Done, here is the empty glass for pint $number" + Console.RESET)

      sender ! EmptyPint(number)
  }
}