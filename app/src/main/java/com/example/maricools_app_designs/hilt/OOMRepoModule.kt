package com.example.maricools_app_designs.hilt

import com.example.maricools_app_designs.database.OOMDao
import com.example.maricools_app_designs.utils.repositories.OOMRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object OOMRepoModule {

    @Singleton
    @Provides
    fun provideOOMRepository(oomDao: OOMDao): OOMRepository {
        return OOMRepository(oomDao)
    }
}