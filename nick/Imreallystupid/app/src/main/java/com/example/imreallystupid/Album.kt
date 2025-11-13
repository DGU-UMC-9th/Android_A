package com.example.imreallystupid

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "albumTable")
data class Album(
    var title: String = "",
    var singer: String = "",
    var coverImg: Int? = null)
    //var songs: ArrayList<Song>? = null)
    {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}