package com.dizdarevic.apt.repository

import com.dizdarevic.apt.Constants.API
import com.dizdarevic.apt.models.RandomUser
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface RetrofitService {
    @GET("api/")
    fun getUsers(@QueryMap queryMap: Map<String, String>): Call<RandomUser>

    companion object {
        var retrofitService: RetrofitService? = null

        fun getInstance() : RetrofitService {

            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}