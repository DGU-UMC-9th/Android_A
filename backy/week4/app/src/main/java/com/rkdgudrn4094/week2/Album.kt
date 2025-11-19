package com.rkdgudrn4094.week2

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AlbumTable")
data class Album(
    @PrimaryKey(autoGenerate = false) var id: Int = 0,
    var title: String?="",
    var singer: String?="",
    var coverImg: Int?= null

    /*
    var title: String?="",
    var singer: String?="",
    var coverImg: Int?= null,
    var songs: ArrayList<Song>?= null

     */



    /*
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var title: String = "",
    var singer: String = "",
    var isLike: Boolean = false,
    var coverImg: Int? = null*/
)
