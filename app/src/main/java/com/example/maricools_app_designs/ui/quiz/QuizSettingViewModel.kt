package com.example.maricools_app_designs.ui.quiz

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.maricools_app_designs.interfaces_kids.StateInterface
import com.example.maricools_app_designs.utils.models.QuizEntityModel
import com.example.maricools_app_designs.utils.repositories.QuizSettingRepository
import java.util.concurrent.CountDownLatch

class QuizSettingViewModel
    @ViewModelInject
 constructor(var repo: QuizSettingRepository
    ): ViewModel() {

    lateinit var state: StateInterface
    private val latch = CountDownLatch(1)
    fun setStateInterfaceListener(listener: StateInterface){
        state = listener
    }

     fun getAnsweredList(id: Int, part: String) {
             state.isLoading()
               AllQuizQuestions = repo.getQuizByPart(part).toMutableList()
                    Log.i("quizQuestions", AllQuizQuestions.size.toString())
                    latch.countDown()
                    latch.await()
                    QuizQuestionsToAnswer.clear()

                  for (i in 0 until id) {
                 val randomNumber: Int = (0 until AllQuizQuestions.size).random()
                      Log.i("quizQuestions", randomNumber.toString())
                 QuizQuestionsToAnswer.add(AllQuizQuestions[randomNumber])
                 AllQuizQuestions.removeAt(randomNumber)
             }
         Log.i("quizQuestion", QuizQuestionsToAnswer.toString())
         state.fullyLoaded()
    }

    companion object {
        var AllQuizQuestions: MutableList<QuizEntityModel> = mutableListOf()
        var QuizQuestionsToAnswer: MutableList<QuizEntityModel> = mutableListOf()
    }
}