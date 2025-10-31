package com.example.mission2

data class Song(
    var title: String = "",
    var singer: String = "",
    var second: Int=0,
    val playTime:Int=0,
    var isPlaying: Boolean=false,
    var mills:Int=0
)