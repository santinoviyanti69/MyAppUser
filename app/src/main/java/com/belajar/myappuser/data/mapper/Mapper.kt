package com.belajar.myappuser.data.mapper

import com.belajar.myappuser.data.entity.FavoriteEntity
import com.belajar.myappuser.data.model.User

// fun sebagai fungsi elemen penyusun
// untuk mapping dataclass User ke data class favorite entity

fun User.toFavoriteEntity()=
    FavoriteEntity(
        avatar_url = this.avatar_url,
        events_url = this.events_url,
        followers_url = this.followers_url,
        following_url = this.following_url,
        gists_url = this.gists_url,
        gravatar_id = this.gravatar_id,
        html_url = this.html_url,
        id = this.id,
        login = this.login,
        node_id = this.node_id,
        organizations_url = this.organizations_url,
        received_events_url = this.received_events_url,
        repos_url = this.repos_url,
        site_admin = this.site_admin,
        starred_url = this.starred_url,
        subscriptions_url = this.subscriptions_url,
        type = this.type,
        url = this.url
    )