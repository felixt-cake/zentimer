package org.terkhorn.zentimer

import com.typesafe.config.ConfigFactory
import org.terkhorn.zentimer.DoSomethingIn._
import org.terkhorn.zentimer.RingBell._

import scala.concurrent.duration._
import scala.util.Try

object Main extends App {
  val conf = ConfigFactory.load()

  def exit = () => System.exit(0)

  val period = Try {
    args.head.toInt
  } getOrElse conf.getInt("default_period_minutes")

  val InitialDelay = conf.getInt("initial_delay_seconds")
  val ExitDelay = conf.getInt("exit_delay_seconds")

  println(s"Ringing the bell in $period minutes...")

  // wait a few seconds, then ring initially
  ringBell in InitialDelay.seconds

  ringBell in
    (InitialDelay.seconds plus period.minutes)

  // This can be reassuring.
  Range(1, period)
    .filter(_ % 10 == 0)
    .foreach(tick =>
      (() => println(s"$tick minutes...")) in (InitialDelay.seconds plus tick.minutes))

  exit in (InitialDelay.seconds
    plus period.minutes
    plus ExitDelay.seconds)
}
