package com.johnmaricool.mario_designs.ui.quiz

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johnmaricool.mario_designs.utils.Result
import com.johnmaricool.mario_designs.utils.models.QuizEntityModel
import com.johnmaricool.mario_designs.utils.repositories.CatholicQuizRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class CatholicQuizViewModel
    @ViewModelInject
 constructor(var repo: CatholicQuizRepository
    ): ViewModel() {

    private val _data = MutableLiveData<Result>()
    val data: LiveData<Result> = _data

    init {
        QuizQuestionsToAnswer?.toMutableList()?.clear()
    }

     fun getAnsweredList(id: Int, part: String) {
         _data.value = Result.isLoading
         viewModelScope.launch(IO) {
             _data.postValue(Result.isLoaded(repo.getQuizByPart(part, id)))
         }
    }

    companion object {
        var QuizQuestionsToAnswer: List<QuizEntityModel>? = null
    }
}