package com.dizdarevic.apt.models

data class User(
    val cell: String,
    val dob: Dob,
    val email: String,
    val gender: String,
    val id: Id,
    val location: Location,
    val login: Login,
    val name: Name,
    val nat: String,
    val phone: String,
    val picture: Picture,
    val registered: Registered
) {
    fun getName(): String {
        return "${name.title} ${name.first} ${name.last}"
    }
}