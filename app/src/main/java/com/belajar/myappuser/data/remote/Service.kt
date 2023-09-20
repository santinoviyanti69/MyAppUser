package com.belajar.myappuser.data.remote

import com.belajar.myappuser.data.response.UserItemResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//untuk mengambil data User dan username USer dari API
interface Service {

    @GET("users")
    suspend fun getUsers(
        @Query("per_page") size: Int,
        @Query("since") startUserId: Int
    ): List<UserItemResponse>


    //untuk mengambil detail user berdasarkan username dan endpoint users/username
    @GET("users/{username}")
    suspend fun getUser(
        @Path("username") username: String?
    ): UserItemResponse

}