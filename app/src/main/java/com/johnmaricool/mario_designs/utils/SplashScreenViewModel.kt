package com.johnmaricool.mario_designs.utils

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.johnmaricool.mario_designs.database.QuizDao
import com.johnmaricool.mario_designs.utils.models.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class SplashScreenViewModel
@Inject constructor( @ApplicationContext var context: Context,
                    private var prayerdao: PrayerDaoImpl,
                    private var factdao: FactsDaoImpl,
                    private var qDao: QuizDao,
                    private var gson: Gson,
                    private var mapper: CacheMapper,
                    private var factMapper: FactMapper, private var quizMapper: QuizMapper) : ViewModel() {

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    private fun getData() {
        val jsonFile = "MY FILES/PRAYERS.json".getJsonDataFromAsset(context)
        val listPrayer = object : TypeToken<List<PrayerServerModel>>() {}.type
        val prayers: List<PrayerServerModel> = gson.fromJson(jsonFile, listPrayer)
        val list = mapper.convertToCacheList(prayers)
        addToRoom(list)
    }

    private fun String.getJsonDataFromAsset(context: Context): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(this).bufferedReader().use {
                it.readText()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return jsonString
    }

    private fun getFactData() {
        val jsonFile = "MY FILES/FACTS.json".getJsonDataFromAsset(context)
        val listFact = object : TypeToken<List<FactServerModel>>() {}.type
        val facts: List<FactServerModel> = gson.fromJson(jsonFile, listFact)
        val list = factMapper.convertToCacheList(facts)
        addToRoomFact(list)
    }

    private fun getQuizData() {
        val jsonFile = "MY FILES/BIBLE QUIZ.json".getJsonDataFromAsset(context)
        val listFact = object : TypeToken<List<QuizModel>>() {}.type
        val quizes: List<QuizModel> = gson.fromJson(jsonFile, listFact)
        val list = quizMapper.convertToCacheList(quizes)
        addToRoomQuiz(list)
    }

    private fun getCatholicQuizData() {
        val jsonFile = "MY FILES/CATHOLIC QUIZ.json".getJsonDataFromAsset(context)
        val listFact = object : TypeToken<List<QuizModel>>() {}.type
        val quizes: List<QuizModel> = gson.fromJson(jsonFile, listFact)
        val list = quizMapper.convertToCacheList(quizes)
        addToRoomQuiz(list)
    }

    fun doAllWork() {
        getFactData()
        getData()
        getQuizData()
        getCatholicQuizData()
    }

    private fun addToRoom(prayer: List<PrayerModel>) {
        viewModelScope.launch(dispatcher) {
            prayerdao.addPrayer(prayer)
        }
    }

    private fun addToRoomFact(fact: List<FactModel>) {
        viewModelScope.launch(dispatcher) {
            factdao.addFact(fact)
        }
    }

    private fun addToRoomQuiz(quiz: List<QuizEntityModel>) {
        viewModelScope.launch(dispatcher) {
            qDao.insertQuiz(quiz)
        }
    }

}