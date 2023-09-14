package com.belajar.myappuser.data.remote

import com.belajar.myappuser.data.response.UserItemResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//untuk mengambil data ListUser dan id ListUSer dari API
interface Service {

    @GET("users")
    suspend fun getUsers(
        @Query("per_page") size: Int,
        @Query("since") startUserId: Int
    ): List<UserItemResponse>


    @GET("users/{username}")
    suspend fun getUsername(
        @Path("username") username: String?
    ): UserItemResponse

}