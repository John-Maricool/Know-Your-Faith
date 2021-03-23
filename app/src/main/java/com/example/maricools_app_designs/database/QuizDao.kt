package com.example.maricools_app_designs.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.maricools_app_designs.utils.models.QuizEntityModel

@Dao
interface QuizDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
fun insertQuiz(quiz: List<QuizEntityModel>)

    @Query("select * from QUIZ where quiz = :part")
     fun getQuizWherePart(part: String): List<QuizEntityModel>
}