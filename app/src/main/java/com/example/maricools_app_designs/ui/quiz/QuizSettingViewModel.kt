package com.example.maricools_app_designs.ui.quiz

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.maricools_app_designs.interfaces_kids.StateInterface
import com.example.maricools_app_designs.utils.models.QuizModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source

class QuizSettingViewModel
    @ViewModelInject
 constructor( var userFirestore: FirebaseFirestore
    ): ViewModel() {

    lateinit var state: StateInterface
    fun setStateInterfaceListener(listener: StateInterface){
        state = listener
    }

    fun clearAllLists(){
        if (AllQuizQuestions.isNotEmpty() && QuizQuestionsToAnswer.isNotEmpty()) {
            AllQuizQuestions.clear()
            QuizQuestionsToAnswer.clear()
        }
    }

     fun getAllQuestions(selection: String, items: Int){
         state.isLoading()
        val collection: CollectionReference =
                userFirestore.collection(selection)
            collection.get(Source.CACHE).addOnCompleteListener { taskQuerySnapshot ->
               if (taskQuerySnapshot.result.isEmpty){
                   state.notLoaded()
               }
                if (taskQuerySnapshot.isSuccessful) {
                    for (query in taskQuerySnapshot.result!!) {
                        val quest = query.toObject(QuizModel::class.java)
                        if (quest.question.isNotEmpty()) {
                            AllQuizQuestions.add(quest)
                        }
                    }
                    getAnsweredList(items)
                } else {
                  state.notLoaded()
                }
            }
    }

    private fun getAnsweredList(id: Int = 10) {
        if (AllQuizQuestions.size == 0){
            state.notLoaded()
        }else {
            for (i in 0 until id) {
                val randomNumber: Int = (Math.random() * AllQuizQuestions.size - 1).toInt()
                QuizQuestionsToAnswer.add(AllQuizQuestions[randomNumber])
                Log.d("START", QuizQuestionsToAnswer[i].question)
                AllQuizQuestions.removeAt(randomNumber)
            }
            state.fullyLoaded()
        }
    }

    companion object {
        val AllQuizQuestions: MutableList<QuizModel> = mutableListOf()
        val QuizQuestionsToAnswer: MutableList<QuizModel> = mutableListOf()
    }
}