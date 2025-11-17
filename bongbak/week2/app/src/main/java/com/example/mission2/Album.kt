package com.example.mission2

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="AlbumTable")
data class Album(
    var title:String?="",
    var singer:String?="",
    var coverImg : Int? =null,
    var isLike: Boolean = false,
    var songs: ArrayList<Song>?=null
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}
