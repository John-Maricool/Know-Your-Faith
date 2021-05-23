package com.johnmaricool.mario_designs.hilt

import com.johnmaricool.mario_designs.database.PrayerDao
import com.johnmaricool.mario_designs.utils.repositories.PrayerListRepository
import com.johnmaricool.mario_designs.utils.repositories.PrayerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object PrayerRepositoryModule {

    @Singleton
    @Provides
    fun providePrayerListRepository(dao: PrayerDao): PrayerListRepository {
        return PrayerListRepository(dao)
    }

    @Singleton
    @Provides
    fun providePrayerRepository(prayerDao: PrayerDao): PrayerRepository {
        return PrayerRepository(prayerDao)
    }
}