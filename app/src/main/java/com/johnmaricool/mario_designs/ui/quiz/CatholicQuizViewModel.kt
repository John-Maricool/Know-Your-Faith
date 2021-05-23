package com.johnmaricool.mario_designs.ui.quiz

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.johnmaricool.mario_designs.utils.models.QuizEntityModel
import com.johnmaricool.mario_designs.utils.repositories.CatholicQuizRepository
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

                    //shuffle all the questions
                    AllQuizQuestions.shuffle()

                  for (i in 0 until id) {
                      if (i < AllQuizQuestions.size) {
                          QuizQuestionsToAnswer.add(AllQuizQuestions[i])
                          AllQuizQuestions.removeAt(i)
                      }
             }
         AllQuizQuestions.clear()
    }

    companion object {
        var AllQuizQuestions: MutableList<QuizEntityModel> = mutableListOf()
        var QuizQuestionsToAnswer: MutableList<QuizEntityModel> = mutableListOf()
    }
}