package com.johnmaricool.mario_designs.ui.quiz

import android.os.CountDownTimer
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.johnmaricool.mario_designs.utils.models.QuizEntityModel
import com.johnmaricool.mario_designs.utils.repositories.QuizCustomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuizCustomViewModel
@Inject    constructor(): ViewModel(){

    val repo = QuizCustomRepository()
   lateinit var downloadedQuestions: List<QuizEntityModel>

    init {
        if (repo.GottenQuestions != null){
            downloadedQuestions = repo.GottenQuestions
        }
    }

    val totalSize = downloadedQuestions.size
    var isAnswered = false
    var correctAnswers = 0
    lateinit var itemClick: onTimeClick

    var questionIndex: Int = 0
    var questionCount = 1
    lateinit var currentQuiz: QuizEntityModel
    lateinit var countDownTimer: CountDownTimer

    fun startTimer(listener: onTimeClick){

        itemClick = listener
        val timeToAnswer = currentQuiz.timer
        countDownTimer = object: CountDownTimer(timeToAnswer*1000, 10){
            override fun onFinish() {
                itemClick.onFinishSelected()
            }
            override fun onTick(p0: Long) {
                itemClick.onTickSelected(p0, timeToAnswer)
            }
        }
    }

    interface onTimeClick{
        fun onTickSelected(p0: Long, ttA: Long)
        fun onFinishSelected()
    }

}