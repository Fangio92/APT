package com.dizdarevic.apt.repository

import com.dizdarevic.apt.models.RandomUser
import retrofit2.Call

class MainRepository(
    private val retrofitService: RetrofitService
) {
    fun getUsers(map: HashMap<String, String>): Call<RandomUser> {
        return retrofitService.getUsers(map)
    }
}