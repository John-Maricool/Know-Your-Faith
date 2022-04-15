package com.johnmaricool.mario_designs.ui.quiz

import android.content.Context
import android.content.SharedPreferences
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class QuizResultViewModel
@Inject constructor(@ApplicationContext var context: Context): ViewModel() {


     fun arrangeViewsResult(score: Int, wrong: Int): Int{
        val totalQuestions = score + wrong
        val percentage = (score.times(100)).div(totalQuestions)

         return percentage
    }
}