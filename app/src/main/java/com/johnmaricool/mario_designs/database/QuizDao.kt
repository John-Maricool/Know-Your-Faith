package com.johnmaricool.mario_designs.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.johnmaricool.mario_designs.utils.models.QuizEntityModel

@Dao
interface QuizDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
suspend fun insertQuiz(quiz: List<QuizEntityModel>)

    @Query("select * from QUIZ where quiz = :part order by RANDOM() limit :q")
    suspend fun getQuizWherePart(part: String, q: Int): List<QuizEntityModel>
}

