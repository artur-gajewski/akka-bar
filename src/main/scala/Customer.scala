import akka.actor.{ActorLogging, Actor}

class Customer extends Actor with ActorLogging {
  def receive = {
    case FullPint(number) =>
      log.info(s"I'm drinking pint $number now...")

      // TODO: Akka Scheduler instead: http://doc.akka.io/docs/akka/2.3.1/scala/scheduler.html
      Thread.sleep(30000)

      log.info(s"Done, here is the empty glass for pint $number")

      sender ! EmptyPint(number)
  }
}