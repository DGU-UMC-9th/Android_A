package com.example.week4

data class Album(
    val title: String = "",
    val singer: String = "",
    val coverImg: Int,
    val songs: ArrayList<Song>? = null
)