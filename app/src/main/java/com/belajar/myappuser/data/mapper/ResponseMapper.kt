package com.belajar.myappuser.data.mapper

import com.belajar.myappuser.data.model.User
import com.belajar.myappuser.data.response.UserItemResponse

// fun sebagai fungsi elemen penyusun
// untuk mapping dataclass dari UserResponse ke listuser
fun List<UserItemResponse>.toModel(): List<User>{
    return this.map {
        it.toModel()
    }
}

// fun sebagai fungsi elemen penyusun
// untuk mapping dataclass dari UserResponse ke list user

fun UserItemResponse.toModel() =
    User(
        avatar_url,
        events_url,
        followers_url,
        following_url,
        gists_url,
        gravatar_id,
        html_url,
        id,
        login,
        node_id,
        organizations_url,
        received_events_url,
        repos_url,
        site_admin,
        starred_url,
        subscriptions_url,
        type,
        url

    )