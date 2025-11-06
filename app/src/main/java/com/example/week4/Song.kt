package com.example.week4
data class Song(
    var title: String = "",
    var singer: String = "",
    var second: Int = 0,
    var playTime: Int = 0,
    var isPlaying: Boolean = false,
    var musicResId: Int = 0,
    var albumArt: Int? = null
)