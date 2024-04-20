package com.example.stop_watch_notify.utils

import android.content.Context
import android.media.MediaActionSound
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import java.io.File

object SoundManager {

    fun playSound(context: Context, resId: Int) {
        val mediaPlayer = MediaPlayer.create(context, resId)
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener {
            mediaPlayer.release()
        }
    }

    fun playSound(context: Context, location: String) {
        val mediaPlayer = MediaPlayer.create(context, Uri.parse("$location"))

        mediaPlayer.start()
    }

}