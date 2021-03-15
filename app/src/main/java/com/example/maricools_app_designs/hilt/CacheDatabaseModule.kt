package com.example.maricools_app_designs.hilt

import android.content.Context
import com.example.maricools_app_designs.database.PrayerDao
import com.example.maricools_app_designs.database.CacheDatabase
import com.example.maricools_app_designs.database.FactDao
import com.example.maricools_app_designs.database.OOMDao
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
    fun provideOOMDAO(cacheDatabase: CacheDatabase): OOMDao {
        return cacheDatabase.oomDao()
    }
}