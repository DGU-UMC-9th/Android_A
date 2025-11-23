package com.example.mission2

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "SongTable")
data class Song(
    var title: String = "",
    var singer: String = "",
    var second: Int=0,
    val playTime:Int=0,
    var isPlaying: Boolean=false,
    var music: String="",
    var coverImg:Int?=null,
    var isLike:Boolean=false

    // 어떤 음악이 재생되고 있었는지를 확인
){
    @PrimaryKey(autoGenerate=true)var id:Int=0
}