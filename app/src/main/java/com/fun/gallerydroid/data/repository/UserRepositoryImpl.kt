package com.`fun`.gallerydroid.data.repository

import android.util.Log
import com.`fun`.gallerydroid.common.Constants.TAG
import com.`fun`.gallerydroid.data.remote.UserAlbumsApi
import com.`fun`.gallerydroid.data.remote.dto.AlbumDto
import com.`fun`.gallerydroid.data.remote.dto.PhotoDto
import com.`fun`.gallerydroid.data.remote.dto.UserDto
import com.`fun`.gallerydroid.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: UserAlbumsApi
) : UserRepository {
    override suspend fun getUser(): List<UserDto> {
        Log.e(TAG, "${api.getUsers()}")
        return api.getUsers()
    }

    override suspend fun getUserAlbums(id: Int): List<AlbumDto> {
        Log.e(TAG, "${api.getAlbumsByUser(id)}")
        return api.getAlbumsByUser(id)
    }

    override suspend fun getAlbumPhotos(id: Int): List<PhotoDto> {
        Log.e(TAG, "${api.getPhotosByAlbum(id)}")
        return api.getPhotosByAlbum(id)
    }
}