package com.`fun`.gallerydroid.data.remote.dto

class AlbumData : ArrayList<AlbumDto>()

data class AlbumDto(
    val id: Int,
    val title: String,
    val userId: Int,
    var photos: List<String> = listOf()
)