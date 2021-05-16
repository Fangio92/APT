package com.dizdarevic.apt.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dizdarevic.apt.repository.MainRepository
import com.dizdarevic.apt.models.RandomUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(
    private val repository: MainRepository
) : ViewModel() {
    val users = MutableLiveData<RandomUser>()
    val errorMessage = MutableLiveData<String>()

    fun getUsers() {
        val map = HashMap<String, String>()
        map.put("page","3")
        map.put("results","20")

        val response = repository.getUsers(map)
        response.enqueue(object : Callback<RandomUser> {
            override fun onResponse(call: Call<RandomUser>, response: Response<RandomUser>) {
                users.postValue(response.body())
            }
            override fun onFailure(call: Call<RandomUser>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

    init {
        getUsers()
    }
}