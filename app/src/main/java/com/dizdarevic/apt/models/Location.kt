package com.dizdarevic.apt.models

data class Location(
    val city: String,
    val coordinates: Coordinates,
    val country: String,
    val postcode: String,
    val state: String,
    val street: Street,
    val timezone: Timezone
) {
    fun getAdress(): CharSequence? {
        return "${street.name}, ${street.number}, ${state}, ${postcode}, ${country}"
    }
}