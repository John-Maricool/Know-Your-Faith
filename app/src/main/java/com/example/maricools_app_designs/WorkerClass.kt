package com.example.maricools_app_designs

import android.app.Notification
import android.content.Context
import android.os.Build.VERSION_CODES.N
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.example.maricools_app_designs.androidcomponents.MyApplication
import com.example.maricools_app_designs.database.CacheDatabase
import com.example.maricools_app_designs.utils.models.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import java.util.concurrent.CountDownLatch
import javax.inject.Inject

class WorkerClass
@Inject
constructor(@ApplicationContext var context: Context,
            params: WorkerParameters): CoroutineWorker(context, params) {

    private val auth = FirebaseAuth.getInstance()
     private var database = CacheDatabase.getDatabase(context)
    private val cloud = FirebaseFirestore.getInstance()
    private val latch = CountDownLatch(N)
    val gson = Gson()

     override suspend fun doWork(): Result {
         return try {
             doAllWork()
             latch.await()
             Result.success()
         } catch (e: Exception) {
             Result.failure()
         }
    }

    private fun addToRoomQuiz(quiz: List<QuizEntityModel>) {
       //quizDow.insertQuiz(quiz)
    }

    private fun doAWork(){
        if (auth.currentUser == null) {
            auth.signInAnonymously()
                    .addOnSuccessListener {
                        if (it.user != null) {
                            latch.countDown()
                            Result.success()
                        }
                    }
                    .addOnFailureListener {
                        latch.countDown()
                        Result.retry()
                    }
        }else{
            latch.countDown()
        }
    }

  private fun getBibleQuiz(){
      cloud.collection("Bible")
              .get(Source.SERVER)
              .addOnCompleteListener { taskQuerySnapshot ->
                  if (taskQuerySnapshot.isSuccessful) {
                          val index = taskQuerySnapshot.result.toObjects(QuizModel::class.java)
                          //val cacheIndex = quizMapper.convertToCacheList(index)
                          val ans = gson.toJson(index)
                          Log.i("GSON", ans)
                          //addToRoomQuiz(cacheIndex)
                          latch.countDown()
                  }else{
                  }
              }.addOnFailureListener {
                  Log.d("error", it.toString())
              }
  }

    private fun doAllWork(){
        doAWork()
        if (auth.currentUser != null){
            getBibleQuiz()
        }
    }
}