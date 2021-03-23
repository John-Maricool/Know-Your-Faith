package com.example.maricools_app_designs.hilt

import com.example.maricools_app_designs.database.PrayerDao
import com.example.maricools_app_designs.database.QuizDao
import com.example.maricools_app_designs.utils.repositories.PrayerListRepository
import com.example.maricools_app_designs.utils.repositories.PrayerRepository
import com.example.maricools_app_designs.utils.repositories.QuizSettingRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object QuizRepositoryModule {

    @Singleton
    @Provides
    fun provideQuizSettingRepository(dao: QuizDao): QuizSettingRepository {
        return QuizSettingRepository(dao)
    }
}