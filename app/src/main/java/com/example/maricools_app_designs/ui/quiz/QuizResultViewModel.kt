package com.example.maricools_app_designs.ui.quiz

import android.content.Context
import android.content.SharedPreferences
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.maricools_app_designs.androidcomponents.ApplicationConstants
import com.example.maricools_app_designs.hilt.Reward
import dagger.hilt.android.qualifiers.ApplicationContext

class QuizResultViewModel
    @ViewModelInject
 constructor(@ApplicationContext var context: Context,
             @Reward  var rewardPrefs: SharedPreferences): ViewModel() {

    fun saveToSP(p: Int){
        val editor = rewardPrefs.edit()
        if (rewardPrefs.contains("points")){
            ApplicationConstants.points += p
           editor.putInt("points", ApplicationConstants.points)
        }
        editor.commit()
    }

    fun updateNumberOfPoints(): Int{
        return if (rewardPrefs.contains("points")) {
            rewardPrefs.getInt("points", ApplicationConstants.points)
        }else{
            0
        }
    }

     fun arrangeViewsResult(score: Int, wrong: Int): Int{
        val totalQuestions = score + wrong
        val percentage = (score.times(100)).div(totalQuestions)
        if (percentage >= 50){
            saveToSP(5)
        }
         return percentage
    }
}