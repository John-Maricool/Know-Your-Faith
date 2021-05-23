package com.johnmaricool.mario_designs.hilt

import android.content.Context
import com.johnmaricool.mario_designs.database.CacheDatabase
import com.johnmaricool.mario_designs.database.FactDao
import com.johnmaricool.mario_designs.database.PrayerDao
import com.johnmaricool.mario_designs.database.QuizDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)

object CacheDatabaseModule {


    @Singleton
    @Provides
    fun provideCacheDatabase(@ApplicationContext context: Context): CacheDatabase {
        return CacheDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun providePrayerDAO(cacheDatabase: CacheDatabase): PrayerDao {
        return cacheDatabase.prayerDao()
    }

    @Singleton
    @Provides
    fun provideFactDAO(cacheDatabase: CacheDatabase): FactDao {
        return cacheDatabase.factDao()
    }


    @Singleton
    @Provides
    fun provideQuizDAO(cacheDatabase: CacheDatabase): QuizDao {
        return cacheDatabase.quiDao()
    }
}