package com.example.maricools_app_designs.hilt

import com.example.maricools_app_designs.database.PrayerDao
import com.example.maricools_app_designs.utils.repositories.PrayerListRepository
import com.example.maricools_app_designs.utils.repositories.PrayerRepository
import com.google.firebase.firestore.FirebaseFirestore
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