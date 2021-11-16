package com.`fun`.gallerydroid.domain.usecase

import com.`fun`.gallerydroid.common.Resource
import com.`fun`.gallerydroid.data.remote.dto.AlbumDto
import com.`fun`.gallerydroid.data.remote.dto.PhotoDto
import com.`fun`.gallerydroid.data.remote.dto.toUser
import com.`fun`.gallerydroid.domain.model.User
import com.`fun`.gallerydroid.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAlbumsUseCase @Inject constructor(
    private val repository: UserRepository
) {
    fun getAlbums(id: Int): Flow<Resource<List<AlbumDto>>> = flow {
        try {
            emit(Resource.Loading<List<AlbumDto>>())
            val albums = repository.getUserAlbums(id)
            emit(Resource.Success<List<AlbumDto>>(albums))
        } catch (e: HttpException) {
            emit(Resource.Error<List<AlbumDto>>(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error<List<AlbumDto>>("Couldn't reach server. Check your internet connection."))
        }
    }
}