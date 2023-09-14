package com.belajar.myappuser.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.belajar.myappuser.data.mapper.toModel
import com.belajar.myappuser.data.model.User
import com.belajar.myappuser.data.room.Dao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// class wishList paging source class untuk mengambil data dari ROOM
class WishListPagingSource (private val UserDao: Dao

): PagingSource<Int, User>() {
    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val pageNumber = params.key ?: 1
        val offset = pageNumber * SIZE
        val items = withContext(Dispatchers.IO){
            UserDao.loadAll(offset, SIZE).map { it.toModel() }
        }

        return try {
            //jika hasilnya berhasil
            LoadResult.Page(
                //item dari data yang diambil
                data = items,
                //mengambil item di belakang halaman
                prevKey = if (pageNumber > 0) pageNumber - 1 else null,
                //mengambil item setelah halaman
                nextKey = pageNumber.plus(1)
            )
        } catch (e: Exception) {
            //jika terjadi error
            LoadResult.Error(e)
        }    }

}