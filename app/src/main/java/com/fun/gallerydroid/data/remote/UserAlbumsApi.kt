package com.`fun`.gallerydroid.data.remote

import com.`fun`.gallerydroid.data.remote.dto.AlbumData
import com.`fun`.gallerydroid.data.remote.dto.PhotoData
import com.`fun`.gallerydroid.data.remote.dto.UserData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserAlbumsApi {

    @GET("/users")
    suspend fun getUsers(): UserData

    @GET("/albums")
    suspend fun getAlbumsByUser(@Query("userId") userId: Int): AlbumData

    @GET("/photos")
    suspend fun getPhotosByAlbum(@Path("coinId") coinId: Int): PhotoData
}