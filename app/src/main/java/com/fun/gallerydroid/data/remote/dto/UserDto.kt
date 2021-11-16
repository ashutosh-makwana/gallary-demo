package com.`fun`.gallerydroid.data.remote.dto

import com.`fun`.gallerydroid.domain.model.User


class UserData : ArrayList<UserDto>()

data class UserDto(
    val address: Address,
    val company: Company,
    val email: String,
    val id: Int,
    val name: String,
    val phone: String,
    val username: String,
    val website: String
)

data class Address(
    val city: String,
    val geo: Geo,
    val street: String,
    val suite: String,
    val zipcode: String
)

data class Company(
    val bs: String,
    val catchPhrase: String,
    val name: String
)

data class Geo(
    val lat: String,
    val lng: String
)

fun UserDto.toUser(): User {
    return User(
        id = id,
        address = address,
        name = name,
        email = email,
        phone = phone
    )
}