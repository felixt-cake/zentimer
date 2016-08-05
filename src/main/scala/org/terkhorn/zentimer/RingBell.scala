package org.terkhorn.zentimer

import java.io.InputStream

import com.typesafe.config.ConfigFactory
import sun.audio.{AudioPlayer, AudioStream}

import scala.util.Try

/**
  * Ring the damn bell!
  */
object RingBell {
  private val SOUND_FILENAME = s"/${ConfigFactory.load().getString("sound_filename")}"

  def ringBell = () =>
    Try {
      /** reads from src/main/resources! */
      val inputStream: InputStream = getClass.getResourceAsStream(SOUND_FILENAME)
      val audioStream: AudioStream = new AudioStream(inputStream)
      AudioPlayer.player.start(audioStream)
    } recover {
      case e => e.printStackTrace()
    }
}
