package com.example.maricools_app_designs

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.job.JobInfo
import android.content.Context
import android.os.Build
import android.os.Build.VERSION_CODES.N
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.*
import com.example.maricools_app_designs.androidcomponents.MyApplication
import com.example.maricools_app_designs.database.CacheDatabase
import com.example.maricools_app_designs.utils.models.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import java.util.concurrent.CountDownLatch
import javax.inject.Inject

class WorkerClass
@Inject
constructor(@ApplicationContext var context: Context,
            params: WorkerParameters): CoroutineWorker(context, params) {

     private var mapper: CacheMapper = CacheMapper()
    private var factMapper: FactMapper = FactMapper()
    private var quizMapper: QuizMapper = QuizMapper()
    private var oomMapper: OOMMapper = OOMMapper()

     private var database = CacheDatabase.getDatabase(context)
    private val cloud = FirebaseFirestore.getInstance()
   private val prayerdao = database.prayerDao()
    private val factdao = database.factDao()
    private val oomDao = database.oomDao()
    private val quizDow = database.quiDao()
    private val latch = CountDownLatch(N)

     override suspend fun doWork(): Result {
         try {
             setForeground(createForegroundInfo())
                 doAllWork()
                 latch.await()
           return Result.success()
        } catch (e: Exception) {
            return Result.failure()
        }
    }

    private fun addToRoom(prayer: List<PrayerModel>) {
        prayerdao.addPrayer(prayer)
    }

    private fun addOomToRoom(oom: List<OOMModel>) {
        oomDao.addOOM(oom)
    }

    private fun addToRoomFact(fact: List<FactModel>) {
        factdao.addFact(fact)
    }

    private fun addToRoomQuiz(quiz: List<QuizEntityModel>) {
        quizDow.insertQuiz(quiz)
    }

    private fun getData(){
                cloud.collection("Prayer ").orderBy("id")
                .get(Source.SERVER)
                .addOnCompleteListener { taskQuerySnapshot ->
                    if (taskQuerySnapshot.isSuccessful) {
                        for (query in taskQuerySnapshot.result!!) {
                            val index =  taskQuerySnapshot.result!!.toObjects(PrayerServerModel::class.java)
                            val cacheIndex = mapper.convertToCacheList(index)
                           addToRoom(cacheIndex)
                            latch.countDown()
                            Result.success()
                        }
                    }
                    else{
                        Result.retry()
                    }
               }.addOnFailureListener {
                    Log.d("error", it.toString())
                            latch.countDown()
                    Result.retry()
                }
    }

    private fun getOrderOfMass(){
        cloud.collection("Order of mass")
                .get(Source.SERVER)
                .addOnCompleteListener { taskQuerySnapshot ->
                    if (taskQuerySnapshot.isSuccessful) {
                        for (query in taskQuerySnapshot.result!!) {
                            val index =  taskQuerySnapshot.result!!.toObjects(OOMServerModel::class.java)
                            val cacheIndex = oomMapper.convertToCacheList(index)
                            addOomToRoom(cacheIndex)
                            latch.countDown()
                            Result.success()
                        }
                    }
                    else{
                        Result.retry()
                    }
                }.addOnFailureListener {
                    Log.d("error", it.toString())
                    latch.countDown()
                    Result.retry()
                }
    }

    private fun getFactData(){
        cloud.collection("Facts")
                .get(Source.SERVER)
                .addOnCompleteListener { taskQuerySnapshot ->
                    if (taskQuerySnapshot.isSuccessful) {
                        for (query in taskQuerySnapshot.result!!) {
                            val index =  taskQuerySnapshot.result!!.toObjects(FactServerModel::class.java)
                            val cacheIndex = factMapper.convertToCacheList(index)
                            addToRoomFact(cacheIndex)
                            latch.countDown()
                            Result.success()
                        }
                    }
                    else{
                        Result.retry()
                    }
                }.addOnFailureListener {
                    Log.d("error", it.toString())
                    latch.countDown()
                    Result.retry()
                }
    }

    private fun getBibleQuiz(){
        cloud.collection("Bible")
                .get(Source.SERVER)
                .addOnCompleteListener { taskQuerySnapshot ->
                    if (taskQuerySnapshot.isSuccessful) {
                        for (query in taskQuerySnapshot.result!!) {
                            val index = taskQuerySnapshot.result!!.toObjects(QuizModel::class.java)
                            val cacheIndex = quizMapper.convertToCacheList(index)
                            addToRoomQuiz(cacheIndex)
                            latch.countDown()
                            Result.success()
                        }
                        }
                    else{
                        Result.retry()
                    }
                }.addOnFailureListener {
                    Log.d("error", it.toString())
                    latch.countDown()
                    Result.retry()
                }
    }

    private fun doAllWork(){
        getData()
        getFactData()
       getBibleQuiz()
        getOrderOfMass()
    }

    private fun createForegroundInfo(): ForegroundInfo {
        val notificationId = 1
        return ForegroundInfo(notificationId, showNotification())
    }

    private fun showNotification(): Notification {
        //val manager = NotificationManagerCompat.from(context)
        val builder = NotificationCompat.Builder(context, MyApplication.CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_faithicon_round)
                .setContentTitle("Know your Faith")
                .setContentText("Work in progress")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setOngoing(true)
                .build()

        return builder
    }
}