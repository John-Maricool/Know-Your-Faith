package com.example.maricools_app_designs.hilt

import com.example.maricools_app_designs.database.FactDao
import com.example.maricools_app_designs.utils.repositories.FavFactRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object FavFactRepoModule {

    @Singleton
    @Provides
    fun provideFavListRepository(fDao: FactDao): FavFactRepository {
        return FavFactRepository(fDao)
    }

}