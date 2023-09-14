package com.belajar.myappuser.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.belajar.myappuser.data.entity.FavoriteEntity

//anotasi database untuk abstract class database dengan parameter entities, version dan exportschema
@Database(entities = [FavoriteEntity::class], version = 2, exportSchema = false)

abstract class DataBase: RoomDatabase() {

    //untuk memudahkan database mengakses class DAO
    abstract fun Dao(): Dao

    // object di dalam class
    companion object{

        //variable instance dengan anotasi volatile
        @Volatile
        private var INSTANCE : DataBase? = null

        // function untuk getdatabase dari class productDatabase dengan variable instance
        fun getDatabase(context: Context): DataBase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBase::class.java,
                    "item_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE=instance
                return instance
            }
        }
    }
}