package com.dizdarevic.apt.models

import com.google.gson.annotations.SerializedName

data class RandomUser(
    val info: Info,

    @SerializedName("results")
    val userList: List<User>
)