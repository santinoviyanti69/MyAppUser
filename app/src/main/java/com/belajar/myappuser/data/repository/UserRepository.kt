package com.belajar.myappuser.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.belajar.myappuser.data.mapper.toFavoriteEntity
import com.belajar.myappuser.data.mapper.toModel
import com.belajar.myappuser.data.model.User
import com.belajar.myappuser.data.paging.SIZE
import com.belajar.myappuser.data.paging.UserPagingSource
import com.belajar.myappuser.data.paging.WishListPagingSource
import com.belajar.myappuser.data.remote.Service
import com.belajar.myappuser.data.room.Dao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepository (
    private val service: Service,
    private val Dao: Dao
){

    fun gerFavorite(user: Int): Flow<User?> {
        return Dao.getUserById(user).map {
            it?.toModel()
        }
    }

    suspend fun setFavoriteDelete(User: User) {
        return Dao.delete(User.toFavoriteEntity())
    }

    suspend fun setFavoriteInsert(User: User) {
        return Dao.insert(User.toFavoriteEntity())
    }
    suspend fun getListUser(username: String?): User {
        return service.getUsername(username).toModel()
    }

    fun getFavoriteUser(): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(pageSize = SIZE, enablePlaceholders = false),
            pagingSourceFactory = { WishListPagingSource(Dao) }
        )
            .flow
    }

    //untuk menangani cache dalam memori dan meminta data saat user ada di akhir page
    fun getUsers(): Flow<PagingData<User>> {
        return Pager (
            config = PagingConfig(pageSize = SIZE, enablePlaceholders = false),
            pagingSourceFactory = {UserPagingSource(service)}
        )
            .flow
    }
}