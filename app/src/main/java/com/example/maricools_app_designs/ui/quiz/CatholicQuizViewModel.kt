package com.example.maricools_app_designs.ui.quiz

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.maricools_app_designs.interfaces_kids.StateInterface
import com.example.maricools_app_designs.utils.models.QuizEntityModel
import com.example.maricools_app_designs.utils.repositories.CatholicQuizRepository
import java.util.concurrent.CountDownLatch

class CatholicQuizViewModel
    @ViewModelInject
 constructor(var repo: CatholicQuizRepository
    ): ViewModel() {

    private val latch = CountDownLatch(1)

     fun getAnsweredList(id: Int, part: String) {
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
    }

    companion object {
        var AllQuizQuestions: MutableList<QuizEntityModel> = mutableListOf()
        var QuizQuestionsToAnswer: MutableList<QuizEntityModel> = mutableListOf()
    }
}