package com.example.maricools_app_designs.hilt

import com.example.maricools_app_designs.database.QuizDao
import com.example.maricools_app_designs.utils.repositories.CatholicQuizRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object QuizRepositoryModule {

    @Singleton
    @Provides
    fun provideQuizSettingRepository(dao: QuizDao): CatholicQuizRepository {
        return CatholicQuizRepository(dao)
    }
}