package com.johnmaricool.mario_designs.hilt

import com.johnmaricool.mario_designs.database.QuizDao
import com.johnmaricool.mario_designs.utils.repositories.CatholicQuizRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object QuizRepositoryModule {

    @Singleton
    @Provides
    fun provideQuizSettingRepository(dao: QuizDao): CatholicQuizRepository {
        return CatholicQuizRepository(dao)
    }
}