package com.example.maricools_app_designs.hilt

import android.content.Context
import com.example.maricools_app_designs.database.FactDao
import com.example.maricools_app_designs.database.PrayerDao
import com.example.maricools_app_designs.utils.repositories.FactsListRepository
import com.example.maricools_app_designs.utils.repositories.FactsRepository
import com.example.maricools_app_designs.utils.repositories.PrayerListRepository
import com.example.maricools_app_designs.utils.repositories.PrayerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object FactRepositoryModule {

    @Singleton
    @Provides
    fun provideFactListRepository(dao: FactDao, @ApplicationContext context: Context): FactsListRepository {
        return FactsListRepository(dao, context)
    }

    @Singleton
    @Provides
    fun provideFactRepository(dao: FactDao, scope: CoroutineScope): FactsRepository {
        return FactsRepository(dao, scope)
    }
}