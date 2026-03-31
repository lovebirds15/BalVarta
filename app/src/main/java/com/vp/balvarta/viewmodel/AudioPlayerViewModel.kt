package com.vp.balvarta.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.common.util.UnstableApi
import androidx.media3.database.StandaloneDatabaseProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

data class AudioPlayerState(
    val isPlaying: Boolean = false,
    val currentPosition: Long = 0L,
    val duration: Long = 0L,
    val progress: Float = 0f,
    val isLoading: Boolean = false,
    val currentStoryId: Int? = null,
    val errorMessage: String? = null,
    val isBuffering: Boolean = false
)

@UnstableApi
class AudioPlayerViewModel : ViewModel() {
    
    private val _playerState = mutableStateOf(AudioPlayerState())
    val playerState: State<AudioPlayerState> = _playerState
    
    private var exoPlayer: ExoPlayer? = null
    private var context: Context? = null
    private var simpleCache: SimpleCache? = null
    
    fun initializePlayer(context: Context) {
        this.context = context
        if (exoPlayer == null) {
            // Initialize cache for audio files (100MB cache)
            val cacheSize = 100L * 1024 * 1024 // 100MB
            val cacheDir = File(context.cacheDir, "audio_cache")
            val evictor = LeastRecentlyUsedCacheEvictor(cacheSize)
            val databaseProvider = StandaloneDatabaseProvider(context)
            
            simpleCache = SimpleCache(cacheDir, evictor, databaseProvider)
            
            // Create data source factory with caching
            val httpDataSourceFactory = DefaultHttpDataSource.Factory()
                .setUserAgent("GujaratiStories/1.0")
                .setConnectTimeoutMs(30000)
                .setReadTimeoutMs(30000)
                .setAllowCrossProtocolRedirects(true)
            
            val defaultDataSourceFactory = DefaultDataSource.Factory(context, httpDataSourceFactory)
            
            val cacheDataSourceFactory = CacheDataSource.Factory()
                .setCache(simpleCache!!)
                .setUpstreamDataSourceFactory(defaultDataSourceFactory)
                .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
            
            // Create media source factory with caching
            val mediaSourceFactory = DefaultMediaSourceFactory(cacheDataSourceFactory)
            
            exoPlayer = ExoPlayer.Builder(context)
                .setMediaSourceFactory(mediaSourceFactory)
                .build().apply {
                    addListener(object : Player.Listener {
                        override fun onPlaybackStateChanged(playbackState: Int) {
                            _playerState.value = _playerState.value.copy(
                                isLoading = playbackState == Player.STATE_BUFFERING,
                                isBuffering = playbackState == Player.STATE_BUFFERING,
                                errorMessage = null
                            )
                        }
                        
                        override fun onIsPlayingChanged(isPlaying: Boolean) {
                            _playerState.value = _playerState.value.copy(
                                isPlaying = isPlaying,
                                errorMessage = null
                            )
                            if (isPlaying) {
                                startProgressUpdates()
                            }
                        }
                        
                        override fun onPlayerError(error: androidx.media3.common.PlaybackException) {
                            _playerState.value = _playerState.value.copy(
                                isLoading = false,
                                isPlaying = false,
                                isBuffering = false,
                                errorMessage = getErrorMessage(error)
                            )
                        }
                    })
                }
        }
    }
    
    private fun getErrorMessage(error: androidx.media3.common.PlaybackException): String {
        return when (error.errorCode) {
            androidx.media3.common.PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_FAILED -> 
                "Network connection failed. Please check your internet connection."
            androidx.media3.common.PlaybackException.ERROR_CODE_IO_BAD_HTTP_STATUS -> 
                "Failed to load audio. Please try again later."
            androidx.media3.common.PlaybackException.ERROR_CODE_IO_FILE_NOT_FOUND -> 
                "Audio file not found."
            else -> "Unable to play audio: ${error.localizedMessage ?: "Unknown error"}"
        }
    }
    
    private fun startProgressUpdates() {
        viewModelScope.launch {
            while (_playerState.value.isPlaying) {
                exoPlayer?.let { player ->
                    val currentPosition = player.currentPosition
                    val duration = player.duration.takeIf { it > 0 } ?: 1L
                    val progress = currentPosition.toFloat() / duration.toFloat()
                    
                    _playerState.value = _playerState.value.copy(
                        currentPosition = currentPosition,
                        duration = duration,
                        progress = progress.coerceIn(0f, 1f)
                    )
                }
                delay(1000)
            }
        }
    }
    
    fun playPause() {
        exoPlayer?.let { player ->
            if (player.isPlaying) {
                player.pause()
            } else {
                player.play()
            }
        }
    }
    
    fun stop() {
        exoPlayer?.let { player ->
            player.pause()
            player.seekTo(0)
            _playerState.value = _playerState.value.copy(
                isPlaying = false,
                currentPosition = 0L,
                progress = 0f,
                errorMessage = null,
                isBuffering = false
            )
        }
    }
    
    fun loadAudio(audioUrl: String, storyId: Int) {
        exoPlayer?.let { player ->
            try {
                if (audioUrl.startsWith("http://") || audioUrl.startsWith("https://")) {
                    // Stream from URL
                    val mediaItem = MediaItem.fromUri(audioUrl)
                    player.setMediaItem(mediaItem)
                    player.prepare()
                    
                    _playerState.value = _playerState.value.copy(
                        currentStoryId = storyId,
                        isLoading = true,
                        currentPosition = 0L,
                        progress = 0f,
                        errorMessage = null,
                        isBuffering = true
                    )
                } else {
                    // Fallback to local resource (legacy support)
                    context?.let { ctx ->
                        val resourceName = audioUrl.substringBeforeLast(".mp3")
                        val resourceId = ctx.resources.getIdentifier(resourceName, "raw", ctx.packageName)
                        
                        if (resourceId != 0) {
                            val uri = androidx.media3.datasource.RawResourceDataSource.buildRawResourceUri(resourceId)
                            val mediaItem = MediaItem.fromUri(uri)
                            player.setMediaItem(mediaItem)
                            player.prepare()
                            
                            _playerState.value = _playerState.value.copy(
                                currentStoryId = storyId,
                                isLoading = true,
                                currentPosition = 0L,
                                progress = 0f,
                                errorMessage = null
                            )
                        } else {
                            _playerState.value = _playerState.value.copy(
                                isLoading = false,
                                isPlaying = false,
                                errorMessage = "Audio file not found: $audioUrl"
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                _playerState.value = _playerState.value.copy(
                    isLoading = false,
                    isPlaying = false,
                    errorMessage = "Error loading audio: ${e.localizedMessage}"
                )
            }
        }
    }
    
    fun seekTo(progress: Float) {
        exoPlayer?.let { player ->
            val seekPosition = (progress * player.duration).toLong()
            player.seekTo(seekPosition)
        }
    }
    
    fun clearError() {
        _playerState.value = _playerState.value.copy(errorMessage = null)
    }
    
    override fun onCleared() {
        super.onCleared()
        exoPlayer?.release()
        exoPlayer = null
        simpleCache?.release()
        simpleCache = null
        context = null
    }
}