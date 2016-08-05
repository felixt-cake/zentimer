package org.terkhorn.zentimer

import scala.concurrent.duration.Duration

object DoSomethingIn {
  implicit class doSomething[A](fn: () => A) {
    def in(delay: Duration) = {
      val t = new java.util.Timer()
      val task = new java.util.TimerTask {
        def run() = fn()
      }
      t.schedule(task, delay.toMillis)
    }
  }
}
