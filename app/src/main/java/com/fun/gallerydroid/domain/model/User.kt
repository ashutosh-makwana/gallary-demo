package com.`fun`.gallerydroid.domain.model

import com.`fun`.gallerydroid.data.remote.dto.Address


data class User(
    val address: Address,
    val email: String,
    val id: Int,
    val name: String,
    val phone: String
)
