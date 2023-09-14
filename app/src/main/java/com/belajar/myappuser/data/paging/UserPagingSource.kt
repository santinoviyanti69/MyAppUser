package com.belajar.myappuser.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.belajar.myappuser.data.mapper.toModel
import com.belajar.myappuser.data.model.User
import com.belajar.myappuser.data.remote.Service

// ketika ingin berbagi nilai antar class di dalam file
const val STARTING_KEY = 0
const val SIZE = 30


// class paging source //class untuk mengambil data dari API
class UserPagingSource  (

    // private val agar tidak dapat diubah oleh class lain
    private val service: Service
): PagingSource<Int, User>() {

    // ovveride fun proses penulisan ulang fungsi yang ada di Parent Class dari dalam Child class
    // getRefreshkey untuk merefresh data pada aplikasi
    // //untuk menyediakan key yang akan digunakan untuk memuat PagingSource
    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)

        }
    }

    //untuk mengambil lebih banyak data yang akan ditampilkan saat user melakukan scroll secara asinkron
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val pageNumber = params.key ?: STARTING_KEY


        return try {
            val items = service.getUsers(SIZE, pageNumber ).toModel()

            //jika hasilnya berhasil
            LoadResult.Page(
                //item dari data yang diambil
                data = items,
                //mengambil item di belakang halaman
                prevKey = if (pageNumber > 0) pageNumber - 1 else null,
                //mengambil item setelah halaman
                nextKey = pageNumber.plus(1)

            )

        //memanggil tindakan tertentu dengan pengecualian yang tertangkap
        } catch (e: Exception) {
            Log.e("nama anda", e.message.toString())

            //jika terjadi error
            LoadResult.Error(e)


        }
    }
}
