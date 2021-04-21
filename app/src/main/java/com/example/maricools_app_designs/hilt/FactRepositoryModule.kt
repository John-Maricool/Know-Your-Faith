package com.example.maricools_app_designs.hilt

import com.example.maricools_app_designs.database.FactDao
import com.example.maricools_app_designs.utils.repositories.FactsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object FactRepositoryModule {

    @Singleton
    @Provides
    fun provideFactRepository(dao: FactDao): FactsRepository {
        return FactsRepository(dao)
    }
}