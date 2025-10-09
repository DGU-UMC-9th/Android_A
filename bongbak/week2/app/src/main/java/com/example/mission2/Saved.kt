package com.example.mission2

data class Saved(
    var title:String?="",
    var singer:String?="",
    var coverImg : Int? =null,
    var songs: ArrayList<Song>?=null
)
