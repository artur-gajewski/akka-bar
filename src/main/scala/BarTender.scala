import akka.actor.{ActorLogging, Actor}

class BarTender extends Actor with ActorLogging {
  var total = 0

  def receive = {
    case Order(quantity) =>
      total = total + quantity

      log.info(Console.YELLOW + s"I received an order of $quantity pints for ${sender.path.name}" + Console.RESET)

      for (number <- 1 to quantity) {
        log.info(Console.YELLOW + s"Pint $number is coming right up for ${sender.path.name}" + Console.RESET)

        Thread.sleep(5000)

        log.info(Console.YELLOW + s"Pint $number is ready, here you go ${sender.path.name}!" + Console.RESET)

        sender ! FullPint(number)
      }

    case EmptyPint(number) =>
      total match {
        case 1 =>
          log.info(Console.YELLOW + "One more round on the house for everyone! Oh sorry, I forgot we're out of beer." + Console.RESET)
          log.info(Console.RED + "THE END" + Console.RESET)

          context.system.terminate()

        case n =>
          total = total - 1
      }
  }
}