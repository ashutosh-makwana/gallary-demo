package com.`fun`.gallerydroid.data.remote.dto

class PhotoData : ArrayList<PhotoDto>()

data class PhotoDto(
    val albumId: Int,
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
)