package com.example.exodashhls

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.media3.common.MediaItem
import androidx.media3.common.util.Util
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

class MainActivity : ComponentActivity() {

    private var player: ExoPlayer? = null
    private lateinit var playerView: PlayerView

    // Replace or pass via Intent extra "url" (String) to override
    private val defaultHlsUrl = "https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8"
    private val defaultDashUrl = "https://storage.googleapis.com/exoplayer-test-media-1/gen-3/mpds/tears_of_steel/tears_of_steel.mpd"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        playerView = findViewById(R.id.player_view)
    }

    private fun initializePlayer() {
        if (player != null) return
        player = ExoPlayer.Builder(this).build().also { exoPlayer ->
            playerView.player = exoPlayer

            val providedUrl = intent?.getStringExtra("url")
            val useDash = intent?.getBooleanExtra("dash", false) ?: false
            val url = when {
                providedUrl != null -> providedUrl
                useDash -> defaultDashUrl
                else -> defaultHlsUrl
            }

            val mediaItem = MediaItem.fromUri(Uri.parse(url))
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
            exoPlayer.playWhenReady = true
        }
    }

    private fun releasePlayer() {
        playerView.player = null
        player?.release()
        player = null
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        if (Util.SDK_INT < 24 || player == null) {
            initializePlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
    }
}