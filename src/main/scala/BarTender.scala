import akka.actor.{ActorLogging, Actor}

class BarTender extends Actor with ActorLogging {
  var total = 0

  def receive = {
    case Order(quantity) =>
      total = total + quantity

      log.info(s"I received an order of $quantity pints for [${sender.path}]")

      for (number <- 1 to quantity) {
        log.info(s"Pint $number is coming right up for [${sender.path}]")

        // TODO: Akka Scheduler instead: http://doc.akka.io/docs/akka/2.3.1/scala/scheduler.html
        Thread.sleep(5000)

        log.info(s"Pint $number is ready, here you go [${sender.path}]")

        sender ! FullPint(number)
      }

    case EmptyPint(number) =>
      total match {
        case 1 =>
          log.info("One more round on the house for everyone! Oh sorry, I forgot we're out of beer.")

          context.system.terminate()

        case n =>
          total = total - 1
      }
  }
}