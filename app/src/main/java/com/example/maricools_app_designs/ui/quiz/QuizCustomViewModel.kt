package com.example.maricools_app_designs.ui.quiz

import android.os.CountDownTimer
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.maricools_app_designs.utils.models.QuizModel
import com.example.maricools_app_designs.utils.repositories.QuizCustomRepository

class QuizCustomViewModel
    @ViewModelInject
    constructor(): ViewModel(){

    val repo = QuizCustomRepository()
   var downloadedQuestions: MutableList<QuizModel>

    init {
        downloadedQuestions = repo.GottenQuestions
    }

    var isAnswered = false
    var correctAnswers = 0
    lateinit var itemClick: onTimeClick

    var questionIndex: Int = 0
    var questionCount = 1
    lateinit var currentQuiz: QuizModel
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