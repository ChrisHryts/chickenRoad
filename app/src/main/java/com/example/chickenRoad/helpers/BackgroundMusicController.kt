package com.example.chickenRoad.helpers

import android.annotation.SuppressLint
import android.content.Context
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

class BackgroundMusicController private constructor(private val context: Context) {
    private val player: ExoPlayer by lazy {
        ExoPlayer.Builder(context).build().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(C.USAGE_GAME)
                    .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
                    .build(),
                true
            )
            repeatMode = ExoPlayer.REPEAT_MODE_ONE
            volume = 0.2f
        }
    }

    private var enabled = true
    private var mediaResId: Int? = null

    fun setEnabled(value: Boolean) {
        enabled = value

        if (enabled) {
            if (player.mediaItemCount == 0) mediaResId?.let { prepareIfNeeded(it) }
            if (!player.isPlaying) player.play()
        } else stop()
    }

    private fun prepareIfNeeded(resRawId: Int) {
        mediaResId = resRawId

        if (player.mediaItemCount == 0) {
            val uri = "android.resource://${context.packageName}/$resRawId"
            player.setMediaItem(MediaItem.fromUri(uri))
            player.prepare()
        }
    }

    fun start(resRawId: Int) {
        prepareIfNeeded(resRawId)
        if (enabled && !player.isPlaying) player.play()
    }

    fun pause() {
        if (player.isPlaying) player.pause()
    }

    fun resume() {
        if (enabled && !player.isPlaying) player.play()
    }

    fun stop() {
        if (player.isPlaying) player.pause()
        player.seekTo(0)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: BackgroundMusicController? = null

        fun get(context: Context): BackgroundMusicController {
            return instance ?: synchronized(this) {
                instance ?: BackgroundMusicController(context.applicationContext).also {
                    instance = it
                }
            }
        }
    }
}
