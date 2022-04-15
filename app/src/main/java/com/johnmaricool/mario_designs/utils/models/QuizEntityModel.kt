package com.johnmaricool.mario_designs.utils.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "QUIZ", indices = [Index(value = ["question"], unique = true)])
 data class QuizEntityModel(
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = "question")var question: String,
        @ColumnInfo(name = "quiz")var quiz: String,
        @ColumnInfo(name = "firstOption")var firstOption: String,
        @ColumnInfo(name = "secondOption")var secondOption: String,
        @ColumnInfo(name = "thirdOption")var thirdOption: String,
        @ColumnInfo(name = "correctOption")var correctOption: String,
        @ColumnInfo(name = "timer")var timer: Long){
}