package com.example.mission2

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AlbumDao {

    @Insert
    fun insert(albume:Album)

    @Query("SELECT*FROM AlbumTable")
    fun getAlbums():List<Album>

    @Insert
    fun likeAlbum(like: Like)

    @Query("SELECT id FROM LikeTable WHERE userId=:userId AND albumId=:albumId")
    fun isLikedAlbum(userId:Int, albumId:Int) : Int?

    @Query("DELETE FROM LikeTable WHERE userId=:userId AND albumId=:albumId")
    fun disLikedAlbum(userId:Int, albumId:Int) : Int?

    @Query("SELECT AT .* FROM LikeTable as LT LEFT JOIN AlbumTable as AT ON LT.albumId=AT.id WHERE LT.userId= :userId")
    fun getLikedAlbums(userId:Int) : List<Album>

}