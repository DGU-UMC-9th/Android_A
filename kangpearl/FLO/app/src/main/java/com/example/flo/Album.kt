package com.example.flo
//테스트
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AlbumTable")
data class Album(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var title: String = "",
    var singer: String = "",
    var coverImg: Int? = null
)
//테스트