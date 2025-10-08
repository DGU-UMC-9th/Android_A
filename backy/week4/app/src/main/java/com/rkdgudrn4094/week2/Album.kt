package com.rkdgudrn4094.week2

data class Album(
    var title: String?="",
    var singer: String?="",
    var coverImg: Int?= null,
    var songs: ArrayList<Song>?= null
)
