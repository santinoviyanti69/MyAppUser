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

    //untuk manambahkan feature favorite
    //mengambil data dari dao dengan mengambil user tapi lewat parameter id
    fun getFavorite(user: Int): Flow<User?> {
        return Dao.getUserById(user).map {
            it?.toModel()
        }
    }

    //untuk menghapus favorite
    //mengambil data dari dao menghapus dengan parameter user
    suspend fun setFavoriteDelete(User: User) {
        return Dao.delete(User.toFavoriteEntity())
    }

    //untuk menambahkan favorite
    //mengambil data dari dao menambahkan dengan parameter user
    suspend fun setFavoriteInsert(User: User) {
        return Dao.insert(User.toFavoriteEntity())
    }

    //mengambil detail user
    //mengambil data dari service dengan parameter username dengan mengubah useritemrespons ke user
    suspend fun getUser(username: String?): User {
        return service.getUser(username).toModel()
    }

    fun getFavoriteUser(): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(pageSize = SIZE, enablePlaceholders = false),
            pagingSourceFactory = { WishListPagingSource(Dao) }
        )
            .flow
    }

    fun getUsers(): Flow<PagingData<User>> {
        return Pager (
            config = PagingConfig(pageSize = SIZE, enablePlaceholders = false),
            pagingSourceFactory = {UserPagingSource(service)}
        )
            .flow
    }
}