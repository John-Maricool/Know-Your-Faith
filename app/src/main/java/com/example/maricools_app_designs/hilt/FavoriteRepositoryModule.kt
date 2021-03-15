package com.example.maricools_app_designs.hilt

import com.example.maricools_app_designs.utils.repositories.FavRepository
import com.example.maricools_app_designs.database.PrayerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object FavoriteRepositoryModule {

    @Singleton
    @Provides
    fun provideFavListRepository(prayerDao: PrayerDao): FavRepository {
        return FavRepository(prayerDao)
    }

}