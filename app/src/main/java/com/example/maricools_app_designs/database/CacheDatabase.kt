package com.example.maricools_app_designs.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.maricools_app_designs.utils.models.*
import kotlinx.coroutines.*

@Database(entities = [PrayerModel::class,
                        PrayerFavModel::class,
                        FactModel::class,
                        FactsFavModel::class,
                        QuizEntityModel::class], version = 1, exportSchema = false)
abstract class CacheDatabase: RoomDatabase() {
    abstract fun prayerDao(): PrayerDao
    abstract fun factDao(): FactDao
    abstract fun quiDao(): QuizDao

    companion object {
        @Volatile
        var INSTANCE: CacheDatabase? = null

        fun getDatabase(
                context: Context
        ): CacheDatabase {
            return INSTANCE
                    ?: synchronized(this){
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        CacheDatabase::class.java,
                        "prayer_database"
                )
                        .allowMainThreadQueries()
                        .build()
                INSTANCE = instance
                instance
            }
        }
    }
 }