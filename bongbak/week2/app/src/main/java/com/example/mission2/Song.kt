package com.example.mission2

data class Song(
    var title: String = "",
    var singer: String = "",
    var second: Int=0,
    val playTime:Int=0,
    var isPlaying: Boolean=false,
    var music: String=""
    // 어떤 음악이 재생되고 있었는지를 확인
)