package com.belajar.myappuser.data.response

import com.google.gson.annotations.SerializedName

//untuk menyimpan data item dari UserResponse
data class UserResponse(

    @field:SerializedName("total")
    val total: Int,

    @field:SerializedName("limit")
    val limit: Int,

    @field:SerializedName("skip")
    val skip: Int,

    @field:SerializedName("users")
    val ListUsers: List<UserItemResponse>

)

//untuk menyimpan data item dari UserItemResponse
data class UserItemResponse(

    @field:SerializedName("avatar_url")
    val avatar_url: String,

    @field:SerializedName("events_url")
    val events_url: String,

    @field:SerializedName("followers_url")
    val followers_url: String,

    @field:SerializedName("following_url")
    val following_url: String,

    @field:SerializedName("gists_url")
    val gists_url: String,

    @field:SerializedName("gravatar_id")
    val gravatar_id: String,

    @field:SerializedName("html_url")
    val html_url: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("node_id")
    val node_id: String,

    @field:SerializedName("organizations_url")
    val organizations_url: String,

    @field:SerializedName("received_events_url")
    val received_events_url: String,

    @field:SerializedName("repos_url")
    val repos_url: String,

    @field:SerializedName("site_admin")
    val site_admin: Boolean,

    @field:SerializedName("starred_url")
    val starred_url: String,

    @field:SerializedName("subscriptions_url")
    val subscriptions_url: String,

    @field:SerializedName("type")
    val type: String,

    @field:SerializedName("url")
    val url: String

)

