package com.`fun`.gallerydroid.data.remote.dto

class AlbumData : ArrayList<AlbumDtoItem>()

data class AlbumDtoItem(
    val id: Int,
    val title: String,
    val userId: Int
)