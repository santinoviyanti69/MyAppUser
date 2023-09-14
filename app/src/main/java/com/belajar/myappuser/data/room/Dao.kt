package com.belajar.myappuser.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.belajar.myappuser.data.entity.FavoriteEntity
import com.belajar.myappuser.data.entity.TABLE_USER
import kotlinx.coroutines.flow.Flow

// sebagai Objek Akses Data untuk menentukan interaksi database
@Dao
interface Dao {

    //metode untuk insert parameter ke dalam tabel yang sesuai di database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg item: FavoriteEntity)

    //metode yang menggunakan query select untuk menampilkan objek table_products dengan limit dan offset
    @Query("SELECT * FROM $TABLE_USER LIMIT :limit OFFSET :offset")
    fun loadAll(offset: Int, limit: Int): List<FavoriteEntity>

    //metode yang menggunakan query select untuk menampilkan objek table_products dengan where id
    @Query("SELECT * FROM $TABLE_USER WHERE id= :id")
    fun getUserById(id: Int): Flow<FavoriteEntity?>

    //metode untuk delete baris tertentu dari table di database
    @Delete
    suspend fun delete(vararg item: FavoriteEntity)
}
