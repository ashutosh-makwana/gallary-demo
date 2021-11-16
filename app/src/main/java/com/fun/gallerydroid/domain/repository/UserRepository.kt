package com.`fun`.gallerydroid.domain.repository

import com.`fun`.gallerydroid.data.remote.dto.AlbumDto
import com.`fun`.gallerydroid.data.remote.dto.PhotoDto
import com.`fun`.gallerydroid.data.remote.dto.UserDto

interface UserRepository {

    suspend fun getUser(): List<UserDto>

    suspend fun getUserAlbums(id: Int): List<AlbumDto>

    suspend fun getAlbumPhotos(id: Int): List<PhotoDto>
}