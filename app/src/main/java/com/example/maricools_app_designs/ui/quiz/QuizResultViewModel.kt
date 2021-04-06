package com.example.maricools_app_designs.ui.quiz

import android.content.Context
import android.content.SharedPreferences
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.maricools_app_designs.hilt.Reward
import dagger.hilt.android.qualifiers.ApplicationContext

class QuizResultViewModel
    @ViewModelInject
 constructor(@ApplicationContext var context: Context,
             @Reward  var rewardPrefs: SharedPreferences): ViewModel() {

    fun saveToSP(p: Int){
        val editor = rewardPrefs.edit()
        var point = rewardPrefs.getInt("points", 0)
        point += p
            editor.putInt("points", point)
        editor.apply()
    }

    fun updateNumberOfPoints(): Int{
            return rewardPrefs.getInt("points", 0)
    }

     fun arrangeViewsResult(score: Int, wrong: Int): Int{
        val totalQuestions = score + wrong
        val percentage = (score.times(100)).div(totalQuestions)
        if (percentage >= 90){
            saveToSP(10)
        }
         return percentage
    }
}