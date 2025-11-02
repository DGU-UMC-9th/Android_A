package com.example.week4


data class Song(
    var title: String = "",
    var singer: String = "",
    var albumArt: Int? = null,
    var releaseInfo: String = "",
    var isPlaying: Boolean = false
)